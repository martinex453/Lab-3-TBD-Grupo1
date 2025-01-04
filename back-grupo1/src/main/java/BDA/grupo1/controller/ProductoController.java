package BDA.grupo1.controller;

import BDA.grupo1.model.Producto;
import BDA.grupo1.service.ClienteSesionService;
import BDA.grupo1.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteSesionService clienteSesionService;

    @PostMapping("/producto/crear")
    public Producto crearProducto(@RequestBody Producto producto, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return productoService.crear(producto);
    }

    @GetMapping("/producto/All")
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }

    @PostMapping("/producto/update/{id}")
    public String updateProducto(@RequestBody Producto producto, @PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        return productoService.update(producto,id);
    }

    @GetMapping("/producto/get/{id}")
    public Producto getproductoById(@PathVariable Integer id){
        Producto prod = productoService.getproductoById(id);
        System.out.println(prod);
        return prod;
    }

    @DeleteMapping("/producto/delete/{id}")
    public void deleteProducto(@PathVariable Integer id, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        productoService.delete(id);
    }

    @GetMapping("/producto/pagina/{page}/{pageSize}")
    public List<Producto> productoPages(@PathVariable Integer page, @PathVariable Integer pageSize) {
        return productoService.getProductoPage(page,pageSize);
    }

    @PutMapping("/producto/updateStock/{id}/{stock}")
    public void updateProductoStock(@PathVariable Integer id, @PathVariable Integer stock, @RequestParam Integer id_cliente) {
        clienteSesionService.crear(id_cliente); // Registra la sesión del cliente para que sea usado por el trigger de registro de querys
        productoService.updateProductoStock(id,stock);
    }

    @GetMapping("/producto/getTotalPages/{pageSize}")
    public Integer getTotalPages(@PathVariable Integer pageSize) {
        return productoService.getTotalPages(pageSize);
    }
}
