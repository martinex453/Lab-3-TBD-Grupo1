package BDA.grupo1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private Integer idCliente;
    private List<Compra> compras;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Compra{
        private Integer idOrden;
        private Date fecha;
        private List<Producto> productos;
        private String estado;
        private Double valorTotal;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Producto{
            private Integer idProducto;
            private Integer cantidad;
            private Double precio;
            private Integer categoria;
        }
    }
}
