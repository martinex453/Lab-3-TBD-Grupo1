package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa_zona {
    private Long id_empresa_zona;
    private Integer id_zona;
    private Integer id_empresa;
}
