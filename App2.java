import modelos.*;
import servicios.*;
import java.time.LocalDate;
import java.util.List;

public class App2 {
    public static void main(String[] args) {
        // Crear servicios
        ServicioCultivo servicioCultivo = new ServicioCultivo();
        ServicioParcela servicioParcela = new ServicioParcela();
        ServicioActividad servicioActividad = new ServicioActividad();

        // Crear parcelas
        Parcela parcela1 = new Parcela("PARCELA-A01", 50.0);
        Parcela parcela2 = new Parcela("PARCELA-B01", 30.0);
        servicioParcela.agregarParcela(parcela1);
        servicioParcela.agregarParcela(parcela2);

        // Crear cultivos
        Cultivo cultivo1 = new Cultivo("Maíz", "Variedad Dulce", 20.0, parcela1, LocalDate.of(2023, 3, 1), "ACTIVO", null);
        Cultivo cultivo2 = new Cultivo("Trigo", "Variedad Premium", 25.0, parcela2, LocalDate.of(2023, 2, 15), "ACTIVO", null);
        servicioCultivo.crearCultivo(cultivo1);
        servicioCultivo.crearCultivo(cultivo2);

        // Asignar cultivos a parcelas
        servicioParcela.asignarCultivoAParcela(parcela1, cultivo1);
        servicioParcela.asignarCultivoAParcela(parcela2, cultivo2);

        // Registrar actividades
        Actividad actividad1 = new Actividad("Riego", LocalDate.of(2023, 3, 10));
        Actividad actividad2 = new Actividad("Fertilización", LocalDate.of(2023, 3, 20));
        servicioActividad.registrarActividad(cultivo1, actividad1);
        servicioActividad.registrarActividad(cultivo1, actividad2);

        // Listar cultivos
        System.out.println("Cultivos existentes:");
        List<Cultivo> cultivos = servicioCultivo.listarCultivos();
        for (Cultivo cultivo : cultivos) {
            System.out.println(cultivo.obtenerDescripcion());
        }

        // Buscar cultivos por nombre
        System.out.println("\nBuscar cultivos por nombre 'Maíz':");
        List<Cultivo> resultados = servicioCultivo.buscarCultivos("Maíz");
        for (Cultivo cultivo : resultados) {
            System.out.println(cultivo.obtenerDescripcion());
        }

        // Eliminar cultivo (si no tiene actividades pendientes)
        System.out.println("\nIntentando eliminar cultivo 'Trigo':");
        boolean eliminado = servicioCultivo.eliminarCultivo(cultivo2);
        System.out.println(eliminado ? "Cultivo eliminado." : "No se puede eliminar el cultivo, tiene actividades pendientes.");

        // Guardar en CSV (opcional)
       // ServicioCSV servicioCSV = new ServicioCSV();
        //servicioCSV.guardarCultivosEnCSV(cultivos, "cultivos.csv");
    }
}
