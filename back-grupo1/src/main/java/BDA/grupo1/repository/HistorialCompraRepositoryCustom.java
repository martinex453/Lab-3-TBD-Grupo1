package BDA.grupo1.repository;

import org.bson.Document;

import java.util.List;


public interface HistorialCompraRepositoryCustom {
    List<Document> obtenerCategoriasMasFrecuentes(Integer idUsuario);
}
