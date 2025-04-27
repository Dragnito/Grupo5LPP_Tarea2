import menu.Menu;

public class App2 {

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
                    System.out.println("Listar cultivos");
                    // Aquí iría el llamado a la función real de listar cultivos
                    break;
                case 2:
                    System.out.println("Crear cultivo");
                    // Función de crear cultivo
                    break;
                case 3:
                    System.out.println("Editar cultivo");
                    // Función de editar cultivo
                    break;
                case 4:
                    System.out.println("Eliminar cultivo");
                    // Función de eliminar cultivo
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
                    System.out.println("Listar parcelas");
                    // Aquí iría el llamado a la función real de listar parcelas
                    break;
                case 2:
                    System.out.println("Crear parcela");
                    // Función de crear parcela
                    break;
                case 3:
                    System.out.println("Editar parcela");
                    // Función de editar parcela
                    break;
                case 4:
                    System.out.println("Eliminar parcela");
                    // Función de eliminar parcela
                    break;
                case 5:
                    System.out.println("Asignar cultivo a parcela");
                    // Función de asignar cultivo a parcela
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
                    System.out.println("Registrar actividad");
                    // Función de registrar actividad
                    break;
                case 2:
                    System.out.println("Listar actividades de un cultivo");
                    // Función de listar actividades de un cultivo
                    break;
                case 3:
                    System.out.println("Eliminar actividad");
                    // Función de eliminar actividad
                    break;
                case 4:
                    System.out.println("Marcar actividad como completada");
                    // Función de marcar actividad como completada
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
                    System.out.println("Buscar cultivos por nombre o variedad");
                    // Función de buscar cultivos por nombre o variedad
                    break;
                case 2:
                    System.out.println("Reporte de cultivos");
                    // Función de reporte de cultivos activos, cosechados o en riesgo
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