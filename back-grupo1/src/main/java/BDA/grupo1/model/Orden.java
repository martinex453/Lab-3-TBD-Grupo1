package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orden {
    private Long id_orden;
    private Timestamp fecha_orden;
    private String estado;
    private Integer id_cliente;
    private Double total;
    private Point ubicacion_entrega;
}

