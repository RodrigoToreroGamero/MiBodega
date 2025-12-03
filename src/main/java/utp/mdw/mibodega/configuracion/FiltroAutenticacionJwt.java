package utp.mdw.mibodega.configuracion;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
        String cabecera = peticion.getHeader("Authorization");
        
        if(cabecera != null && cabecera.startsWith("Bearer ")) {
            String jwt = cabecera.substring(7);
            String nombreUsuario = this.utilidadJwt.extraerNombreUsuario(jwt);
            
            if(nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails detallesUsuario = this.servicioDetallesUsuario.loadUserByUsername(nombreUsuario);
                
                if(this.utilidadJwt.validarToken(jwt, detallesUsuario)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(detallesUsuario, null, detallesUsuario.getAuthorities());
                    
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(peticion));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }
        filtro.doFilter(peticion, respuesta);
    }
    
}
