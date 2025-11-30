package utp.mdw.mibodega.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mibodega")
public class LoginControlador {
    
    @GetMapping("/login")
    public String login() {
        return "miBodega_login";
    }
}
