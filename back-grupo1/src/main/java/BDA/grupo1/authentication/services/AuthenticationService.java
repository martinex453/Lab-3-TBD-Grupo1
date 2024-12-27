package BDA.grupo1.authentication.services;


import BDA.grupo1.authentication.entities.AuthenticationResponse;
import BDA.grupo1.authentication.entities.LoginRequest;
import BDA.grupo1.authentication.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    // Método encargado de autenticar a un usuario
    public AuthenticationResponse authenticate(LoginRequest request) {
        // El AuthenticationManager se encarga de verificar las credenciales de usuario (correo y contraseña)
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getContrasena()
                    )
            );
            // Si la autenticación es exitosa, obtenemos el usuario de la base de datos, y luego obtenemos el token para retornarlo
            User user = userService.getUserByEmail(request.getEmail());
            String jwtToken = jwtService.generateToken(user.generateExtraClaims(), user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            // En caso de que ocurra un error durante el proceso de autenticación
            System.out.println("Error en la autenticación: " + e.getMessage());
            throw new RuntimeException("Autenticación fallida");
        }
    }
}
