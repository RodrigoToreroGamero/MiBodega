package utp.mdw.mibodega.controlador;



import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mibodega")
public class VentasControlador {
    
    @GetMapping("/ventas")
    public String mostrarVentas(Model model) {
        // Obtener la fecha y la hora actuales
        String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        
        // Pasar la fecha y la hora al modelo
        model.addAttribute("fecha", fecha);
        model.addAttribute("hora", hora);
        return "miBodega_ventas";  
    }
}