package BDA.grupo1.authentication.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Se deshabilita la proteccion CSRF (Cross-Site Request Forgery), ya que JWT proporciona su propia seguridad
                .csrf(csrf -> csrf.disable())
                // Configuración de las reglas de autorización para las solicitudes.
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Permite el acceso sin autenticación a la ruta para iniciar sesión y crear_cuenta
                                .requestMatchers("/authenticate/login")
                                .permitAll()
                                .requestMatchers(HttpMethod.POST, "/cliente/crear_cuenta")
                                .permitAll()
                                // Permite el acceso a todas las demás rutas (puedes ajustarlo según tu lógica).
                                .anyRequest()
                                .permitAll()
                )
                // Configuración de la gestión de sesiones.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Configura el proveedor de autenticación definido previamente para la validación de usuarios.
                .authenticationProvider(authenticationProvider)
                // Agrega el filtro de autenticación JWT antes del filtro de autenticación de usuario/contraseña estándar.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // Construye y retorna la configuración de seguridad.
        return httpSecurity.build();
    }
}
