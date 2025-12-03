package utp.mdw.mibodega.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utp.mdw.mibodega.modelo.Inventario;
import utp.mdw.mibodega.repositorio.CategoriaRepositorio;
import utp.mdw.mibodega.repositorio.InventarioRepositorio;
import utp.mdw.mibodega.repositorio.ProductoRepositorio;

@Service
public class InventarioServicio {

    @Autowired
    private InventarioRepositorio inventarioRepositorio;
    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    public List<Inventario> listarTodo() {
        return inventarioRepositorio.findAll();
    }
    // (Opcional) obtener el primer inventario
//    public Inventario obtenerInventarioActual() {
//        return inventarioRepositorio.findAll().stream().findFirst().orElse(null);
//    }

    public Inventario obtenerInventarioActual() {
        return inventarioRepositorio.findAll().stream().findFirst().orElse(null);
    }
    public void agregarProducto(utp.mdw.mibodega.modelo.Producto producto) {
        // Asignar inventario actual
        Inventario inventario = obtenerInventarioActual();
        producto.setInventario(inventario);

        // Generar código si viene vacío
        if (producto.getCodigo() == null || producto.getCodigo().isBlank()) {
            String codigoGenerado = "PRD-" + System.currentTimeMillis();
            producto.setCodigo(codigoGenerado);
        }

        productoRepositorio.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepositorio.deleteById(id);
    }

    public void editarProducto(utp.mdw.mibodega.modelo.Producto producto) {
        utp.mdw.mibodega.modelo.Producto existente = productoRepositorio.findById(producto.getId()).orElse(null);
        if (existente != null) {
            existente.setNombre(producto.getNombre());
            existente.setDescripcion(producto.getDescripcion());
            existente.setStock(producto.getStock());
            existente.setPrecioUnitario(producto.getPrecioUnitario());
            existente.setCategoria(producto.getCategoria());
            existente.setProveedor(producto.getProveedor());
            // inventario lo dejamos igual

            productoRepositorio.save(existente);
        }
    
}
}
