package ar.com.crivelli.facturas.controller;
import ar.com.crivelli.facturas.model.Proveedor; import ar.com.crivelli.facturas.repository.ProveedorRepository; import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/proveedores") public class ProveedorController { private final ProveedorRepository repo; public ProveedorController(ProveedorRepository r){repo=r;}
 @GetMapping public List<Proveedor> listar(){return repo.findAll();} @PostMapping public ResponseEntity<Proveedor> crear(@Valid @RequestBody Proveedor p){return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(p));}}
