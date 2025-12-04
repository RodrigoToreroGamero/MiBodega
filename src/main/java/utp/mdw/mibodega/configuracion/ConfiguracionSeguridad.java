package utp.mdw.mibodega.configuracion;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class ConfiguracionSeguridad {

    private final FiltroAutenticacionJwt filtroAutenticacionJwt;

    public ConfiguracionSeguridad(FiltroAutenticacionJwt filtroAutenticacionJwt) {
        this.filtroAutenticacionJwt = filtroAutenticacionJwt;
    }

    @Bean
    public AuthenticationManager autenticador(AuthenticationConfiguration configuracion) throws Exception {
        return configuracion.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder codificadorContrasenia() {
        return new BCryptPasswordEncoder(12); // 12 rondas de hashing para la contraseña
    }

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // JWT NO REQUIERE CSRF
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(peticion -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:8080"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(List.of("*"));
            return config;
        }))
                //CONFIGURA CARACTERÍSTICAS DE SEGURIDAD      
                .authorizeHttpRequests(auth -> {
                    //ENDPOINT DE ACCESO LIBRE                    
                    auth.requestMatchers("/api/autenticacion/login", "/mibodega/login", "/css/**", "/js/**", "/SVG/**", "/favicon.ico", "/error").permitAll();
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/vendedor/**").hasAnyRole("VENDEDOR", "ADMIN");
                    //ENDPOINT CON ACCESO CONTROLADO POR SUTENTICACIÓN
                    auth.anyRequest().authenticated();
                })
                /*FORMULARIO LOGIN DEFAULT DE ACCESO LIBRE      
                .formLogin(form -> form
                        .loginPage("/mibodega/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureUrl("/mibodega/login?error")
                        //MANEJADOR (HANDLER) DE EVENTO SUCCESS
                        .successHandler(successHandler())
                        .permitAll()
                
                
                )                
                .logout(logout -> logout
                        .logoutUrl("/mibodega/logout")
                        .logoutSuccessUrl("/mibodega/login?logout")
                        .permitAll()
                )
                 */
                //ADMINISTRANDO LA SESIÓN DE USUARIO                
                .sessionManagement(session -> session
                //REGISTRO DE LA SESIÓN DE USUARIO
                //CREA O USA SESIÓN EXISTENTE SIN ESTADO
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                /* SI LA SESIÓN ES INVÁLIDA ¿A DÓNDE REDIRIGE?
                        .invalidSessionUrl("/mibodega/login") 
                        // CANTIDAD DE SESIONES DE UN USUARIO A MENOS QUE SEA APP MULTIPLATAFORMA
                        .maximumSessions(1)            
                        // SI SE CUMPLE EL TIEMPO DE LA SESIÓN
                        .expiredUrl("/mibodega/login")                        
                        .sessionRegistry(sessionRegistry())                        
                        .and()
                        //PROTECCIÓN CONTRA VULNERABILIDAD
                        //migrateSession (GENERA OTRO ID DE SESION, COPIA LOS DATOS DE LA SESIÓN
                        .sessionFixation(fixation -> fixation
                                //newSession (CREA UNA NUEVA SESIÓN EN BLANCO, NO COPIA LOS DATOS DE LA SESION)
                                //none (NO HACE NADA, NO ES RECOMENDABLE) 
                                .migrateSession()                
                        ) 
                 */
                )
                .exceptionHandling(e -> e
                    .authenticationEntryPoint((peticion, respuesta, excepcion) -> {
                        String aceptar = peticion.getHeader("Accept");
                        if (aceptar != null && aceptar.contains("text/html")) {
                         respuesta.sendRedirect("/mibodega/login");
                        } else {
                            respuesta.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado");
                        }

                    })
                )
                .addFilterBefore(filtroAutenticacionJwt, UsernamePasswordAuthenticationFilter.class)
                //ESTABLECE LA CONFIGURACIÓN
                .build();
    }

    //MÉTODO PARA REGISTRO DE LA SESIÓN DE USUARIO
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /*CREA EL HANDLER DEL FORM SUCCESS
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            //SI ES AUTENTICADO OK DIRIGIR HACIA endpoint index
            response.sendRedirect("/");
        });
    }
*/
}
