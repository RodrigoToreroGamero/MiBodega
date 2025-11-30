package utp.mdw.mibodega.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.modelo.DetalleVenta;

@Repository
public interface DetalleVentaRepositorio extends JpaRepository<DetalleVenta, Long> {
    
}
