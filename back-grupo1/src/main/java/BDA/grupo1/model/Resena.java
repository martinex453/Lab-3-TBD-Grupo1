package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "resenas") // Define el nombre de la colección en MongoDB
public class Resena {

    @Id
    private String id; // ObjectID, se generará automáticamente si no se establece

    private Integer idUsuario; // ID del usuario asociado a la reseña
    private Integer idProducto; // ID del producto al que pertenece la reseña
    private Integer puntuacion; // Puntuación (por ejemplo, 1-5 estrellas)
    private String comentario; // Comentario del usuario sobre el producto
}
