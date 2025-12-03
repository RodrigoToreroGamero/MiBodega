package utp.mdw.mibodega.controlador;

import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/vendedor")
    public String vendedorPage() {
        return "vendedor";
    }
}
