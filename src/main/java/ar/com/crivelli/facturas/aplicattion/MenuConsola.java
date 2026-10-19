package ar.com.crivelli.facturas.application;

import ar.com.crivelli.facturas.model.Director;
import ar.com.crivelli.facturas.model.EstadoFactura;
import ar.com.crivelli.facturas.model.Factura;
import ar.com.crivelli.facturas.model.Gerente;
import ar.com.crivelli.facturas.model.Proveedor;
import ar.com.crivelli.facturas.model.Usuario;
import ar.com.crivelli.facturas.service.AprobacionService;
import ar.com.crivelli.facturas.service.FacturaService;
import ar.com.crivelli.facturas.service.ProveedorService;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuConsola {

    private final Scanner scanner;
    private final FacturaService facturaService;
    private final ProveedorService proveedorService;
    private final AprobacionService aprobacionService;

    public MenuConsola(
            FacturaService facturaService,
            ProveedorService proveedorService,
            AprobacionService aprobacionService) {

        this.scanner = new Scanner(System.in);
        this.facturaService = facturaService;
        this.proveedorService = proveedorService;
        this.aprobacionService = aprobacionService;
    }

    public void mostrarMenu() {

        int opcion = 0;

        while (opcion != 9) {

            mostrarOpciones();

            try {

                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {

                    case 1:
                        registrarProveedor();
                        break;

                    case 2:
                        registrarFactura();
                        break;

                    case 3:
                        listarFacturas();
                        break;

                    case 4:
                        buscarFactura();
                        break;

                    case 5:
                        aprobarFactura();
                        break;

                    case 6:
                        rechazarFactura();
                        break;

                    case 7:
                        listarPendientes();
                        break;

                    case 8:
                        ordenarPorImporte();
                        break;

                    case 9:
                        System.out.println("Cerrando el sistema...");
                        break;

                    default:
                        System.out.println(
                                "Opcion incorrecta. Ingrese un numero entre 1 y 9.");
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debe ingresar un numero valido.");

            } catch (Exception e) {

                System.out.println(
                        "Error: " + e.getMessage());
            }
        }
    }

    private void mostrarOpciones() {

        System.out.println();
        System.out.println("========================================");
        System.out.println(" SISTEMA DE GESTION DE FACTURAS");
        System.out.println("========================================");
        System.out.println("1. Registrar proveedor");
        System.out.println("2. Registrar factura");
        System.out.println("3. Listar facturas");
        System.out.println("4. Buscar factura");
        System.out.println("5. Aprobar factura");
        System.out.println("6. Rechazar factura");
        System.out.println("7. Mostrar facturas pendientes");
        System.out.println("8. Ordenar facturas por importe");
        System.out.println("9. Salir");
        System.out.println("========================================");
        System.out.print("Seleccione una opcion: ");
    }

    private void registrarProveedor() {

        System.out.println();
        System.out.println("=== REGISTRAR PROVEEDOR ===");

        System.out.print("Nombre del proveedor: ");
        String nombre = scanner.nextLine();

        Proveedor proveedor =
                proveedorService.crear(nombre);

        System.out.println();
        System.out.println(
                "Proveedor registrado correctamente.");
        System.out.println(
                "ID: " + proveedor.getId());
        System.out.println(
                "Nombre: " + proveedor.getNombre());
    }

    private void registrarFactura() {

        System.out.println();
        System.out.println("=== REGISTRAR FACTURA ===");

        List<Proveedor> proveedores =
                proveedorService.listar();

        if (proveedores.isEmpty()) {

            System.out.println(
                    "No existen proveedores. Registre primero un proveedor.");

            return;
        }

        System.out.println("Proveedores disponibles:");

        for (Proveedor proveedor : proveedores) {

            System.out.println(
                    proveedor.getId()
                            + " - "
                            + proveedor.getNombre());
        }

        System.out.print("Ingrese ID del proveedor: ");

        Long proveedorId =
                Long.parseLong(scanner.nextLine());

        System.out.print("Ingrese importe de la factura: $");

        BigDecimal importe =
                new BigDecimal(scanner.nextLine());

        Factura factura = new Factura();
        factura.setImporte(importe);

        Factura facturaGuardada =
                facturaService.crear(
                        proveedorId,
                        factura);

        System.out.println();
        System.out.println(
                "Factura registrada correctamente.");

        mostrarFactura(facturaGuardada);
    }

    private void listarFacturas() {

        List<Factura> facturas =
                facturaService.listar();

        if (facturas.isEmpty()) {

            System.out.println(
                    "No hay facturas registradas.");

            return;
        }

        for (Factura factura : facturas) {
            mostrarFactura(factura);
        }
    }

    private void buscarFactura() {

        System.out.print("Ingrese ID de factura: ");

        Long id =
                Long.parseLong(scanner.nextLine());

        Factura factura =
                facturaService.buscarPorId(id);

        mostrarFactura(factura);
    }

    private void aprobarFactura() {

        System.out.println();
        System.out.println("=== APROBAR FACTURA ===");

        List<Factura> pendientes =
                facturaService.porEstado(
                        EstadoFactura.PENDIENTE);

        if (pendientes.isEmpty()) {

            System.out.println(
                    "No existen facturas pendientes.");

            return;
        }

        System.out.println("Facturas pendientes:");

        for (Factura factura : pendientes) {
            mostrarFactura(factura);
        }

        System.out.print("Ingrese ID de factura: ");

        Long facturaId =
                Long.parseLong(scanner.nextLine());

        System.out.println();
        System.out.println("Seleccione rol del aprobador:");
        System.out.println("1. GERENCIA");
        System.out.println("2. DIRECCION");
        System.out.print("Opcion: ");

        int rol =
                Integer.parseInt(scanner.nextLine());

        Usuario usuario;

        if (rol == 1) {

            usuario = new Gerente(
                    1L,
                    "Aprobador Gerencia",
                    "gerencia@empresa.com");

        } else if (rol == 2) {

            usuario = new Director(
                    2L,
                    "Aprobador Direccion",
                    "direccion@empresa.com");

        } else {

            throw new IllegalArgumentException(
                    "Rol de aprobador incorrecto");
        }

        Factura facturaAprobada =
                aprobacionService.aprobar(
                        facturaId,
                        usuario);

        System.out.println();
        System.out.println(
                "Factura aprobada correctamente.");

        System.out.println(
                "Rol utilizado: " + usuario.getRol());

        mostrarFactura(facturaAprobada);
    }

    private void rechazarFactura() {

        System.out.println();
        System.out.println("=== RECHAZAR FACTURA ===");

        List<Factura> pendientes =
                facturaService.porEstado(
                        EstadoFactura.PENDIENTE);

        if (pendientes.isEmpty()) {

            System.out.println(
                    "No existen facturas pendientes.");

            return;
        }

        System.out.println("Facturas pendientes:");

        for (Factura factura : pendientes) {
            mostrarFactura(factura);
        }

        System.out.print(
                "Ingrese ID de factura a rechazar: ");

        Long facturaId =
                Long.parseLong(scanner.nextLine());

        Factura facturaRechazada =
                facturaService.cambiarEstado(
                        facturaId,
                        EstadoFactura.RECHAZADA);

        System.out.println();
        System.out.println(
                "Factura rechazada correctamente.");

        mostrarFactura(facturaRechazada);
    }

    private void listarPendientes() {

        List<Factura> pendientes =
                facturaService.porEstado(
                        EstadoFactura.PENDIENTE);

        if (pendientes.isEmpty()) {

            System.out.println(
                    "No hay facturas pendientes.");

            return;
        }

        for (Factura factura : pendientes) {
            mostrarFactura(factura);
        }
    }

    private void ordenarPorImporte() {

        List<Factura> facturas =
                facturaService.ordenarPorImporte();

        if (facturas.isEmpty()) {

            System.out.println(
                    "No hay facturas para ordenar.");

            return;
        }

        System.out.println();
        System.out.println(
                "Facturas ordenadas por importe:");

        for (Factura factura : facturas) {
            mostrarFactura(factura);
        }
    }

    private void mostrarFactura(Factura factura) {

        System.out.println(
                "----------------------------------------");

        System.out.println(
                "ID: " + factura.getId());

        if (factura.getProveedor() != null) {

            System.out.println(
                    "Proveedor: "
                            + factura
                            .getProveedor()
                            .getNombre());
        }

        System.out.println(
                "Importe: $" + factura.getImporte());

        System.out.println(
                "Estado: " + factura.getEstado());

        System.out.println(
                "Nivel de aprobacion: "
                        + factura.getNivelAprobacion());
    }
}