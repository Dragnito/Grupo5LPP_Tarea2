import menu.Menu;
import modelos.*;
import servicios.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App2 {

    private static final ServicioCultivo servicioCultivo = new ServicioCultivo();
    private static final ServicioParcela servicioParcela = new ServicioParcela();
    private static final ServicioActividad servicioActividad = new ServicioActividad();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inicializar los servicios principales
        ServicioCSV servicioCSV = new ServicioCSV(servicioCultivo, servicioParcela, servicioActividad);

        // Cargar datos desde el archivo CSV al inicio del programa
        servicioCSV.leerArchivo("cultivos.csv");

        // Crear el menú principal
        Menu menu = new Menu();
        boolean salir = false;

        // Bucle principal del programa
        while (!salir) {
            // Mostrar el menú principal y capturar la opción seleccionada
            int opcionPrincipal = menu.mostrarMenuPrincipal();

            // Ejecutar la acción correspondiente según la opción seleccionada
            switch (opcionPrincipal) {
                case 1:
                    gestionarCultivos(menu); // Gestión de cultivos
                    break;
                case 2:
                    gestionarParcelas(menu); // Gestión de parcelas
                    break;
                case 3:
                    gestionarActividades(menu); // Gestión de actividades
                    break;
                case 4:
                    gestionarBusquedaReporte(menu); // Búsqueda y reportes
                    break;
                case 5:
                    System.out.println("¡Gracias por usar el sistema! ¡Adiós!");
                    salir = true; // Salir del programa
                    break;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }
    }

    private static void gestionarCultivos(Menu menu) {
        boolean volver = false;

        // Bucle para gestionar las opciones relacionadas con cultivos
        while (!volver) {
            int opcion = menu.mostrarMenuCultivos(); // Mostrar menú de cultivos
            switch (opcion) {
                case 1:
                    // Listar todos los cultivos registrados
                    System.out.println("Listar cultivos:");
                    List<Cultivo> cultivos = servicioCultivo.listarCultivos();
                    for (Cultivo cultivo : cultivos) {
                        System.out.println(cultivo.obtenerDescripcion());
                    }
                    break;
                case 2:
                    // Crear un nuevo cultivo
                    System.out.println("Crear cultivo:");
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Variedad: ");
                    String variedad = scanner.nextLine();

                    // Validar la superficie del cultivo
                    System.out.print("Superficie: ");
                    double superficie = -1;
                    while (superficie <= 0) {
                        try {
                            superficie = Double.parseDouble(scanner.next());
                            if (superficie <= 0) {
                                System.out.println("La superficie debe ser un número positivo. Intenta nuevamente.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Por favor, ingresa un número válido.");
                        }
                    }

                    // Validar la parcela asociada
                    System.out.print("Código de la parcela: ");
                    String codigoParcela = scanner.next();
                    Parcela parcelaAsignar = servicioParcela.listarParcelas().stream()
                            .filter(p -> p.getCodigo().equals(codigoParcela))
                            .findFirst()
                            .orElse(null);

                    if (parcelaAsignar == null) {
                        System.out.println("Error: La parcela no existe. Por favor, verifica el código ingresado.");
                        break;
                    }

                    // Validar que la superficie del cultivo no exceda el tamaño de la parcela
                    if (superficie > parcelaAsignar.getTamano()) {
                        System.out.println("Error: La superficie del cultivo excede el tamaño de la parcela.");
                        break;
                    }

                    // Validar la fecha de siembra
                    System.out.print("Fecha de siembra (YYYY-MM-DD): ");
                    LocalDate fechaSiembra = null;
                    while (fechaSiembra == null) {
                        try {
                            String fechaInput = scanner.next();
                            fechaSiembra = LocalDate.parse(fechaInput);
                        } catch (Exception e) {
                            System.out.println("Fecha inválida. Por favor, ingresa una fecha válida en formato YYYY-MM-DD.");
                        }
                    }

                    // Validar el estado del cultivo
                    System.out.print("Estado (ACTIVO, EN_RIESGO, COSECHADO): ");
                    String estado = "";
                    while (!estado.equalsIgnoreCase("ACTIVO") && !estado.equalsIgnoreCase("EN_RIESGO") && !estado.equalsIgnoreCase("COSECHADO")) {
                        estado = scanner.next();
                        if (!estado.equalsIgnoreCase("ACTIVO") && !estado.equalsIgnoreCase("EN_RIESGO") && !estado.equalsIgnoreCase("COSECHADO")) {
                            System.out.println("Estado inválido. Por favor, ingresa ACTIVO, EN_RIESGO o COSECHADO.");
                        }
                    }

                    // Crear y registrar el nuevo cultivo
                    Cultivo nuevoCultivo = new Cultivo(nombre, variedad, superficie, parcelaAsignar, fechaSiembra, estado, null);
                    servicioCultivo.crearCultivo(nuevoCultivo);

                    // Asignar el cultivo a la parcela
                    servicioParcela.asignarCultivoAParcela(parcelaAsignar, nuevoCultivo);
                    System.out.println("Nuevo cultivo creado y asignado a la parcela con éxito.");
                    break;
                case 3:
                    // Editar un cultivo existente
                    System.out.println("Editar cultivo:");
                    System.out.print("Nombre del cultivo a editar: ");
                    String nombreEditar = scanner.nextLine();
                    Cultivo cultivoEditar = servicioCultivo.buscarCultivos(nombreEditar).stream().findFirst().orElse(null);

                    if (cultivoEditar != null) {
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nueva variedad: ");
                        String nuevaVariedad = scanner.nextLine();
                        System.out.print("Nueva superficie: ");
                        double nuevaSuperficie = Double.parseDouble(scanner.nextLine());

                        // Actualizar los datos del cultivo
                        servicioCultivo.editarCultivo(cultivoEditar, nuevoNombre, nuevaVariedad, nuevaSuperficie, cultivoEditar.getParcela());
                        System.out.println("Cultivo editado con éxito.");
                    } else {
                        System.out.println("Cultivo no encontrado.");
                    }
                    break;
                case 4:
                    // Eliminar un cultivo
                    System.out.println("Eliminar cultivo:");
                    System.out.print("Nombre del cultivo a eliminar: ");
                    String nombreEliminar = scanner.nextLine();
                    Cultivo cultivoEliminar = servicioCultivo.buscarCultivos(nombreEliminar).stream().findFirst().orElse(null);

                    if (cultivoEliminar != null) {
                        boolean eliminado = servicioCultivo.eliminarCultivo(cultivoEliminar);
                        System.out.println(eliminado ? "Cultivo eliminado con éxito." : "No se puede eliminar el cultivo, tiene actividades pendientes.");
                    } else {
                        System.out.println("Cultivo no encontrado.");
                    }
                    break;
                case 5:
                    volver = true; // Volver al menú principal
                    break;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }
    }

    private static void gestionarParcelas(Menu menu) {
        boolean volver = false;

        // Bucle para gestionar las opciones relacionadas con parcelas
        while (!volver) {
            int opcion = menu.mostrarMenuParcelas(); // Mostrar menú de parcelas
            switch (opcion) {
                case 1:
                    // Listar todas las parcelas registradas
                    System.out.println("Listar parcelas:");
                    List<Parcela> parcelas = servicioParcela.listarParcelas();
                    for (Parcela parcela : parcelas) {
                        System.out.println("Parcela: " + parcela.getCodigo() + ", Tamaño: " + parcela.getTamano());
                    }
                    break;
                case 2:
                    // Crear una nueva parcela
                    System.out.println("Crear parcela:");
                    System.out.print("Código: ");
                    String codigo = scanner.next();
                    System.out.print("Tamaño: ");
                    double tamano = scanner.nextDouble();

                    // Crear y registrar la nueva parcela
                    Parcela nuevaParcela = new Parcela(codigo, tamano);
                    servicioParcela.agregarParcela(nuevaParcela);
                    System.out.println("Parcela creada con éxito.");
                    break;
                case 3:
                    // Editar una parcela existente
                    System.out.println("Editar parcela:");
                    System.out.print("Código de la parcela a editar: ");
                    String codigoEditar = scanner.next();
                    Parcela parcelaEditar = servicioParcela.listarParcelas().stream()
                            .filter(p -> p.getCodigo().equals(codigoEditar))
                            .findFirst()
                            .orElse(null);

                    if (parcelaEditar != null) {
                        System.out.print("Nuevo tamaño: ");
                        double nuevoTamano = scanner.nextDouble();
                        servicioParcela.editarParcela(parcelaEditar, nuevoTamano);
                        System.out.println("Parcela editada con éxito.");
                    } else {
                        System.out.println("Parcela no encontrada.");
                    }
                    break;
                case 4:
                    // Eliminar una parcela
                    System.out.println("Eliminar parcela:");
                    System.out.print("Código de la parcela a eliminar: ");
                    String codigoEliminar = scanner.next();
                    Parcela parcelaEliminar = servicioParcela.listarParcelas().stream()
                            .filter(p -> p.getCodigo().equals(codigoEliminar))
                            .findFirst()
                            .orElse(null);

                    if (parcelaEliminar != null) {
                        boolean eliminada = servicioParcela.eliminarParcela(parcelaEliminar);
                        System.out.println(eliminada ? "Parcela eliminada con éxito." : "No se puede eliminar la parcela, tiene cultivos activos.");
                    } else {
                        System.out.println("Parcela no encontrada.");
                    }
                    break;
                case 5:
                    // Asignar un cultivo a una parcela
                    System.out.println("Asignar cultivo a parcela:");
                    System.out.print("Código de la parcela: ");
                    String codigoParcela = scanner.next();
                    Parcela parcelaAsignar = servicioParcela.listarParcelas().stream()
                            .filter(p -> p.getCodigo().equals(codigoParcela))
                            .findFirst()
                            .orElse(null);

                    if (parcelaAsignar != null) {
                        System.out.print("Nombre del cultivo: ");
                        String nombreCultivo = scanner.next();
                        Cultivo cultivoAsignar = servicioCultivo.buscarCultivos(nombreCultivo).stream()
                                .findFirst()
                                .orElse(null);

                        if (cultivoAsignar != null) {
                            // Validar que la superficie del cultivo no exceda el tamaño de la parcela
                            if (cultivoAsignar.getSuperficie() > parcelaAsignar.getTamano()) {
                                System.out.println("Error: La superficie del cultivo excede el tamaño de la parcela.");
                                break;
                            }

                            // Asignar el cultivo existente a la parcela
                            cultivoAsignar.setParcela(parcelaAsignar);
                            servicioParcela.asignarCultivoAParcela(parcelaAsignar, cultivoAsignar);
                            System.out.println("Cultivo existente asignado a la parcela con éxito.");
                        } else {
                            System.out.println("Cultivo no encontrado.");
                        }
                    } else {
                        System.out.println("Parcela no encontrada.");
                    }
                    break;
                case 6:
                    volver = true; // Volver al menú principal
                    break;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }
    }

    private static void gestionarActividades(Menu menu) {
        boolean volver = false;
        while (!volver) {
            int opcion = menu.mostrarMenuActividades();
            switch (opcion) {
                case 1:
                    System.out.println("Registrar actividad:");
                    System.out.print("Nombre del cultivo: ");
                    String nombreCultivo = scanner.next();
                    Cultivo cultivo = servicioCultivo.buscarCultivos(nombreCultivo).stream().findFirst().orElse(null);
                    if (cultivo != null) {
                        System.out.print("Tipo de actividad: ");
                        String tipo = scanner.next();
                        System.out.print("Fecha de la actividad (YYYY-MM-DD): ");
                        LocalDate fecha = LocalDate.parse(scanner.next());
                        Actividad nuevaActividad = new Actividad(tipo, fecha);
                        servicioActividad.registrarActividad(cultivo, nuevaActividad);
                        System.out.println("Actividad registrada con éxito.");
                    } else {
                        System.out.println("Cultivo no encontrado.");
                    }
                    break;
                case 2:
                    System.out.println("Listar actividades de un cultivo:");
                    System.out.print("Nombre del cultivo: ");
                    String nombreCultivoListar = scanner.next();
                    Cultivo cultivoListar = servicioCultivo.buscarCultivos(nombreCultivoListar).stream().findFirst().orElse(null);
                    if (cultivoListar != null) {
                        List<Actividad> actividades = servicioActividad.listarActividades(cultivoListar);
                        for (Actividad actividad : actividades) {
                            System.out.println(actividad);
                        }
                    } else {
                        System.out.println("Cultivo no encontrado.");
                    }
                    break;
                case 3:
                    System.out.println("Eliminar actividad:");
                    System.out.print("Nombre del cultivo: ");
                    String nombreCultivoEliminar = scanner.next();
                    Cultivo cultivoEliminar = servicioCultivo.buscarCultivos(nombreCultivoEliminar).stream().findFirst().orElse(null);
                    if (cultivoEliminar != null) {
                        System.out.print("Tipo de actividad a eliminar: ");
                        String tipoEliminar = scanner.next();
                        Actividad actividadEliminar = cultivoEliminar.getActividades().stream().filter(a -> a.getTipo().equalsIgnoreCase(tipoEliminar)).findFirst().orElse(null);
                        if (actividadEliminar != null) {
                            servicioActividad.eliminarActividad(cultivoEliminar, actividadEliminar);
                            System.out.println("Actividad eliminada con éxito.");
                        } else {
                            System.out.println("Actividad no encontrada.");
                        }
                    } else {
                        System.out.println("Cultivo no encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("Marcar actividad como completada:");
                    System.out.print("Nombre del cultivo: ");
                    String nombreCultivoCompletar = scanner.next();
                    Cultivo cultivoCompletar = servicioCultivo.buscarCultivos(nombreCultivoCompletar).stream().findFirst().orElse(null);
                    if (cultivoCompletar != null) {
                        System.out.print("Tipo de actividad a completar: ");
                        String tipoCompletar = scanner.next();
                        Actividad actividadCompletar = cultivoCompletar.getActividades().stream().filter(a -> a.getTipo().equalsIgnoreCase(tipoCompletar)).findFirst().orElse(null);
                        if (actividadCompletar != null) {
                            servicioActividad.completarActividad(actividadCompletar);
                            System.out.println("Actividad marcada como completada.");
                        } else {
                            System.out.println("Actividad no encontrada.");
                        }
                    } else {
                        System.out.println("Cultivo no encontrado.");
                    }
                    break;
                case 5:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }
    }

    private static void gestionarBusquedaReporte(Menu menu) {
        boolean volver = false;
        while (!volver) {
            int opcion = menu.mostrarMenuBusquedaReporte();
            switch (opcion) {
                case 1:
                    System.out.println("Buscar cultivos por nombre o variedad:");
                    System.out.print("Criterio de búsqueda: ");
                    String criterio = scanner.next();
                    List<Cultivo> resultados = servicioCultivo.buscarCultivos(criterio);
                    for (Cultivo cultivo : resultados) {
                        System.out.println(cultivo.obtenerDescripcion());
                    }
                    break;
                case 2:
                    System.out.println("Reporte de cultivos:");
                    List<Cultivo> cultivos = servicioCultivo.listarCultivos();
                    System.out.println("Cultivos activos:");
                    for (Cultivo cultivo : cultivos) {
                        if ("ACTIVO".equalsIgnoreCase(cultivo.getEstado())) {
                            System.out.println(cultivo.obtenerDescripcion());
                        }
                    }
                    System.out.println("Cultivos cosechados:");
                    for (Cultivo cultivo : cultivos) {
                        if ("COSECHADO".equalsIgnoreCase(cultivo.getEstado())) {
                            System.out.println(cultivo.obtenerDescripcion());
                        }
                    }
                    System.out.println("Cultivos en riesgo:");
                    for (Cultivo cultivo : cultivos) {
                        if ("EN_RIESGO".equalsIgnoreCase(cultivo.getEstado())) {
                            System.out.println(cultivo.obtenerDescripcion());
                        }
                    }
                    break;
                case 3:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }
    }
}