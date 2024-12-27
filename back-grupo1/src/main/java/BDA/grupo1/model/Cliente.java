package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private Long id_cliente;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String contrasena;
    private String rol;
}
