package p3p;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PuntosDyVCorreccionClase {

    private double minDistancia = Double.MAX_VALUE;
    // 1 columna por cada punto
    // La fila uno contiene las x y la fila dos las y
    private static double[][] matrizPuntos;

    public static void main(Strin[] arg) {
        //Ordenar la matriz por la coordenada x
        Arrays.sort(matrizPuntos, Comparator.comparingDouble(matriz -> matriz[0]));

    }

    public static void buscarDistanciaMinima(){
        int izd=0;
        int dec=matrizPuntos.length-1;

        if(dec-izd==1){
            //Calcular distancia entre los dos puntos del caso base
        }


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

}
