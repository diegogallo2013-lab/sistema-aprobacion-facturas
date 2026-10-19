package ar.com.crivelli.facturas.model;

public abstract class Usuario {

    private Long id;
    private String nombre;
    private String email;

    public Usuario(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public abstract boolean puedeAprobar(Factura factura);

    public abstract String getRol();
}