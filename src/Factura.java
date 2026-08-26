public class Factura {

    private int id;
    private String proveedor;
    private double importe;

    public Factura(int id, String proveedor, double importe){
        this.id = id;
        this.proveedor = proveedor;
        this.importe = importe;
    }

    public String getProveedor(){
        return proveedor;
    }
}