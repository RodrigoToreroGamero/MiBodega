package utp.mdw.mibodega.controlador.api;

import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utp.mdw.mibodega.configuracion.UtilidadJwt;
import utp.mdw.mibodega.dto.LoginPeticionDTO;

@RestController
@RequestMapping("/api/autenticacion")
public class AutenticacionControlador {

    private final AuthenticationManager autenticador;
    private final UtilidadJwt utilidadJwt;

    public AutenticacionControlador(AuthenticationManager autenticador, UtilidadJwt utilidadJwt) {
        this.autenticador = autenticador;
        this.utilidadJwt = utilidadJwt;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginPeticionDTO peticion, HttpServletResponse respuesta) {
        try {
            Authentication autenticacion = this.autenticador.authenticate(
                    new UsernamePasswordAuthenticationToken(peticion.getCorreo(), peticion.getContrasenia())
            );

            UserDetails detallesUsuario = (UserDetails) autenticacion.getPrincipal();
            String token = utilidadJwt.generarToken(detallesUsuario);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                //.secure(true) // solo por https
                .path("/")
                .sameSite("Strict") // frontend y backend en el mismo dominio
                .maxAge(Duration.ofHours(10)) // 10 horas;
                .build();
            respuesta.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity.ok("Login correct");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
