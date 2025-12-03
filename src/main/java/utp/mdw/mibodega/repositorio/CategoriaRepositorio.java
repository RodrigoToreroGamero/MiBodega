package utp.mdw.mibodega.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utp.mdw.mibodega.modelo.Categoria;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {
}
