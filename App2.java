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
        // Crear el menú
        Menu menu = new Menu();
        boolean salir = false;

        while (!salir) {
            int opcionPrincipal = menu.mostrarMenuPrincipal();

            switch (opcionPrincipal) {
                case 1:
                    gestionarCultivos(menu);
                    break;
                case 2:
                    gestionarParcelas(menu);
                    break;
                case 3:
                    gestionarActividades(menu);
                    break;
                case 4:
                    gestionarBusquedaReporte(menu);
                    break;
                case 5:
                    System.out.println("¡Gracias por usar el sistema! ¡Adiós!");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
            }
        }
    }

    private static void gestionarCultivos(Menu menu) {
        boolean volver = false;
        while (!volver) {
            int opcion = menu.mostrarMenuCultivos();
            switch (opcion) {
                case 1:
                    System.out.println("Listar cultivos:");
                    List<Cultivo> cultivos = servicioCultivo.listarCultivos();
                    for (Cultivo cultivo : cultivos) {
                        System.out.println(cultivo.obtenerDescripcion());
                    }
                    break;
                case 2:
                    System.out.println("Crear cultivo:");
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine(); // Cambiado a nextLine()
                    System.out.print("Variedad: ");
                    String variedad = scanner.nextLine(); // Cambiado a nextLine()
                    System.out.print("Superficie: ");
                    double superficie = Double.parseDouble(scanner.nextLine()); // nextLine() y conversión
                    System.out.print("Fecha de siembra (YYYY-MM-DD): ");
                    LocalDate fechaSiembra = LocalDate.parse(scanner.nextLine()); // Cambiado a nextLine()
                    System.out.print("Estado (ACTIVO, EN_RIESGO, COSECHADO): ");
                    String estado = scanner.nextLine(); // Cambiado a nextLine()
                    Cultivo nuevoCultivo = new Cultivo(nombre, variedad, superficie, null, fechaSiembra, estado, null);
                    servicioCultivo.crearCultivo(nuevoCultivo);
                    System.out.println("Cultivo creado con éxito.");
                    break;
                case 3:
                    System.out.println("Editar cultivo:");
                    System.out.print("Nombre del cultivo a editar: ");
                    String nombreEditar = scanner.nextLine(); // Cambiado a nextLine()
                    Cultivo cultivoEditar = servicioCultivo.buscarCultivos(nombreEditar).stream().findFirst().orElse(null);
                    if (cultivoEditar != null) {
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine(); // Cambiado a nextLine()
                        System.out.print("Nueva variedad: ");
                        String nuevaVariedad = scanner.nextLine(); // Cambiado a nextLine()
                        System.out.print("Nueva superficie: ");
                        double nuevaSuperficie = Double.parseDouble(scanner.nextLine()); // nextLine() y conversión
                        servicioCultivo.editarCultivo(cultivoEditar, nuevoNombre, nuevaVariedad, nuevaSuperficie, cultivoEditar.getParcela());
                        System.out.println("Cultivo editado con éxito.");
                    } else {
                        System.out.println("Cultivo no encontrado.");
                    }
                    break;
                case 4:
                    System.out.println("Eliminar cultivo:");
                    System.out.print("Nombre del cultivo a eliminar: ");
                    String nombreEliminar = scanner.nextLine(); // Cambiado a nextLine()
                    Cultivo cultivoEliminar = servicioCultivo.buscarCultivos(nombreEliminar).stream().findFirst().orElse(null);
                    if (cultivoEliminar != null) {
                        boolean eliminado = servicioCultivo.eliminarCultivo(cultivoEliminar);
                        System.out.println(eliminado ? "Cultivo eliminado con éxito." : "No se puede eliminar el cultivo, tiene actividades pendientes.");
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

    private static void gestionarParcelas(Menu menu) {
        boolean volver = false;
        while (!volver) {
            int opcion = menu.mostrarMenuParcelas();
            switch (opcion) {
                case 1:
                    System.out.println("Listar parcelas:");
                    List<Parcela> parcelas = servicioParcela.listarParcelas();
                    for (Parcela parcela : parcelas) {
                        System.out.println("Parcela: " + parcela.getCodigo() + ", Tamaño: " + parcela.getTamano());
                    }
                    break;
                case 2:
                    System.out.println("Crear parcela:");
                    System.out.print("Código: ");
                    String codigo = scanner.next();
                    System.out.print("Tamaño: ");
                    double tamano = scanner.nextDouble();
                    Parcela nuevaParcela = new Parcela(codigo, tamano);
                    servicioParcela.agregarParcela(nuevaParcela);
                    System.out.println("Parcela creada con éxito.");
                    break;
                case 3:
                    System.out.println("Editar parcela:");
                    System.out.print("Código de la parcela a editar: ");
                    String codigoEditar = scanner.next();
                    Parcela parcelaEditar = servicioParcela.listarParcelas().stream().filter(p -> p.getCodigo().equals(codigoEditar)).findFirst().orElse(null);
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
                    System.out.println("Eliminar parcela:");
                    System.out.print("Código de la parcela a eliminar: ");
                    String codigoEliminar = scanner.next();
                    Parcela parcelaEliminar = servicioParcela.listarParcelas().stream().filter(p -> p.getCodigo().equals(codigoEliminar)).findFirst().orElse(null);
                    if (parcelaEliminar != null) {
                        boolean eliminada = servicioParcela.eliminarParcela(parcelaEliminar);
                        System.out.println(eliminada ? "Parcela eliminada con éxito." : "No se puede eliminar la parcela, tiene cultivos activos.");
                    } else {
                        System.out.println("Parcela no encontrada.");
                    }
                    break;
                case 5:
                    System.out.println("Asignar cultivo a parcela:");
                    System.out.print("Código de la parcela: ");
                    String codigoParcela = scanner.next();
                    Parcela parcelaAsignar = servicioParcela.listarParcelas().stream().filter(p -> p.getCodigo().equals(codigoParcela)).findFirst().orElse(null);
                    if (parcelaAsignar != null) {
                        System.out.print("Nombre del cultivo: ");
                        String nombreCultivo = scanner.next();
                        Cultivo cultivoAsignar = servicioCultivo.buscarCultivos(nombreCultivo).stream().findFirst().orElse(null);
                        if (cultivoAsignar != null) {
                            servicioParcela.asignarCultivoAParcela(parcelaAsignar, cultivoAsignar);
                            System.out.println("Cultivo asignado a la parcela con éxito.");
                        } else {
                            System.out.println("Cultivo no encontrado.");
                        }
                    } else {
                        System.out.println("Parcela no encontrada.");
                    }
                    break;
                case 6:
                    volver = true;
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