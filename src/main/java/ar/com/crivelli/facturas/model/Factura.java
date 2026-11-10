package ar.com.crivelli.facturas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "numero_factura",
            nullable = false,
            length = 50
    )
    private String numeroFactura;

    @ManyToOne(
            optional = false,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "proveedor_id",
            nullable = false
    )
    private Proveedor proveedor;

    @Column(
            name = "fecha_emision",
            nullable = false
    )
    private LocalDate fechaEmision = LocalDate.now();

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @NotNull
    @DecimalMin("0.00")
    @Column(
            name = "importe_neto",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal importeNeto = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.00")
    @Column(
            name = "iva",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal iva = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.01")
    @Column(
            name = "importe_total",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal importeTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "estado",
            nullable = false,
            length = 30
    )
    private EstadoFactura estado = EstadoFactura.PENDIENTE;

    @Column(
            name = "observaciones",
            length = 500
    )
    private String observaciones;

    public Factura() {
    }

    public Long getId() {
        return id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(BigDecimal importeNeto) {
        this.importeNeto = importeNeto;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /*
     * Métodos de compatibilidad con el proyecto anterior.
     *
     * FacturaService, MenuConsola y los tests todavía utilizan
     * getImporte() y setImporte().
     *
     * Ahora "importe" representa el importe total de la factura.
     */

    public BigDecimal getImporte() {
        return importeTotal;
    }

    public void setImporte(BigDecimal importe) {
        this.importeTotal = importe;
    }

    /*
     * Regla de negocio:
     *
     * Hasta $10.000.000 -> GERENCIA
     * Más de $10.000.000 -> DIRECCION
     */

    @Transient
    public String getNivelAprobacion() {

        if (importeTotal == null) {
            return "GERENCIA";
        }

        BigDecimal limite =
                new BigDecimal("10000000");

        if (importeTotal.compareTo(limite) > 0) {
            return "DIRECCION";
        }

        return "GERENCIA";
    }

    /*
     * Calcula automáticamente el total:
     * importe neto + IVA.
     */

    public void calcularTotal() {

        BigDecimal neto =
                importeNeto != null
                        ? importeNeto
                        : BigDecimal.ZERO;

        BigDecimal importeIva =
                iva != null
                        ? iva
                        : BigDecimal.ZERO;

        this.importeTotal =
                neto.add(importeIva);
    }
}