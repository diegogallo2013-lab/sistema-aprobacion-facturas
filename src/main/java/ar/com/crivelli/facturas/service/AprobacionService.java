package ar.com.crivelli.facturas.service;

import ar.com.crivelli.facturas.model.EstadoFactura;
import ar.com.crivelli.facturas.model.Factura;
import ar.com.crivelli.facturas.model.Usuario;
import ar.com.crivelli.facturas.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AprobacionService {

    private final FacturaRepository facturas;

    public AprobacionService(FacturaRepository facturas) {
        this.facturas = facturas;
    }

    public Factura aprobar(Long facturaId, Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "El usuario no puede ser nulo");
        }

        Factura factura = facturas.findById(facturaId)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Factura inexistente con ID: "
                                        + facturaId));

        if (factura.getEstado() != EstadoFactura.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo pueden aprobarse facturas pendientes");
        }

        if (!usuario.puedeAprobar(factura)) {
            throw new IllegalStateException(
                    "El usuario con rol "
                            + usuario.getRol()
                            + " no puede aprobar una factura de $"
                            + factura.getImporte()
                            + ". Se requiere: "
                            + factura.getNivelAprobacion());
        }

        factura.setEstado(EstadoFactura.APROBADA);

        return facturas.save(factura);
    }
}