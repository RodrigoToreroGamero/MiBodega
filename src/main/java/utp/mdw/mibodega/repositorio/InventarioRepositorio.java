package utp.mdw.mibodega.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.modelo.Inventario;

@Repository
public interface InventarioRepositorio extends JpaRepository<Inventario, Long> {
}
