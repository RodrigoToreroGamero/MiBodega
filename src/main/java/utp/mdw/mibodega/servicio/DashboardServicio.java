package utp.mdw.mibodega.servicio;

import java.time.LocalDateTime;
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

    public long pedidosPendientes() {
        return ventaRepo.countByEstado(Estado.pendiente);
    }

    public long nuevosClientes() {
        return clienteRepo.count();
    }

    public long productosAgotados() {
        return productoRepo.countByStock(0);
    }

    /**
     * Obtiene el total de ventas según el filtro de fecha
     *
     * @param filtro: "hoy", "semana", "mes", "anio", "todos"
     */
    public long totalVentas(String filtro) {
        LocalDateTime fechaInicio = obtenerFechaInicio(filtro);

        if (fechaInicio == null) {
            return ventaRepo.count(); // Todos
        }

        return ventaRepo.countByFechaEmisionAfter(fechaInicio);
    }

    /**
     * Calcula la fecha de inicio según el filtro
     */
    private LocalDateTime obtenerFechaInicio(String filtro) {
        LocalDateTime ahora = LocalDateTime.now();

        switch (filtro.toLowerCase()) {
            case "hoy":
                return ahora.toLocalDate().atStartOfDay();
            case "semana":
                return ahora.minusWeeks(1);
            case "mes":
                return ahora.minusMonths(1);
            case "anio":
                return ahora.minusYears(1);
            case "todos":
            default:
                return null;
        }
    }
}
