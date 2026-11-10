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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class MenuConsola {

    /*
     * Arreglo fijo utilizado para almacenar las opciones
     * disponibles del menu principal.
     *
     * Este arreglo permite demostrar declaracion,
     * inicializacion, recorrido y acceso por indice.
     */
    private static final String[] OPCIONES_MENU = {
            "Registrar proveedor",
            "Registrar factura",
            "Listar facturas",
            "Buscar factura",
            "Aprobar factura",
            "Rechazar factura",
            "Mostrar facturas pendientes",
            "Ordenar facturas por importe",
            "Salir"
    };

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

        /*
         * Recorrido del arreglo.
         * Los indices comienzan en 0, por eso
         * mostramos i + 1 al usuario.
         */
        for (int i = 0; i < OPCIONES_MENU.length; i++) {

            System.out.println(
                    (i + 1)
                            + ". "
                            + OPCIONES_MENU[i]
            );
        }

        System.out.println("========================================");
        System.out.print("Seleccione una opcion: ");
    }

    private void registrarProveedor() {

        System.out.println();
        System.out.println("=== REGISTRAR PROVEEDOR ===");

        System.out.print("CUIT: ");
        String cuit = scanner.nextLine().trim();

        System.out.print("Razon social: ");
        String razonSocial = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine().trim();

        Proveedor proveedor = proveedorService.crear(
                cuit,
                razonSocial,
                email,
                telefono
        );

        System.out.println();
        System.out.println("Proveedor registrado correctamente.");
        System.out.println("ID: " + proveedor.getId());
        System.out.println("CUIT: " + proveedor.getCuit());

        System.out.println(
                "Razon social: "
                        + proveedor.getRazonSocial());

        System.out.println(
                "Email: "
                        + proveedor.getEmail());

        System.out.println(
                "Telefono: "
                        + proveedor.getTelefono());
    }

    private void registrarFactura() {

        System.out.println();
        System.out.println("=== REGISTRAR FACTURA ===");

        List<Proveedor> proveedores =
                proveedorService.listar();

        if (proveedores.isEmpty()) {

            System.out.println(
                    "No hay proveedores registrados.");

            System.out.println(
                    "Primero debe registrar un proveedor.");

            return;
        }

        System.out.println();
        System.out.println("Proveedores disponibles:");

        for (Proveedor proveedor : proveedores) {

            System.out.println(
                    proveedor.getId()
                            + " - "
                            + proveedor.getRazonSocial()
                            + " - CUIT: "
                            + proveedor.getCuit());
        }

        System.out.println();

        System.out.print("ID del proveedor: ");

        Long proveedorId =
                Long.parseLong(scanner.nextLine());

        System.out.print("Numero de factura: ");

        String numeroFactura =
                scanner.nextLine().trim();

        System.out.print(
                "Fecha de emision (AAAA-MM-DD): ");

        LocalDate fechaEmision =
                leerFechaObligatoria();

        System.out.print(
                "Fecha de vencimiento (AAAA-MM-DD): ");

        LocalDate fechaVencimiento =
                leerFechaOpcional();

        System.out.print("Importe neto: ");

        BigDecimal importeNeto =
                leerImporte();

        System.out.print("IVA: ");

        BigDecimal iva =
                leerImporte();

        BigDecimal importeTotal =
                importeNeto.add(iva);

        System.out.print("Observaciones: ");

        String observaciones =
                scanner.nextLine().trim();

        Factura factura = new Factura();

        factura.setNumeroFactura(numeroFactura);
        factura.setFechaEmision(fechaEmision);
        factura.setFechaVencimiento(fechaVencimiento);
        factura.setImporteNeto(importeNeto);
        factura.setIva(iva);
        factura.setImporteTotal(importeTotal);
        factura.setObservaciones(observaciones);
        factura.setEstado(EstadoFactura.PENDIENTE);

        Factura facturaGuardada =
                facturaService.crear(
                        proveedorId,
                        factura
                );

        System.out.println();

        System.out.println(
                "Factura registrada correctamente.");

        mostrarFactura(facturaGuardada);
    }

    private LocalDate leerFechaObligatoria() {

        while (true) {

            String valor =
                    scanner.nextLine().trim();

            try {

                return LocalDate.parse(valor);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Fecha incorrecta. Use el formato AAAA-MM-DD.");

                System.out.print(
                        "Ingrese nuevamente la fecha: ");
            }
        }
    }

    private LocalDate leerFechaOpcional() {

        while (true) {

            String valor =
                    scanner.nextLine().trim();

            if (valor.isEmpty()) {
                return null;
            }

            try {

                return LocalDate.parse(valor);

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Fecha incorrecta. Use el formato AAAA-MM-DD.");

                System.out.print(
                        "Ingrese nuevamente la fecha o deje vacio: ");
            }
        }
    }

    private BigDecimal leerImporte() {

        while (true) {

            String valor =
                    scanner.nextLine()
                            .trim()
                            .replace(".", "")
                            .replace(",", ".");

            try {

                BigDecimal importe =
                        new BigDecimal(valor);

                if (importe.compareTo(BigDecimal.ZERO) < 0) {

                    System.out.println(
                            "El importe no puede ser negativo.");

                    System.out.print(
                            "Ingrese nuevamente el importe: ");

                    continue;
                }

                return importe;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Importe incorrecto.");

                System.out.print(
                        "Ingrese nuevamente el importe: ");
            }
        }
    }

    private void listarFacturas() {

        System.out.println();
        System.out.println("=== LISTADO DE FACTURAS ===");

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

        System.out.println();
        System.out.println("=== BUSCAR FACTURA ===");

        System.out.print("ID de la factura: ");

        Long facturaId =
                Long.parseLong(scanner.nextLine());

        Factura factura =
                facturaService.buscarPorId(facturaId);

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
                    "No hay facturas pendientes.");

            return;
        }

        for (Factura factura : pendientes) {
            mostrarFactura(factura);
        }

        System.out.print(
                "ID de la factura a aprobar: ");

        Long facturaId =
                Long.parseLong(scanner.nextLine());

        Factura factura =
                facturaService.buscarPorId(facturaId);

        System.out.println();

        System.out.println(
                "Nivel requerido: "
                        + factura.getNivelAprobacion());

        System.out.println("1. Gerencia");
        System.out.println("2. Direccion");

        System.out.print(
                "Seleccione el rol aprobador: ");

        int rol =
                Integer.parseInt(scanner.nextLine());

        System.out.print(
                "Nombre del aprobador: ");

        String nombre =
                scanner.nextLine().trim();

        System.out.print(
                "Email del aprobador: ");

        String email =
                scanner.nextLine().trim();

        Usuario usuario;

        if (rol == 1) {

            usuario = new Gerente(
                    1L,
                    nombre,
                    email
            );

        } else if (rol == 2) {

            usuario = new Director(
                    2L,
                    nombre,
                    email
            );

        } else {

            System.out.println(
                    "Rol incorrecto.");

            return;
        }

        Factura facturaAprobada =
                aprobacionService.aprobar(
                        facturaId,
                        usuario
                );

        System.out.println();

        System.out.println(
                "Factura aprobada correctamente.");

        System.out.println(
                "Aprobada por: "
                        + usuario.getNombre());

        System.out.println(
                "Rol: "
                        + usuario.getRol());

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
                    "No hay facturas pendientes.");

            return;
        }

        for (Factura factura : pendientes) {
            mostrarFactura(factura);
        }

        System.out.print(
                "ID de la factura a rechazar: ");

        Long facturaId =
                Long.parseLong(scanner.nextLine());

        Factura facturaRechazada =
                facturaService.cambiarEstado(
                        facturaId,
                        EstadoFactura.RECHAZADA
                );

        System.out.println();

        System.out.println(
                "Factura rechazada correctamente.");

        mostrarFactura(facturaRechazada);
    }

    private void listarPendientes() {

        System.out.println();

        System.out.println(
                "=== FACTURAS PENDIENTES ===");

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

        System.out.println();

        System.out.println(
                "=== FACTURAS ORDENADAS POR IMPORTE ===");

        List<Factura> facturas =
                facturaService.ordenarPorImporte();

        if (facturas.isEmpty()) {

            System.out.println(
                    "No hay facturas registradas.");

            return;
        }

        for (Factura factura : facturas) {
            mostrarFactura(factura);
        }
    }

    private void mostrarFactura(Factura factura) {

        System.out.println();

        System.out.println(
                "----------------------------------------");

        System.out.println(
                "ID: " + factura.getId());

        System.out.println(
                "Numero: "
                        + factura.getNumeroFactura());

        if (factura.getProveedor() != null) {

            System.out.println(
                    "Proveedor: "
                            + factura.getProveedor()
                                    .getRazonSocial());

            System.out.println(
                    "CUIT: "
                            + factura.getProveedor()
                                    .getCuit());
        }

        System.out.println(
                "Fecha de emision: "
                        + factura.getFechaEmision());

        System.out.println(
                "Fecha de vencimiento: "
                        + factura.getFechaVencimiento());

        System.out.println(
                "Importe neto: $"
                        + factura.getImporteNeto());

        System.out.println(
                "IVA: $"
                        + factura.getIva());

        System.out.println(
                "Importe total: $"
                        + factura.getImporteTotal());

        System.out.println(
                "Estado: "
                        + factura.getEstado());

        System.out.println(
                "Nivel de aprobacion: "
                        + factura.getNivelAprobacion());

        System.out.println(
                "Observaciones: "
                        + factura.getObservaciones());

        System.out.println(
                "----------------------------------------");
    }
}