package utp.mdw.mibodega.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mibodega")
public class ConfiguracionControlador {
    
    @GetMapping("/configuracion")
    public String mostrarconfiguracion() {
        return "miBodega_configuracion";  
    }
    
}