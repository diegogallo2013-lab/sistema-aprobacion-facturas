package ar.com.crivelli.facturas.controller;
import ar.com.crivelli.facturas.model.*; import ar.com.crivelli.facturas.service.FacturaService; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/facturas") public class FacturaController { private final FacturaService service; public FacturaController(FacturaService s){service=s;}
 @GetMapping public List<Factura> listar(){return service.listar();}
 @GetMapping("/estado/{estado}") public List<Factura> estado(@PathVariable EstadoFactura estado){return service.porEstado(estado);}
 @GetMapping("/proveedor/{id}") public List<Factura> proveedor(@PathVariable Long id){return service.porProveedor(id);}
 @PostMapping public ResponseEntity<Factura> crear(@RequestParam Long proveedorId,@Valid @RequestBody Factura f){return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(proveedorId,f));}
 @PutMapping("/{id}/aprobar") public Factura aprobar(@PathVariable Long id){return service.cambiarEstado(id,EstadoFactura.APROBADA);}
 @PutMapping("/{id}/rechazar") public Factura rechazar(@PathVariable Long id){return service.cambiarEstado(id,EstadoFactura.RECHAZADA);}}
