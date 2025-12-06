package utp.mdw.mibodega.repositorio;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.dto.ResumenVentaDTO;
import utp.mdw.mibodega.modelo.Estado;
import utp.mdw.mibodega.modelo.Venta;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {

    // Contar ventas por estado (por ejemplo "pagada" o "pendiente")
    long countByEstado(Estado estado);

    // Contar ventas desde una fecha específica
    long countByFechaEmisionAfter(LocalDateTime fechaInicio);
    // Obtener el producto más vendido (sin filtro de fecha)

    @Query(value = "SELECT p.nombre FROM detalles_ventas dv "
            + "JOIN productos p ON dv.id_producto = p.id "
            + "GROUP BY p.id, p.nombre "
            + "ORDER BY SUM(dv.cantidad) DESC "
            + "LIMIT 1", nativeQuery = true)
    String findProductoMasVendido();

    @Query(value = "SELECT p.nombre FROM detalles_ventas dv "
            + "JOIN productos p ON dv.id_producto = p.id "
            + "JOIN ventas v ON dv.id_venta = v.id "
            + "WHERE v.fecha >= :fechaInicio "
            + "GROUP BY p.id, p.nombre "
            + "ORDER BY SUM(dv.cantidad) DESC "
            + "LIMIT 1", nativeQuery = true)
    String findProductoMasVendidoByFecha(@Param("fechaInicio") LocalDateTime fechaInicio);

// Obtener resumen de ventas (sin filtro de fecha)
    @Query("SELECT new utp.mdw.mibodega.dto.ResumenVentaDTO(p.nombre, SUM(dv.cantidad), SUM(dv.precioTotal)) "
            + "FROM DetalleVenta dv "
            + "JOIN dv.producto p "
            + "GROUP BY p.id, p.nombre "
            + "ORDER BY SUM(dv.cantidad) DESC")
    List<ResumenVentaDTO> findResumenVentas();

// Obtener resumen de ventas con filtro de fecha
    @Query("SELECT new utp.mdw.mibodega.dto.ResumenVentaDTO(p.nombre, SUM(dv.cantidad), SUM(dv.precioTotal)) "
            + "FROM DetalleVenta dv "
            + "JOIN dv.producto p "
            + "JOIN dv.venta v "
            + "WHERE v.fechaEmision >= :fechaInicio "
            + "GROUP BY p.id, p.nombre "
            + "ORDER BY SUM(dv.cantidad) DESC")
    List<ResumenVentaDTO> findResumenVentasByFecha(@Param("fechaInicio") LocalDateTime fechaInicio);
}