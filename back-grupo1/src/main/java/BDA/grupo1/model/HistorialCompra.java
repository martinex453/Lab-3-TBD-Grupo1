package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "historial_compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialCompra {
    @Id
    private ObjectId id;

    private Integer idCliente;
    private List<Compra> compras;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Compra {
        private Integer idOrden;
        private Date fecha;
        private String estado;
        private Double valorTotal;
        private List<Producto> productos;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Producto {
            private Integer idProducto;
            private Integer cantidad;
            private Double precio;
            private Integer categoria;
        }
    }
}
