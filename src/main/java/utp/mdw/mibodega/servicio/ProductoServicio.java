package utp.mdw.mibodega.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Producto;
import utp.mdw.mibodega.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio {

    @Autowired
    private ProductoRepositorio repo;
    
    public List<Producto> obtenerTodos() {
        return this.repo.findAll();
    }

    public Optional<Producto> buscarPorNombreExacto(String nombre) {
        return this.repo.findByNombre(nombre);
    }

    public List<Producto> buscarPorNombreParcial(String nombre) {
        return this.repo.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorNombreDesdeInicio(String nombre) {
        return this.repo.findByNombreStartingWithIgnoreCase(nombre);
    }
    
    public List<Producto> buscarPorNombreDesdeInicioYParcial(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        
        List<Producto> iniciales = this.repo.findByNombreStartingWithIgnoreCase(nombre);
        resultados.addAll(iniciales);
        
        List<Producto> parciales = this.repo.findByNombreContainingIgnoreCase(nombre);
        
        for(Producto p : parciales) {
            if(!resultados.contains(p)) {
                resultados.add(p);
            }
        }
        
        return resultados;
    }    

    public Producto obtenerPorId(Long id) {
        return this.repo.findById(id).orElse(null);
    }
    
    public Producto guardar(Producto producto) {
        return this.repo.save(producto);
    } 

}
