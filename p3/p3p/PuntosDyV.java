package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PuntosDyV {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Necesista nombre del fichero");
            return;
        }

        String nombreFichero = args[0];
        List<Punto> puntos = leerPuntos(nombreFichero);
        puntos.sort((p1,p2) -> Double.compare(p1.x, p2.x));
        if (puntos.isEmpty())
            return;

        System.out.println(nombreFichero);

        // for (int i = 0; i < result.size(); i += 2) {
        // Punto p1 = result.get(i);
        // Punto p2 = result.get(i + 1);

        // System.out.printf("PUNTOS MAS CERCANOS: [%.6f, %.6f] [%.6f, %.6f]%n",
        // p1.x, p1.y, p2.x, p2.y);
        // }

        double dist = puntosDyV(puntos);
        System.out.printf("SU DISTANCIA MINIMA= %.6f%n", dist);
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

    /**
     * Solo funcionaria con una lista de puntos ordenados por distancia entre ellos
     * a=2 //Número de llamadas recursivas
     * b=2 //Número entre el que se divide el problema
     * k=0 //Complejidad del algoritmo sin tener en cuenta la recursividad
     * Complejidad (Sin tener en cuenta ordenación) : O(n)
     * 
     * Falta la comprobación de la franja que son los valores que están en los
     * limites de la lista
     */
    public static double puntosDyV(List<Punto> puntos) {
        // Ordenar la lista por una de las dos coordenadas

        int n = puntos.size();

        if (n == 2) { // Si solo hay dos puntos en la lista devolvemos la distancia
            return calcularDistancia(puntos.get(0), puntos.get(1));
        }

        List<Punto> izquierda;
        List<Punto> derecha;
        double distFranja=Double.MAX_VALUE;
        // Dividimos la lista en dos
        // Si n==3 dividimos de forma especial para que no quede un punto suelto
        if (n == 3) {
            izquierda = puntos.subList(0, 2);
            derecha = puntos.subList(1, 3);
        } else {
            int medio = n / 2;
            izquierda = puntos.subList(0, medio);
            derecha = puntos.subList(medio, n);
            distFranja=calcularDistancia(puntos.get(medio-1), puntos.get(medio));
        }
        // Calculamos la distancia mínima de cada lado
        double distIzq = puntosDyV(izquierda);
        double distDer = puntosDyV(derecha);

        // Comprobar las franjas de la división
        
        // Devolvemos el valor más pequeño
        return Math.min(distFranja,Math.min(distIzq, distDer));
    }

    /**
     * Calcula la distancia entre dos puntos dados
     * 
     * @param p1
     * @param p2
     * @return la distancia calculada
     */
    private static double calcularDistancia(Punto p1, Punto p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    /**
     * REPASAR LA LECTURA DE FICHEROS
     * Lee de fichero los puntos dónde se va a aplicar el algoritmo
     * 
     * @param nombreFichero
     * @return Una lista con los puntos leídos de fichero
     */
    public static List<Punto> leerPuntos(String nombreFichero) {
        List<Punto> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

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
