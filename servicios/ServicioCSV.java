package servicios;

import modelos.Actividad;
import modelos.Cultivo;
import modelos.Parcela;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioCSV {

    private final ServicioCultivo servicioCultivo;
    private final ServicioParcela servicioParcela;
    private final ServicioActividad servicioActividad;

    // Constructor para inyectar los servicios
    public ServicioCSV(ServicioCultivo servicioCultivo, ServicioParcela servicioParcela, ServicioActividad servicioActividad) {
        this.servicioCultivo = servicioCultivo;
        this.servicioParcela = servicioParcela;
        this.servicioActividad = servicioActividad;
    }

    // Leer archivo CSV y cargar datos en los servicios
    public void leerArchivo(String nombreArchivo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Dividir por comas, respetando las comillas
                if (partes.length >= 8) { // Validar que haya suficientes datos
                    // Datos del cultivo
                    String nombre = partes[1].replace("\"", "").trim();
                    String variedad = partes[2].replace("\"", "").trim();
                    double superficie = Double.parseDouble(partes[3].trim());
                    String codigoParcela = partes[4].replace("\"", "").trim();
                    LocalDate fechaSiembra = LocalDate.parse(partes[5].replace("\"", "").trim());
                    String estado = partes[6].replace("\"", "").trim();

                    // Procesar la parcela
                    Parcela parcela = servicioParcela.listarParcelas().stream()
                            .filter(p -> p.getCodigo().equals(codigoParcela))
                            .findFirst()
                            .orElseGet(() -> {
                                Parcela nuevaParcela = new Parcela(codigoParcela, superficie);
                                servicioParcela.agregarParcela(nuevaParcela);
                                return nuevaParcela;
                            });

                    // Procesar las actividades (formato JSON)
                    String actividadesTexto = partes[7].trim();
                    List<Actividad> actividades = new ArrayList<>();
                    if (actividadesTexto.startsWith("[") && actividadesTexto.endsWith("]")) {
                        actividadesTexto = actividadesTexto.substring(1, actividadesTexto.length() - 1); // Quitar corchetes
                        String[] actividadesPartes = actividadesTexto.split(",");
                        for (String actividadTexto : actividadesPartes) {
                            actividadTexto = actividadTexto.replace("\"", "").trim(); // Quitar comillas
                            String[] actividadDatos = actividadTexto.split(":");
                            if (actividadDatos.length == 2) {
                                String tipo = actividadDatos[0].trim();
                                LocalDate fecha = LocalDate.parse(actividadDatos[1].trim());
                                Actividad actividad = new Actividad(tipo, fecha);
                                actividades.add(actividad);
                            }
                        }
                    }

                    // Crear el cultivo y asignar parcela y actividades
                    Cultivo cultivo = new Cultivo(nombre, variedad, superficie, parcela, fechaSiembra, estado, actividades);
                    servicioCultivo.crearCultivo(cultivo);

                    // Registrar actividades en el servicio de actividades
                    for (Actividad actividad : actividades) {
                        servicioActividad.registrarActividad(cultivo, actividad);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar los datos del archivo: " + e.getMessage());
        }
    }
}
