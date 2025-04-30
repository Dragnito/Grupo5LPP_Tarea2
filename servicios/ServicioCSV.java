import java.io.BufferedReader;
import java.io.FileReader;

package servicios;

public class ServicioCSV {

    // Importante

    // El archivo CSV se pasa como argumento al programa (args[0])

    private BufferedReader lector; // Lee el archivo
    private String linea; // Recibe la linea de cada fila
    private String partes[] = null; // Almacena cada linea leída

    public void leerArchivo(String nombreArchivo){
        try {
            lector = new BufferedReader(new FileReader(nombreArchivo));
            while ((linea = lector.readLine()) != null) {
                partes = linea.split(",");
                
            }
        }
    }

}
