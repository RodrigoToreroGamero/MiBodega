package utp.mdw.mibodega.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Inventario;
import utp.mdw.mibodega.repositorio.InventarioRepositorio;

@Service
public class InventarioServicio {

    @Autowired
    private InventarioRepositorio inventarioRepositorio;

    public List<Inventario> listarTodo() {
        return inventarioRepositorio.findAll();
    }
    // (Opcional) obtener el primer inventario
//    public Inventario obtenerInventarioActual() {
//        return inventarioRepositorio.findAll().stream().findFirst().orElse(null);
//    }

    public Inventario obtenerInventarioActual() {
        return inventarioRepositorio.findAll().stream().findFirst().orElse(null);
    }

    
}
