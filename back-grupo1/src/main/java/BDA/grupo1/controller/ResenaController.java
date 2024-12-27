package BDA.grupo1.controller;

import BDA.grupo1.model.Resena;
import BDA.grupo1.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @PostMapping
    public Resena crearResena(@RequestBody Resena resena) {
        return resenaService.crearResena(resena);
    }

    @GetMapping
    public List<Resena> obtenerResenas() {
        return resenaService.obtenerResenas();
    }

    @GetMapping("/{id}")
    public Resena obtenerResenaPorId(@PathVariable String id) {
        return resenaService.obtenerResenaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarResena(@PathVariable String id) {
        resenaService.eliminarResena(id);
    }
}
