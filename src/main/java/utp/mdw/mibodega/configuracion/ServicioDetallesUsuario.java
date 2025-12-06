package utp.mdw.mibodega.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Usuario;
import utp.mdw.mibodega.repositorio.UsuarioRepositorio;

@Service
public class ServicioDetallesUsuario implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio repo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = this.repo.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new DetallesUsuario(usuario);
    }
}
