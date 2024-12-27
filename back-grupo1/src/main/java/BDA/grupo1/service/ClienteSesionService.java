package BDA.grupo1.service;

import BDA.grupo1.model.ClienteSesion;
import BDA.grupo1.repository.ClienteSesionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteSesionService {

    @Autowired
    private ClienteSesionRepository clienteSesionRepository;

    // servicio para añadir el id del usuario en la tabla
    public Integer crear(Integer id) {
        clienteSesionRepository.deleteAll(); // eliminar filas anteriores
        return clienteSesionRepository.crear(id); // insertar el id
    }

    // servicio para eliminar los elementos de la tabla
    public void delete() {
        clienteSesionRepository.deleteAll();
    }
}
