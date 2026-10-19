package ar.com.crivelli.facturas.service;

import ar.com.crivelli.facturas.model.EstadoFactura;
import ar.com.crivelli.facturas.model.Factura;
import ar.com.crivelli.facturas.repository.FacturaRepository;
import ar.com.crivelli.facturas.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FacturaService {

    private final FacturaRepository facturas;
    private final ProveedorRepository proveedores;

    public FacturaService(
            FacturaRepository facturas,
            ProveedorRepository proveedores) {

        this.facturas = facturas;
        this.proveedores = proveedores;
    }

    public List<Factura> listar() {
        return facturas.findAll();
    }

    public Factura buscarPorId(Long id) {
        return facturas.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Factura inexistente con ID: " + id));
    }

    public List<Factura> porEstado(EstadoFactura estado) {
        return facturas.findByEstado(estado);
    }

    public List<Factura> porProveedor(Long proveedorId) {
        return facturas.findByProveedorId(proveedorId);
    }

    public Factura crear(Long proveedorId, Factura factura) {

        factura.setProveedor(
                proveedores.findById(proveedorId)
                        .orElseThrow(() ->
                                new NoSuchElementException(
                                        "Proveedor inexistente con ID: "
                                                + proveedorId))
        );

        factura.setEstado(EstadoFactura.PENDIENTE);

        return facturas.save(factura);
    }

    public Factura cambiarEstado(
            Long id,
            EstadoFactura nuevoEstado) {

        Factura factura = buscarPorId(id);

        if (factura.getEstado() != EstadoFactura.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo una factura pendiente puede evaluarse");
        }

        if (nuevoEstado == EstadoFactura.PENDIENTE) {
            throw new IllegalArgumentException(
                    "El nuevo estado debe ser APROBADA o RECHAZADA");
        }

        factura.setEstado(nuevoEstado);

        return facturas.save(factura);
    }

    public List<Factura> ordenarPorImporte() {

        List<Factura> resultado = facturas.findAll();

        resultado.sort(
                Comparator.comparing(Factura::getImporte)
        );

        return resultado;
    }
}