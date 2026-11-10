package ar.com.crivelli.facturas.service;

import ar.com.crivelli.facturas.model.EstadoFactura;
import ar.com.crivelli.facturas.model.Factura;
import ar.com.crivelli.facturas.model.Proveedor;
import ar.com.crivelli.facturas.repository.FacturaRepository;
import ar.com.crivelli.facturas.repository.ProveedorRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepository facturas;

    @Mock
    private ProveedorRepository proveedores;

    @InjectMocks
    private FacturaService service;

    @Test
    void creaFacturaPendiente() {

        Proveedor proveedor = new Proveedor(
                "30-12345678-9",
                "Transportes SA",
                "facturacion@transportes.com",
                "11-5555-5555"
        );

        Factura factura = new Factura();

        factura.setImporte(
                new BigDecimal("8500000")
        );

        when(proveedores.findById(1L))
                .thenReturn(Optional.of(proveedor));

        when(facturas.save(any()))
                .thenAnswer(invocacion ->
                        invocacion.getArgument(0));

        Factura resultado =
                service.crear(1L, factura);

        assertEquals(
                EstadoFactura.PENDIENTE,
                resultado.getEstado()
        );

        assertEquals(
                "GERENCIA",
                resultado.getNivelAprobacion()
        );
    }

    @Test
    void montoSuperiorRequiereDireccion() {

        Factura factura = new Factura();

        factura.setImporte(
                new BigDecimal("12500000")
        );

        assertEquals(
                "DIRECCION",
                factura.getNivelAprobacion()
        );
    }

    @Test
    void noPermiteEvaluarDosVeces() {

        Factura factura = new Factura();

        factura.setEstado(
                EstadoFactura.APROBADA
        );

        when(facturas.findById(1L))
                .thenReturn(Optional.of(factura));

        assertThrows(
                IllegalStateException.class,
                () -> service.cambiarEstado(
                        1L,
                        EstadoFactura.RECHAZADA
                )
        );
    }
}