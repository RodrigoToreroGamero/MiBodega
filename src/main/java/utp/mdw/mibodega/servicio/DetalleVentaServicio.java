package utp.mdw.mibodega.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.DetalleVenta;
import utp.mdw.mibodega.repositorio.DetalleVentaRepositorio;

@Service
public class DetalleVentaServicio {
    
    @Autowired
    private DetalleVentaRepositorio repo;
    
    public DetalleVenta guardar(DetalleVenta detalle) {
        return this.repo.save(detalle);
    }
    
    public List<DetalleVenta> listarTodos() {
        return this.repo.findAll();
    }
}
