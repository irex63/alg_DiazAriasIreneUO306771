package algstudent.s4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColoreoGrafo {

    private static ArrayList<String> colores;
    private static int maxColor=colores.size();


    /**
     * Colorea el mapa de forma que los nodos adyacentes sean de distinto color.
     * @param grafo Cada nodo es una entrada de la tabla y luego la lista asginada es 
     * la lista de nodos adyacentes a esa tabla.
     * @return Una tabla dónde cada entrada es un nodo y luego una cadena identidicando
     * de ese nodo
     */
    public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo){
        Map<String, String> result= new HashMap<>();
        //Recorremos todos las entradas de la tabla, es decir, todos los nodos del grafo
        for(String nodoActual : grafo.keySet()){
            //Visitamos los vecinos del nodos
            List<String> vecinos=grafo.get(nodoActual);
            //Miramos que colores no están disponibles para nuestro nodoActual
            //Para marcar esas disponibilidades 
            Boolean[] disponibilidad=new Boolean[maxColor];
            for(String vecino:vecinos){
                if(result.containsKey(vecino)){
                    String color= result.get(vecino);
                }
            }
        }
        return null;
    }
}
