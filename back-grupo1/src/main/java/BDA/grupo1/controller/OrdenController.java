package BDA.grupo1.controller;

import BDA.grupo1.model.Orden;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin
public class OrdenController {

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @PostMapping("/orden/crear")
    public Orden crearOrden(@RequestBody Orden orden, @RequestParam Integer id) {
        clienteSesionService.crear(id); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return ordenService.crear(orden);
    }

    @GetMapping("/orden/All")
    public List<Orden> getAllOrden() {
        return ordenService.findAll();
    }

    @PostMapping("/orden/update/{id}")
    public String updateOrden(@RequestBody Orden orden, @PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return ordenService.update(orden,id);
    }

    @DeleteMapping("/orden/delete/{id}")
    public void deleteOrden(@PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        ordenService.delete(id);
    }

    @GetMapping("/orden/get/{id}")
    public List<Orden>  getOrdenByUserId(@PathVariable Integer id){
        return ordenService.getOrdenByUserId(id);
    }

    @GetMapping("/orden/getOrderid/{id}")
    public Orden getOrdenById(@PathVariable Integer id){
        return ordenService.getOrdenById(id);
    }

    @GetMapping("/orden/pagina/{page}/{pageSize}")
    public List<Orden> OrdenPages(@PathVariable Integer page, @PathVariable Integer pageSize) {
        return ordenService.getOrdersPage(page,pageSize);
    }

    @GetMapping("/orden/pagina/user/{user}/{page}/{pageSize}")
    public List<Orden> OrdenPagesUser(@PathVariable Integer user, @PathVariable Integer page, @PathVariable Integer pageSize) {
        return ordenService.getOrdersPageUser(user,page,pageSize);
    }

    @GetMapping("/orden/timestamp")
    public Integer getOrdenTimestamp() {
        return ordenService.getOrdenIDByTimestamp();
    }

    @GetMapping("/orden/point_restricted")
    public Boolean getIfPointIsInRestrictedZone(@RequestParam Double x, @RequestParam Double y){
        return ordenService.getIfPointIsInRestrictedZone(x,y);
    }

    @GetMapping("/orden/TotalPages/{pageSize}")
    public Integer getOrdenTotalPages(@PathVariable Integer pageSize) {
        return ordenService.getOrdersTotalPages(pageSize);
    }

    @GetMapping("/orden/TotalPages/user/{User}/{pageSize}")
    public Integer getOrdenTotalPagesUser(@PathVariable Integer User, @PathVariable Integer pageSize) {
        return ordenService.getOrdersTotalPagesUser(User,pageSize);
    }
}
