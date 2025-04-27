package menu;

import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);

    public int mostrarMenuPrincipal() {
        System.out.println("===== MENU PRINCIPAL =====");
        System.out.println("1. Gestión de Cultivos");
        System.out.println("2. Gestión de Parcelas");
        System.out.println("3. Gestión de Actividades");
        System.out.println("4. Búsqueda / Reporte");
        System.out.println("5. Salir");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }

    public int mostrarMenuCultivos() {
        System.out.println("--- Gestión de Cultivos ---");
        System.out.println("1. Listar cultivos");
        System.out.println("2. Crear cultivo");
        System.out.println("3. Editar cultivo");
        System.out.println("4. Eliminar cultivo");
        System.out.println("5. Volver");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }

    public int mostrarMenuParcelas() {
        System.out.println("--- Gestión de Parcelas ---");
        System.out.println("1. Listar parcelas");
        System.out.println("2. Crear parcela");
        System.out.println("3. Editar parcela");
        System.out.println("4. Eliminar parcela");
        System.out.println("5. Asignar cultivo a parcela");
        System.out.println("6. Volver");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }

    public int mostrarMenuActividades() {
        System.out.println("--- Gestión de Actividades ---");
        System.out.println("1. Registrar actividad");
        System.out.println("2. Listar actividades de un cultivo");
        System.out.println("3. Eliminar actividad");
        System.out.println("4. Marcar actividad como completada");
        System.out.println("5. Volver");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }

    public int mostrarMenuBusquedaReporte() {
        System.out.println("--- Búsqueda / Reporte ---");
        System.out.println("1. Buscar cultivos por nombre o variedad");
        System.out.println("2. Reporte de cultivos activos, cosechados o en riesgo");
        System.out.println("3. Volver");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }
}