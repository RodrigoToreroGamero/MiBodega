package utp.mdw.mibodega.controlador;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import utp.mdw.mibodega.modelo.DetalleVenta;
import utp.mdw.mibodega.modelo.Estado;
import utp.mdw.mibodega.modelo.Producto;
import utp.mdw.mibodega.modelo.Usuario;
import utp.mdw.mibodega.modelo.Venta;
import utp.mdw.mibodega.servicio.ProductoServicio;
import utp.mdw.mibodega.servicio.UsuarioServicio;
import utp.mdw.mibodega.servicio.VentaServicio;

@Controller
@RequestMapping("/mibodega")
@SessionAttributes({"detallesSeleccionados", "venta"})
public class RegistroControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private VentaServicio ventaServicio;

    @ModelAttribute("detallesSeleccionados")
    public List<DetalleVenta> detallesSeleccionados() {
        return new ArrayList<>();
    }

    @ModelAttribute("venta")
    public Venta venta() {
        return new Venta();
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model, @ModelAttribute("detallesSeleccionados") @Valid List<DetalleVenta> detallesSeleccionados) {
        
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

        
        model.addAttribute("fecha", fecha);
        model.addAttribute("hora", hora);
        model.addAttribute("detalles", detallesSeleccionados);
        
        return "miBodega_registro";
    }

    @GetMapping("/registro/buscar")
    public String buscar(@RequestParam String busquedaNombreParcial, Model model, @ModelAttribute("detallesSeleccionados") @Valid List<DetalleVenta> detallesSeleccionados) {
        List<Producto> productos = null;

        if (busquedaNombreParcial != null && !busquedaNombreParcial.isBlank()) {
            productos = this.productoServicio.buscarPorNombreDesdeInicioYParcial(busquedaNombreParcial);

            if (productos != null && !productos.isEmpty()) {
                productos.removeIf(Objects::isNull);
                model.addAttribute("productos", productos);
            } else {
                model.addAttribute("sinResultados", true);
            }

        }
        model.addAttribute("detalles", detallesSeleccionados);
        return "miBodega_registro";
    }

    @GetMapping("/registro/mostrar-todos")
    public String mostrarTodos(Model model, @ModelAttribute("detallesSeleccionados") @Valid List<DetalleVenta> detallesSeleccionados) {
        List<Producto> productos = this.productoServicio.obtenerTodos();
        if (productos != null && !productos.isEmpty()) {
            productos.removeIf(Objects::isNull);
            model.addAttribute("productos", productos);
        } else {
            model.addAttribute("sinResultados", true);
        }

        model.addAttribute("detalles", detallesSeleccionados);
        return "miBodega_registro";
    }

    @PostMapping("/registro/seleccionar")
    public String seleccionarProducto(@RequestParam Long idProducto, Model model, @ModelAttribute("detallesSeleccionados") @Valid List<DetalleVenta> detallesSeleccionados) {
        Producto p = this.productoServicio.obtenerPorId(idProducto);
        if (p != null) {
            model.addAttribute("productoSeleccionado", p);
        }

        model.addAttribute("detalles", detallesSeleccionados); // Esto asegura que la tabla de productos seleccionados se renderice correctamente junto al formulario del producto seleccionado.
        return "miBodega_registro";
    }

    @PostMapping("/registro/confirmar-seleccion")
    public String confirmarSeleccion(@RequestParam Long idProducto, @RequestParam Integer cantidad, Model model, @ModelAttribute("detallesSeleccionados") @Valid List<DetalleVenta> detallesSeleccionados, @ModelAttribute("venta") Venta venta) {
        Producto p = this.productoServicio.obtenerPorId(idProducto);

        if (p != null) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(p);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(p.getPrecioUnitario());
            detalle.setPrecioTotal(p.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad)));
            detalle.setVenta(venta);
            detallesSeleccionados.add(detalle);
        }

        BigDecimal precioTotal = BigDecimal.ZERO;

        for (DetalleVenta dv : detallesSeleccionados) {
            precioTotal = precioTotal.add(dv.getPrecioTotal());
        }

        venta.setPrecioTotal(precioTotal);
        venta.setDetalles(detallesSeleccionados);

        model.addAttribute("detalles", detallesSeleccionados); // Esto actualiza la tabla en la vista con el nuevo producto agregado.
        model.addAttribute("venta", venta);
        model.addAttribute("productoSeleccionado", null);
        return "miBodega_registro";
    }

    @PostMapping("/registro/eliminar-seleccion")
    public String eliminarSeleccion(@RequestParam Long idProducto, Model model, @ModelAttribute("detallesSeleccionados") @Valid List<DetalleVenta> detallesSeleccionados) {
        Producto p = this.productoServicio.obtenerPorId(idProducto);

        int indice = -1;

        for (int i = 0; i < detallesSeleccionados.size(); i++) {
            if (Objects.equals(detallesSeleccionados.get(i).getProducto().getId(), p.getId())) {
                indice = i;
            }
        }

        if (indice > -1) {
            detallesSeleccionados.remove(indice);
        }

        model.addAttribute("detalles", detallesSeleccionados); // Esto actualiza la tabla en la vista con el producto eliminado.
        return "miBodega_registro";
    }

    @PostMapping("/registro/simple")
    public String registroSimple(@ModelAttribute Venta venta, Model model, SessionStatus status, @ModelAttribute("detallesSeleccionados") @Valid List<DetalleVenta> detallesSeleccionados) {
        venta.setFechaEmision(LocalDateTime.now());

        if (venta.getEstado() == null) {
            venta.setEstado(Estado.pendiente);
        }

        Usuario u = this.usuarioServicio.obtenerPorId(1L); // Valor asignado para pruebas, ELIMINAR una vez exista login.
        if (u == null) {
            return "error";
        }

        venta.setUsuario(u);
        venta.setDetalles(detallesSeleccionados);

        for (DetalleVenta dv : detallesSeleccionados) {
            Producto p = dv.getProducto();
            Integer cantidad = dv.getCantidad();

            if (p.getStock() < cantidad) {
                model.addAttribute("stockInsuficiente", p.getNombre());
            } else {
                p.setStock(p.getStock() - cantidad);
                this.productoServicio.guardar(p);
            }
        }

        Venta ventaSimple = this.ventaServicio.guardarVentaSimple(venta);

        status.setComplete();
        return "miBodega_registro";
    }
}
