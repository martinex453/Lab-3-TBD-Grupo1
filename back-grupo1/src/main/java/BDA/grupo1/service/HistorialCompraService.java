package BDA.grupo1.service;

import BDA.grupo1.model.HistorialCompra;
import BDA.grupo1.model.Producto;
import BDA.grupo1.repository.HistorialCompraRepository;
import BDA.grupo1.repository.HistorialCompraRepositoryCustom;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistorialCompraService {

    @Autowired
    private HistorialCompraRepository historialCompraRepository;

    @Autowired
    private HistorialCompraRepositoryCustom historialCompraRepositoryCustom;

    @Autowired
    private ProductoService productoService;

    public HistorialCompra guardarHistorialCompra(Integer idUsuario, HistorialCompra.Compra nuevaCompra) {

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

        return historialCompraRepository.save(historial);
    }


    public HistorialCompra buscarHistorialCompra(Integer idUsuario) {
        return historialCompraRepository.findByIdCliente(idUsuario);
    }

    public HistorialCompra actualizarEstadoCompra(Integer idUsuario, Integer idOrden, String nuevoEstado) {
        HistorialCompra historial = historialCompraRepository.findByIdCliente(idUsuario);

        // Si no existe el historial de compras
        if (historial == null) {
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
        return historialCompraRepository.save(historial);
    }

    public List<Producto> obtenerCategoriasMasFrecuentes(Integer idUsuario) {

        List<Document> categoriasFrecuentes = historialCompraRepositoryCustom.obtenerCategoriasMasFrecuentes(idUsuario);
        List<Integer> categoriaIds = new ArrayList<>();

        for (Document doc : categoriasFrecuentes) {
            Integer categoriaId = doc.getInteger("_id"); // '_id' contiene el ID de la categoría
            if (categoriaId != null) {
                categoriaIds.add(categoriaId);
            }
        }

        List<Producto> productos = new ArrayList<>();
        // Obtener productos random con ids de categorias
        for(Integer categoria : categoriaIds) {
            List<Producto> productosCat = productoService.getProductosAleatoreosByCategoria(categoria);
            productos.addAll(productosCat);
        }


        return productos;
    }


}
