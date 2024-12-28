package BDA.grupo1.repository;

import BDA.grupo1.model.HistorialCompra;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialCompraRepository extends MongoRepository<HistorialCompra, String> {
    HistorialCompra findByIdCliente(Integer idCliente);
}
