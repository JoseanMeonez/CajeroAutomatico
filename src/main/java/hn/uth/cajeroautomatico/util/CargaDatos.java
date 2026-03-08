package hn.uth.cajeroautomatico.util;

import hn.uth.cajeroautomatico.models.Cliente;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CargaDatos {
    private static Path rutaCsv;

    private static Path obtenerRutaCsv() {
        if (rutaCsv == null) {
            try {
                URL url = CargaDatos.class.getClassLoader().getResource("clientes.csv");
                if (url != null) {
                    rutaCsv = Paths.get(url.toURI());
                }
            } catch (Exception e) {
                System.err.println("Error al obtener ruta del CSV: " + e.getMessage());
            }
        }
        return rutaCsv;
    }

    public static List<Cliente> cargarClientes() {
        List<Cliente> clientes = new ArrayList<>();

        try (InputStream is = CargaDatos.class.getClassLoader().getResourceAsStream("clientes.csv");
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            // Saltar la línea de header
            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    String numeroCuenta = datos[0].trim();
                    String pin = datos[1].trim();
                    double saldo = Double.parseDouble(datos[2].trim());
                    clientes.add(new Cliente(numeroCuenta, pin, saldo));
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar clientes desde CSV: " + e.getMessage());
        }

        // Inicializar la ruta para escritura
        obtenerRutaCsv();

        return clientes;
    }


}
