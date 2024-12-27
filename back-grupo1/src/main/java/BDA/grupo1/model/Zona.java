package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zona {
    private Long id_zona;
    private String nombre;
    private String tipo;
    private Polygon zona_geom;
}
