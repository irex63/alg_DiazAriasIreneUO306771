package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PuntosDyVTiempos {

    public static void main(String[] args) {

        long t1, t2;

        for (int n = 1024; n <= 1024000; n *= 2) {
            List<Punto> puntos = generarPuntosAleatorios(n);
            
            t1 = System.currentTimeMillis();

            double dist=puntosDyV(puntos,0,puntos.size()-1);

            t2 = System.currentTimeMillis();

            System.out.println("DIST MINIMA ENCONTRADA " + dist);
            System.out.println(" n=" + n + "**TIEMPO=" + (t2 - t1));
        } // for
    }
    
    private static List<Punto> generarPuntosAleatorios(int n) {
        List<Punto> lista = new ArrayList<>(n);
        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            double x = rnd.nextDouble() * 100;
            double y = rnd.nextDouble() * 100;
            lista.add(new Punto(x, y));
        }
        return lista;
    }

    static class Punto {
        double x, y;

        Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("[%.6f, %.6f]", x, y);
        }
    }


    public static double puntosDyV(List<Punto> puntos, int izq, int der) {
        // Tratamos los caso base
        int numeroElem = (der - izq) + 1;
        if (numeroElem < 2) {
            return Double.MAX_VALUE;
        } else if (numeroElem == 2) {
            return calcularDistancia(puntos.get(izq), puntos.get(der));
        }

        int medio = (der + izq) / 2;
        double distFranja = calcularDistancia(puntos.get(medio), puntos.get(medio + 1));
        double distIzq = puntosDyV(puntos, izq, medio);
        double distDer = puntosDyV(puntos, medio + 1, der);

        // Devolvemos el valor más pequeño
        return Math.min(distFranja, Math.min(distIzq, distDer));
    }

    /**
     * Calcula la distancia entre dos puntos dados
     * @param p1
     * @param p2
     * @return la distancia calculada
     */
    private static double calcularDistancia(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    /**
     * Lee de fichero los puntos dónde se va a aplicar el algoritmo
     * @param nombreFichero
     * @return Una lista con los puntos
     */
    public static List<Punto> leerPuntos(String nombreFichero) {
        List<Punto> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                String[] partes = line.split(",");
                if (partes.length >= 2) {
                    double x = Double.parseDouble(partes[0]);
                    double y = Double.parseDouble(partes[1]);
                    lista.add(new Punto(x, y));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el fichero: " + e.getMessage());
        }
        return lista;
    }
}
