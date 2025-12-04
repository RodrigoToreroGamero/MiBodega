package utp.mdw.mibodega.configuracion;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Override
    protected void doFilterInternal(HttpServletRequest peticion, HttpServletResponse respuesta, FilterChain filtro) throws ServletException, IOException {
        
        String jwt = null;

        String cabecera = peticion.getHeader("Authorization");

        if (cabecera != null && cabecera.startsWith("Bearer ")) {
            jwt = cabecera.substring(7);
        }

        if (jwt == null && peticion.getCookies() != null) {
            for (Cookie cookie : peticion.getCookies()) {
                if ("jwt".equals(cookie.getName())
                        && cookie.getValue() != null
                        && !cookie.getValue().isBlank()) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt != null) {
            try {

                String nombreUsuario = this.utilidadJwt.extraerNombreUsuario(jwt);

                if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails detallesUsuario = this.servicioDetallesUsuario.loadUserByUsername(nombreUsuario);

                    if (this.utilidadJwt.validarToken(jwt, detallesUsuario)) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(detallesUsuario, null, detallesUsuario.getAuthorities());

                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(peticion));
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage()); 
            }
        }
        filtro.doFilter(peticion, respuesta);
    }

}
