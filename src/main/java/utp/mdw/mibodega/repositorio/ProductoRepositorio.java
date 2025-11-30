package utp.mdw.mibodega.repositorio;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.modelo.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
    
    // Contar productos con stock 0
    long countByStock(int stock);
    
    Optional<Producto> findByNombre(String nombre);
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    List<Producto> findByNombreStartingWithIgnoreCase(String nombre);
}
