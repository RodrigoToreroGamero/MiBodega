package utp.mdw.mibodega.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.modelo.Notificacion;

@Repository
public interface NotificacionRepositorio extends JpaRepository<Notificacion, Long> {

    @Query(value = "SELECT * FROM notificaciones ORDER BY fecha_creacion DESC LIMIT ?1", nativeQuery = true)
    List<Notificacion> findTopNByOrderByFechaCreacionDesc(int limite);
}
