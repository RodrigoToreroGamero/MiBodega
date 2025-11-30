package utp.mdw.mibodega.servicio;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Usuario;
import utp.mdw.mibodega.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio repo;
    
    public Usuario obtenerPorId(Long id) {
        return this.repo.findById(id).orElse(null);
    }
}
