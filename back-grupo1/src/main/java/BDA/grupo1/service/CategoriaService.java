package BDA.grupo1.service;

import BDA.grupo1.model.Categoria;
import BDA.grupo1.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    // Servicio para crear una categoría
    public Categoria crear(Categoria categoria) {
        return categoriaRepository.crear(categoria);  // Llama al repositorio para crear la categoría.
    }

    // Serivicio para encontrar todas las categorías
    public List<Categoria> findAll() {
        return categoriaRepository.getAll();  // Llama al repositorio para obtener todas las categorías.
    }

    //  Servicio para actualizar una categoría
    public String update(Categoria categoria, Integer id) {
        return categoriaRepository.update(categoria, id);  // Llama al repositorio para actualizar la categoría.
    }

    // Servicio para eliminar una categoría de la base de datos
    public void delete(Integer id) {
        categoriaRepository.delete(id);  // Llama al repositorio para eliminar la categoría por ID.
    }
}
