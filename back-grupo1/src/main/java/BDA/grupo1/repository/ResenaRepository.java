package BDA.grupo1.repository;

import BDA.grupo1.model.Resena;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenaRepository extends MongoRepository<Resena, String> {
    Resena findByIdUsuarioAndIdProducto(Integer idUsuario, Integer idProducto);
}
