package utp.mdw.mibodega.controlador.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utp.mdw.mibodega.configuracion.UtilidadJwt;
import utp.mdw.mibodega.dto.LoginPeticionDTO;
import utp.mdw.mibodega.dto.LoginRespuestaDTO;

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
    public ResponseEntity<LoginRespuestaDTO> login(@RequestBody LoginPeticionDTO peticion) {
        Authentication autenticacion = this.autenticador.authenticate(
            new UsernamePasswordAuthenticationToken(peticion.getCorreo(), peticion.getContrasenia())
        );

        UserDetails detallesUsuario = (UserDetails) autenticacion.getPrincipal();
        String token = utilidadJwt.generarToken(detallesUsuario);

        return ResponseEntity.ok(new LoginRespuestaDTO(token));
    }
}
