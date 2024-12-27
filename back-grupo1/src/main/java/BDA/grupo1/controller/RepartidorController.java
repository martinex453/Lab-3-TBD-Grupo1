package BDA.grupo1.controller;


import BDA.grupo1.model.Zona;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.RepartidorService;
import BDA.grupo1.model.Repartidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
public class RepartidorController {

    @Autowired
    private RepartidorService repartidorService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @PostMapping("/repartidor/crear")
    public Repartidor crearRepartidor(@RequestBody Repartidor repartidor, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        return repartidorService.crear(repartidor);
    }

    @GetMapping("/repartidor/All")
    public List<Repartidor> getAllRepartidor(){return repartidorService.getAll();}

    @PostMapping("/repartidor/update/{id}")
    public String updateRepartidor(@RequestBody Repartidor repartidor, @PathVariable Integer id, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        return repartidorService.update(repartidor,id);
    }

    @DeleteMapping("/repartidor/delete/{id}")
    public void deleteRepartidor(@PathVariable Integer id, @RequestParam Integer id_cliente){
        clienteSesionService.crear(id_cliente);
        repartidorService.delete(id);
    }

    @GetMapping("/repartidor/getByZona/{id}")
    public List<Repartidor> getRepartidorByZona(@PathVariable Long id){
        return repartidorService.getRepartidorByZona(id);
    }

    @GetMapping("/repartidor/random")
    public Integer getRandomRepartidor(){
        return repartidorService.getRandom();
    }
}
