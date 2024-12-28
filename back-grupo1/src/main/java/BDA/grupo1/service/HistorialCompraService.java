package BDA.grupo1.service;

import BDA.grupo1.model.HistorialCompra;
import BDA.grupo1.model.Producto;
import BDA.grupo1.repository.HistorialCompraRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistorialCompraService {

    @Autowired
    private HistorialCompraRepository historialCompraRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ProductoService productoService;

    public HistorialCompra guardarHistorialCompra(Integer idUsuario, HistorialCompra.Compra nuevaCompra) {
        // Buscar el historial de compras del usuario
        HistorialCompra historial = historialCompraRepository.findByIdCliente(idUsuario);

        // Si no existe el historial de compras, creamos uno nuevo
        if (historial == null) {
            historial = new HistorialCompra();
            historial.setIdCliente(idUsuario);
            historial.setCompras(new ArrayList<>());
        }

        // Comprobar si la compra ya está en el historial, usando el idOrden como criterio único
        boolean compraExistente = historial.getCompras().stream()
                .anyMatch(compra -> compra.getIdOrden().equals(nuevaCompra.getIdOrden()));

        if (compraExistente) {
            // Si la compra ya existe, la reemplazamos
            for (int i = 0; i < historial.getCompras().size(); i++) {
                if (historial.getCompras().get(i).getIdOrden().equals(nuevaCompra.getIdOrden())) {
                    // Reemplazar la compra existente por la nueva
                    historial.getCompras().set(i, nuevaCompra);
                    break;
                }
            }
        } else {
            // Si la compra no existe, la agregamos
            historial.getCompras().add(nuevaCompra);
        }

        // Guardar el historial actualizado
        return historialCompraRepository.save(historial);
    }


    public HistorialCompra buscarHistorialCompra(Integer idUsuario) {
        return historialCompraRepository.findByIdCliente(idUsuario);
    }

    public HistorialCompra actualizarEstadoCompra(Integer idUsuario, Integer idOrden, String nuevoEstado) {
        // Buscar el historial de compras del usuario
        HistorialCompra historial = historialCompraRepository.findByIdCliente(idUsuario);

        // Si no existe el historial de compras, devolvemos null o lanzamos una excepción según el caso
        if (historial == null) {
            // Podrías lanzar una excepción o devolver un valor adecuado (null en este caso)
            return null;
        }

        // Buscar la compra en el historial usando el idOrden
        for (HistorialCompra.Compra compra : historial.getCompras()) {
            if (compra.getIdOrden().equals(idOrden)) {
                // Actualizar el estado de la compra encontrada
                compra.setEstado(nuevoEstado);
                break;
            }
        }

        // Guardar el historial actualizado
        return historialCompraRepository.save(historial);
    }

    public List<Producto> obtenerCategoriasMasFrecuentes(Integer idUsuario) {
        // Crear el pipeline de agregación
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("_id").is(idUsuario)), // Filtrar por usuario
                Aggregation.unwind("compras"), // Descomponer el array de compras
                Aggregation.unwind("compras.productos"), // Descomponer el array de productos
                Aggregation.group("compras.productos.categoria") // Agrupar por categoría
                        .count().as("frecuencia"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "frecuencia")), // Ordenar por frecuencia
                Aggregation.limit(2) // Limitar a las dos categorías más frecuentes
        );

        System.out.println(aggregation);

        // Ejecutar el pipeline
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "historial_compras", Document.class);
        System.out.println(results);
        // Retornar las categorías más frecuentes
        List<Document> categoriasFrecuentes = results.getMappedResults();
        List<Integer> categoriaIds = new ArrayList<>();

        for (Document doc : categoriasFrecuentes) {
            Integer categoriaId = doc.getInteger("_id"); // '_id' contiene el ID de la categoría
            if (categoriaId != null) {
                categoriaIds.add(categoriaId);
            }
        }
        System.out.println(categoriaIds);

        List<Producto> productos = new ArrayList<>();
        // Retornar la lista con los Ids de las categorías
        for(Integer categoria : categoriaIds) {
            List<Producto> productosCat = productoService.getProductosAleatoreosByCategoria(categoria);
            System.out.println(productosCat);
            productos.addAll(productosCat);
        }


        return productos;
    }


}
