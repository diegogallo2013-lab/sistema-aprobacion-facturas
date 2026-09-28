package ar.com.crivelli.facturas.model;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDateTime;
@Entity @Table(name="facturas") public class Factura {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) @JoinColumn(name="proveedor_id",nullable=false) private Proveedor proveedor;
 @NotNull @DecimalMin("0.01") @Column(nullable=false,precision=15,scale=2) private BigDecimal importe;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private EstadoFactura estado=EstadoFactura.PENDIENTE;
 @Column(nullable=false) private LocalDateTime fecha=LocalDateTime.now();
 public Factura(){} public Long getId(){return id;} public Proveedor getProveedor(){return proveedor;} public void setProveedor(Proveedor p){proveedor=p;}
 public BigDecimal getImporte(){return importe;} public void setImporte(BigDecimal i){importe=i;} public EstadoFactura getEstado(){return estado;} public void setEstado(EstadoFactura e){estado=e;}
 public LocalDateTime getFecha(){return fecha;} public void setFecha(LocalDateTime f){fecha=f;}
 @Transient public String getNivelAprobacion(){return importe!=null && importe.compareTo(new BigDecimal("10000000"))>0?"DIRECCION":"GERENCIA";}
}
