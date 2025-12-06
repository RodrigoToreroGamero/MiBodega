package utp.mdw.mibodega.controlador;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import utp.mdw.mibodega.servicio.InventarioServicio;
import utp.mdw.mibodega.modelo.Inventario;
import utp.mdw.mibodega.modelo.Producto;
import utp.mdw.mibodega.repositorio.CategoriaRepositorio;
import utp.mdw.mibodega.repositorio.ProveedorRepositorio;


@Controller
@RequestMapping("/mibodega")
public class InventarioControlador {

    @Autowired
    private InventarioServicio inventarioServicio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;


    @GetMapping("/inventario")
public String mostrarInventario(Model model) {
    // Obtener la fecha y la hora actuales
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        
        // Pasar la fecha y la hora al modelo
        model.addAttribute("fecha", fecha);
        model.addAttribute("hora", hora);
    Inventario inventario = inventarioServicio.obtenerInventarioActual();
    if (inventario != null) {
        System.out.println("Inventario encontrado: " + inventario.getId());
        System.out.println("Productos: " + inventario.getProductos().size());
        model.addAttribute("productos", inventario.getProductos());
    } else {
        System.out.println("⚠️ No se encontró ningún inventario en la base de datos.");
        model.addAttribute("productos", null);
    }
    // Objeto vacío para el formulario de "Añadir producto"
        model.addAttribute("productoNuevo", new Producto());
        model.addAttribute("categorias", categoriaRepositorio.findAll());
        model.addAttribute("proveedores", proveedorRepositorio.findAll());
    return "miBodega_inventario";  
}

   // ➕ Añadir producto
    @PostMapping("/inventario/agregar")
    public String agregarProducto(@ModelAttribute("productoNuevo") Producto producto) {
        inventarioServicio.agregarProducto(producto);
        return "redirect:/mibodega/inventario";
    }

    // 🗑 Eliminar producto
    @GetMapping("/inventario/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        inventarioServicio.eliminarProducto(id);
        return "redirect:/mibodega/inventario";
    }

    // ✏️ Editar producto
    @PostMapping("/inventario/editar")
    public String editarProducto(@ModelAttribute Producto producto) {
        inventarioServicio.editarProducto(producto);
        return "redirect:/mibodega/inventario";
    }
}
