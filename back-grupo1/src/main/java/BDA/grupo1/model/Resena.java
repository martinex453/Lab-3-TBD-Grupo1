package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "resenas")
public class Resena {

    @Id
    private ObjectId id;

    private Integer idUsuario;
    private Integer idProducto;
    private Integer puntuacion;
    private String comentario;
}
