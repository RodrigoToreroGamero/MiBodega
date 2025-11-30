package utp.mdw.mibodega.controlador;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import utp.mdw.mibodega.servicio.InventarioServicio;
import utp.mdw.mibodega.modelo.Inventario;

@Controller
@RequestMapping("/mibodega")
public class InventarioControlador {

    @Autowired
    private InventarioServicio inventarioServicio;

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
    return "miBodega_inventario";  
}

}
