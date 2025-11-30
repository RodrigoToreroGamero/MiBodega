package utp.mdw.mibodega.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Estado;
import utp.mdw.mibodega.repositorio.ClienteRepositorio;
import utp.mdw.mibodega.repositorio.ProductoRepositorio;
import utp.mdw.mibodega.repositorio.VentaRepositorio;

@Service
public class DashboardServicio {
    @Autowired
    private VentaRepositorio ventaRepo;
    @Autowired
    private ClienteRepositorio clienteRepo;
    @Autowired
    private ProductoRepositorio productoRepo;

    public long totalVentas() {
        return ventaRepo.count();
    }

    public long pedidosPendientes() {
        return ventaRepo.countByEstado(Estado.pendiente);
    }

    public long nuevosClientes() {
        return clienteRepo.count();
    }

    public long productosAgotados() {
        return productoRepo.countByStock(0);
    }
}
