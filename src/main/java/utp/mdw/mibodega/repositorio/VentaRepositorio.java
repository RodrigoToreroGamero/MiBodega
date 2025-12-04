package utp.mdw.mibodega.repositorio;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.modelo.Estado;
import utp.mdw.mibodega.modelo.Venta;

@Repository
public interface VentaRepositorio extends JpaRepository<Venta, Long> {

    // Contar ventas por estado (por ejemplo "pagada" o "pendiente")
    long countByEstado(Estado estado);
    // Contar ventas desde una fecha específica
    long countByFechaEmisionAfter(LocalDateTime fechaInicio);
}
