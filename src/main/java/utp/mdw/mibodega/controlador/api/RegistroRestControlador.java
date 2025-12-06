package utp.mdw.mibodega.controlador.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utp.mdw.mibodega.dto.DetalleVentaDTO;
import utp.mdw.mibodega.dto.ProductoDTO;
import utp.mdw.mibodega.dto.SeleccionDTO;
import utp.mdw.mibodega.dto.VentaDTO;
import utp.mdw.mibodega.modelo.DetalleVenta;
import utp.mdw.mibodega.modelo.Estado;
import utp.mdw.mibodega.modelo.Producto;
import utp.mdw.mibodega.modelo.Venta;
import utp.mdw.mibodega.servicio.ProductoServicio;
import utp.mdw.mibodega.servicio.VentaServicio;

@RestController
@RequestMapping("/api/registro")
public class RegistroRestControlador {

    @Autowired
    private VentaServicio ventaServicio;
    
    @Autowired
    private ProductoServicio productoServicio;
    
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscar(@RequestParam String nombre) {
        List<Producto> productos = productoServicio.buscarPorNombreDesdeInicioYParcial(nombre);

        List<ProductoDTO> productosDTO = new ArrayList<>();
        for (Producto p : productos) {
            ProductoDTO dto = new ProductoDTO();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setPrecioUnitario(p.getPrecioUnitario());
            dto.setStock(p.getStock());
            productosDTO.add(dto);
        }

        return ResponseEntity.ok(productosDTO);
    }

    // Mostrar todos los productos
    @GetMapping("/mostrar-todos")
    public ResponseEntity<List<ProductoDTO>> mostrarTodos() {
        List<Producto> productos = productoServicio.obtenerTodos();

        List<ProductoDTO> productosDTO = new ArrayList<>();
        for (Producto p : productos) {
            ProductoDTO dto = new ProductoDTO();
            dto.setId(p.getId());
            dto.setNombre(p.getNombre());
            dto.setPrecioUnitario(p.getPrecioUnitario());
            dto.setStock(p.getStock());
            productosDTO.add(dto);
        }

        return ResponseEntity.ok(productosDTO);
    }

    // Registrar venta simple (el carrito completo viene en el body)
    @PostMapping("/simple")
    public ResponseEntity<VentaDTO> registroSimple(@RequestBody VentaDTO dto) {
        Venta venta = new Venta();
        venta.setFechaEmision(LocalDateTime.now());
        venta.setEstado(Estado.valueOf(dto.getEstado()));
        venta.setPrecioTotal(dto.getPrecioTotal());

        List<DetalleVenta> detalles = new ArrayList<>();
        if (dto.getDetalles() != null) {
            for (DetalleVentaDTO d : dto.getDetalles()) {
                DetalleVenta det = new DetalleVenta();
                det.setCantidad(d.getCantidad());
                det.setPrecioUnitario(d.getPrecioUnitario());
                det.setPrecioTotal(d.getPrecioTotal());

                Producto p = new Producto();
                p.setId(d.getProducto().getId());
                det.setProducto(p);

                det.setVenta(venta);
                detalles.add(det);
            }
        }
        venta.setDetalles(detalles);

        Venta guardada = ventaServicio.guardarVentaSimple(venta);

        // Convertir a DTO de respuesta
        VentaDTO respuesta = new VentaDTO();
        respuesta.setId(guardada.getId());
        respuesta.setFechaEmision(guardada.getFechaEmision());
        respuesta.setEstado(guardada.getEstado().name());
        respuesta.setPrecioTotal(guardada.getPrecioTotal());

        List<DetalleVentaDTO> detallesDTO = new ArrayList<>();
        if (guardada.getDetalles() != null) {
            for (DetalleVenta det : guardada.getDetalles()) {
                DetalleVentaDTO d = new DetalleVentaDTO();
                d.setId(det.getId());
                d.setCantidad(det.getCantidad());
                d.setPrecioUnitario(det.getPrecioUnitario());
                d.setPrecioTotal(det.getPrecioTotal());

                ProductoDTO p = new ProductoDTO();
                p.setId(det.getProducto().getId());
                p.setNombre(det.getProducto().getNombre());
                p.setPrecioUnitario(det.getProducto().getPrecioUnitario());
                d.setProducto(p);

                detallesDTO.add(d);
            }
        }
        respuesta.setDetalles(detallesDTO);

        return ResponseEntity.ok(respuesta);
    }

}
