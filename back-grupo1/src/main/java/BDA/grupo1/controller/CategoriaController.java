package BDA.grupo1.controller;

import BDA.grupo1.model.Categoria;
import BDA.grupo1.service.CategoriaService;
import BDA.grupo1.service.ClienteSesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @GetMapping("/categoria/All")
    public List<Categoria> getAll() {
        return categoriaService.findAll();
    }

    @PostMapping("/categoria/crear")
    public Categoria create(@RequestBody Categoria categoria, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return categoriaService.crear(categoria);
    }

    @PostMapping("/categoria/update/{id}")
    public String update( @RequestBody Categoria categoria,@PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return categoriaService.update(categoria,id);
    }

    @DeleteMapping("/categoria/delete/{id}")
    public void delete(@PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        categoriaService.delete(id);
    }
}
