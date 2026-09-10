package Utils;

import Modelos.Pacientes;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Hay que importarlo
import java.util.List;
import java.util.ArrayList;

public class LectorCSV {

    public static List<Pacientes> leerTratamiento(String path) {

        List<Pacientes> paciente = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream("9-9/tratamientos.csv")) {
            Scanner sc = new Scanner(fis);
            while(sc.hasNext()) {
                String linea = sc.nextLine();
                // Separa en columnas
                String[] partes = linea.split(",");
                int dni = Integer.parseInt(partes[0].trim());
                Pacientes p = new Pacientes(dni, partes[1], partes[2], partes[3]);
                paciente.add(p);
            }

        } catch (IOException ex) {
            System.err.println("Error leyendo archivo");
        }

        return paciente;
    }

    // Alternativa 2
    public static List<Pacientes> leerTratamiento2(String path) {

    }

}
