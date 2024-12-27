package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orden_repartidor {
    private Long id;
    private Integer id_orden;
    private Integer id_repartidor;
}
