package servicios;

import modelos.Actividad;
import modelos.Cultivo;
import java.util.List;

public class ServicioActividad {
    // Registrar actividad para un cultivo
    public void registrarActividad(Cultivo cultivo, Actividad actividad) {
        cultivo.agregarActividad(actividad);
    }

    // Listar actividades por cultivo
    public List<Actividad> listarActividades(Cultivo cultivo) {
        return cultivo.getActividades();
    }

    // Eliminar actividad
    public void eliminarActividad(Cultivo cultivo, Actividad actividad) {
        cultivo.getActividades().remove(actividad);
    }

    // Marcar actividad como completada
    public void completarActividad(Actividad actividad) {
        actividad.completar();
    }
}
