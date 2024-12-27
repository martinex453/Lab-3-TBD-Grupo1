package BDA.grupo1.authentication.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la solicitud de inicio de sesión (login) enviada por el cliente.
 *
 * Esta clase contiene los datos que el usuario necesita proporcionar para autenticarse,
 * como su correo electrónico (email) y su contraseña (contrasena).
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String contrasena;
}
