package utp.mdw.mibodega.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.DetalleVenta;
import utp.mdw.mibodega.modelo.Venta;
import utp.mdw.mibodega.repositorio.VentaRepositorio;

@Service
public class VentaServicio {
    
    @Autowired
    private VentaRepositorio repo;
    
    public Venta guardarVentaSimple(Venta venta) {
        
        if(venta.getDetalles() != null) {
            for(DetalleVenta d : venta.getDetalles()) {
                d.setVenta(venta);
            }
        }
        
        return this.repo.save(venta);
    }
}
