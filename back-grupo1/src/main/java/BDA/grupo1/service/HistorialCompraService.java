package BDA.grupo1.service;

import BDA.grupo1.model.HistorialCompra;
import BDA.grupo1.repository.HistorialCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class HistorialCompraService {

    @Autowired
    private HistorialCompraRepository historialCompraRepository;

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

}
