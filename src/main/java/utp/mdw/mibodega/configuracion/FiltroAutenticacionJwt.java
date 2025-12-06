package utp.mdw.mibodega.configuracion;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FiltroAutenticacionJwt extends OncePerRequestFilter {

    @Autowired
    private UtilidadJwt utilidadJwt;

    @Autowired
    private UserDetailsService servicioDetallesUsuario;
    
    //DEBUG
    private static final Logger logger = LoggerFactory.getLogger(FiltroAutenticacionJwt.class);

    @Override
    protected void doFilterInternal(HttpServletRequest peticion, HttpServletResponse respuesta, FilterChain filtro) throws ServletException, IOException {
        String jwt = null;

        String cabecera = peticion.getHeader("Authorization");
        //DEBUG
        this.logger.debug("Petición a {} con cabecera Authorization: {}", peticion.getRequestURI(), cabecera);

        if (cabecera != null && cabecera.startsWith("Bearer ")) {
            jwt = cabecera.substring(7);
        }

        /*
        if (jwt == null && peticion.getCookies() != null) {
            for (Cookie cookie : peticion.getCookies()) {
                //DEBUG 
                this.logger.debug("Cookie encontrada: {}={}", cookie.getName(), cookie.getValue());
                
                if ("jwt".equals(cookie.getName())
                        && cookie.getValue() != null
                        && !cookie.getValue().isBlank()) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }
        */

        //DEBUG
        this.logger.debug("Token extraído: {}", jwt);

        if (jwt != null) {
            try {

                String nombreUsuario = this.utilidadJwt.extraerNombreUsuario(jwt);
                //DEBUG
                this.logger.debug("Usuario extraido del token: {}", nombreUsuario);

                if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails detallesUsuario = this.servicioDetallesUsuario.loadUserByUsername(nombreUsuario);
                    //DEBUG
                    this.logger.debug("UserDetails cargado: {}", detallesUsuario);

                    if (this.utilidadJwt.validarToken(jwt, detallesUsuario)) {
                        //DEBUG
                        this.logger.debug("Token válido para usuario: {}", nombreUsuario);
                        
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(detallesUsuario, null, detallesUsuario.getAuthorities());

                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(peticion));
                        SecurityContextHolder.getContext().setAuthentication(token);
                        
                        //DEBUG
                        this.logger.debug("Authentication seteado en SecurityContextHolder: {}", token);
                    } else {
                        //DEBUG
                        this.logger.debug("Token inválido para usuario {}", nombreUsuario);
                    }
                }
            } catch (Exception e) {
                //DEBUG
                this.logger.error("Error validando token: {}", e.getMessage(), e);
            }
        } else {
            //DEBUG
            this.logger.debug("No se encontró token en la petición {}", peticion.getRequestURI());
        }        
        filtro.doFilter(peticion, respuesta);
    }

}
