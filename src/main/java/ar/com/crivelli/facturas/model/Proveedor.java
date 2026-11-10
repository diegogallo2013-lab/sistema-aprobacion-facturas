package ar.com.crivelli.facturas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "cuit", nullable = false, unique = true, length = 20)
    private String cuit;

    @NotBlank
    @Column(name = "razon_social", nullable = false, length = 100)
    private String razonSocial;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    public Proveedor() {
    }

    public Proveedor(
            String cuit,
            String razonSocial,
            String email,
            String telefono) {

        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.email = email;
        this.telefono = telefono;
        this.activo = true;
    }

    public Long getId() {
        return id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Compatibilidad temporal con el código anterior
    public String getNombre() {
        return razonSocial;
    }
}