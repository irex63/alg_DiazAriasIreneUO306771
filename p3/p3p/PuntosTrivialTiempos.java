package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PuntosTrivialTiempos {

    public static void main(String[] args) {

        long t1, t2;

        for (int n = 1024; n <= 1024000; n *= 2) {
            t1 = System.currentTimeMillis();

            List<Punto> puntos = generarPuntosAleatorios(n);
            puntosIterativo(puntos);

            t2 = System.currentTimeMillis();

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


    public static List<Punto> puntosIterativo(List<Punto> puntos) { //O(n^2)
        ArrayList<Punto> result=new ArrayList<>();
        int n = puntos.size();
        if (n <= 2) { // Si hay 2 o menos puntos retornamos
            return new ArrayList<>(puntos);
        }
        
        //Tomamos como puntos iniciales los dos primeros
        Punto p1Min = puntos.get(0);
        Punto p2Min = puntos.get(1);
        double distMinima = calcularDistancia(p1Min, p2Min);

        result.add(p1Min);
        result.add(p2Min);

        for (int i = 0; i < n; i++) {//O(n)
            for (int j = i + 1; j < n; j++) {// O(n)
                // 1 Calculamos la distancia entre los mínimos
                double d = calcularDistancia(puntos.get(i), puntos.get(j));
                // 2 Si es menor actualizamoss la distancia mínima
                if (d < distMinima) {
                    distMinima = d;
                    //Reiniciamos los datos de la lista
                    result.clear(); 
                    result.add(puntos.get(i));
                    result.add(puntos.get(j));
                } else if (Double.compare(d, distMinima) == 0) {
                    // 3 Si es igual añadimos a la lista sin actualizar la distancia mínima
                    result.add(puntos.get(i));
                    result.add(puntos.get(j));
                }
            }
        }

        return result;
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
