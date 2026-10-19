package ar.com.crivelli.facturas.model;

import java.math.BigDecimal;

public class Director extends Usuario {

    private static final BigDecimal LIMITE_APROBACION =
            new BigDecimal("10000000");

    public Director(Long id, String nombre, String email) {
        super(id, nombre, email);
    }

    @Override
    public boolean puedeAprobar(Factura factura) {
        return factura.getImporte() != null
                && factura.getImporte().compareTo(LIMITE_APROBACION) > 0;
    }

    @Override
    public String getRol() {
        return "DIRECCION";
    }
}