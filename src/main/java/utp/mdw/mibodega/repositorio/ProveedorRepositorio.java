package utp.mdw.mibodega.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.modelo.Proveedor;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, Long> {
}