package BDA.grupo1.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistorialCompraRepositoryCustomImp implements HistorialCompraRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public HistorialCompraRepositoryCustomImp(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Document> obtenerCategoriasMasFrecuentes(Integer idUsuario) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("idCliente").is(idUsuario)), // Filtrar por usuario
                Aggregation.unwind("compras"), // Descomponer el array de compras
                Aggregation.unwind("compras.productos"), // Descomponer el array de productos
                Aggregation.group("compras.productos.categoria") // Agrupar por categoría
                        .count().as("frecuencia"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "frecuencia")), // Ordenar por frecuencia
                Aggregation.limit(2) // Limitar a las dos categorías más frecuentes
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "historial_compras", Document.class);
        return results.getMappedResults();
    }
}
