package BDA.grupo1.service;

import BDA.grupo1.model.Resena;
import BDA.grupo1.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public Resena crearResena(Resena resena) {
        Integer IdUser = resena.getIdUsuario();
        Integer IdProducto = resena.getIdProducto();
        Resena resenaEncotrada = resenaRepository.findByIdUsuarioAndIdProducto(IdUser,IdProducto);
        if (resenaEncotrada == null) {
            return resenaRepository.save(resena);
        }else {
            return null;
        }

    }

    public List<Resena> obtenerResenas() {
        return resenaRepository.findAll();
    }

    public Resena obtenerResenaPorId(String id) {
        return resenaRepository.findById(id).orElse(null);
    }

    public void eliminarResena(String id) {
        resenaRepository.deleteById(id);
    }
}
