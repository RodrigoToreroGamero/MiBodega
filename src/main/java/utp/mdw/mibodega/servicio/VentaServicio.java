package utp.mdw.mibodega.servicio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.dto.ResumenVentaDTO;
import utp.mdw.mibodega.modelo.DetalleVenta;
import utp.mdw.mibodega.modelo.Venta;
import utp.mdw.mibodega.repositorio.VentaRepositorio;

@Service
public class VentaServicio {

    @Autowired
    private VentaRepositorio repo;

    public Venta guardarVentaSimple(Venta venta) {

        if (venta.getDetalles() != null) {
            for (DetalleVenta d : venta.getDetalles()) {
                d.setVenta(venta);
            }
        }

        return this.repo.save(venta);
    }

    /**
     * Obtiene el producto más vendido según el filtro
     */
    public String obtenerProductoMasVendido(String filtro) {
        LocalDateTime fechaInicio = obtenerFechaInicio(filtro);

        if (fechaInicio == null) {
            return repo.findProductoMasVendido();
        }

        return repo.findProductoMasVendidoByFecha(fechaInicio);
    }

    /**
     * Obtiene el resumen de ventas (productos con cantidad e ingresos)
     */
    public List<ResumenVentaDTO> obtenerResumenVentas(String filtro) {
        LocalDateTime fechaInicio = obtenerFechaInicio(filtro);

        if (fechaInicio == null) {
            return repo.findResumenVentas();
        }

        return repo.findResumenVentasByFecha(fechaInicio);
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