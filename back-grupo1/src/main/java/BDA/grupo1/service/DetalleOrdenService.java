package BDA.grupo1.service;

import BDA.grupo1.model.Cliente;
import BDA.grupo1.model.DetalleOrden;
import BDA.grupo1.repository.ClienteRepository;
import BDA.grupo1.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenService {

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    // servicio para crear un detalle de orden
    public DetalleOrden crear(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.crear(detalleOrden);
    }

    // servicio para obtener todos los detalles de orden
    public List<DetalleOrden> findAll() {
        return detalleOrdenRepository.getAll();
    }

    // servicio para actualizar los datos de un detalle de orden
    public String update(DetalleOrden detalleOrden, Integer id) {
        return detalleOrdenRepository.update(detalleOrden, id);
    }

    // servicio para eliminar un detalle de orden según su identificador
    public void delete(Integer id) {
        detalleOrdenRepository.delete(id);
    }

    // servicio para obtener los detalles de orden ordenados según su identificador
    public List<DetalleOrden> getdetalleOrdenByOrdenId(Integer id_orden) {
        return detalleOrdenRepository.getdetalleOrdenByOrdenId(id_orden);
    }
}
