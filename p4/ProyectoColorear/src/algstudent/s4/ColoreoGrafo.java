package algstudent.s4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColoreoGrafo {

    private static ArrayList<String> colores = new ArrayList<>(Arrays.asList(
            "red", "blue", "green", "yellow", "orange", "purple", "cyan", "magenta", "lime"));


    /**
     * Colorea el mapa de forma que los nodos adyacentes sean de distinto color.
     * @param grafo Cada nodo es una entrada de la tabla y luego la lista asginada es 
     * la lista de nodos adyacentes a esa tabla.
     * @return Una tabla dónde cada entrada es un nodo y luego una cadena identidicando
     * de ese nodo
     */
    public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo){
        Map<String, String> result= new HashMap<>();
        //Recorremos todos los nodos del grafo
        for(String nodoActual : grafo.keySet()){ //0(n)
            //Visitamos los vecinos del nodos
            List<String> vecinos=grafo.get(nodoActual);
            //Creamos una copia de los colores disponibles
            List<String> disponibles=new ArrayList<String> (colores);
            //Buscamos quitar los colores no válidos de dentro de la lista de disponibles
            for (Object vecinoObj : vecinos) {
                //Vecino actual
                String vecino = String.valueOf(vecinoObj);
                //Si el vecino ya fue pintado
                if(result.containsKey(vecino)){ 
                    //Obtenemos su color
                    String color= result.get(vecino);
                    //Quitamos el color de la lista de disponibles
                    if(disponibles.contains(color)){
                        disponibles.remove(color);
                    }
                }
            }
            //Ahora disponibles.get(0) tiene el color que debemos poner a nuestro nodo
            result.put(nodoActual,disponibles.get(0));
        }
        return result;
    }
}
