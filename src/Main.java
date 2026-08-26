public class Main {

    public static void main(String[] args) {

        System.out.println("Sistema de Gestión de Facturas");

        Factura factura = new Factura(
                1,
                "Proveedor ABC",
                500000
        );

        System.out.println(
                "Factura cargada: " +
                factura.getProveedor()
        );
    }
}