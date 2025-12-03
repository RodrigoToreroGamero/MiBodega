package utp.mdw.mibodega.configuracion;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Usuario;
import utp.mdw.mibodega.repositorio.UsuarioRepositorio;

@Service
public class ImplementacionServicioDetallesUsuario implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;   
       
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getContrasenia(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name().toUpperCase()))
        );
    }

}
