package ar.com.crivelli.facturas.service;

import ar.com.crivelli.facturas.model.Proveedor;
import ar.com.crivelli.facturas.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedores;

    public ProveedorService(ProveedorRepository proveedores) {
        this.proveedores = proveedores;
    }

    public Proveedor crear(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del proveedor no puede estar vacio");
        }

        Proveedor proveedor = new Proveedor(nombre);

        return proveedores.save(proveedor);
    }

    public List<Proveedor> listar() {
        return proveedores.findAll();
    }

    public Proveedor buscarPorId(Long id) {
        return proveedores.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Proveedor inexistente con ID: " + id));
    }
}