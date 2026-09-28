package ar.com.crivelli.facturas.model;
import jakarta.persistence.*; import jakarta.validation.constraints.NotBlank;
@Entity @Table(name="proveedores") public class Proveedor {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank @Column(nullable=false,length=100) private String nombre;
 public Proveedor(){} public Proveedor(String nombre){this.nombre=nombre;}
 public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String nombre){this.nombre=nombre;}
}
