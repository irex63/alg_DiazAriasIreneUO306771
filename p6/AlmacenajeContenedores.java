package p6;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedores {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Entrada mal introducida");
            return;
        }

        String ruta = "CasosPrueba/" + args[0];
        try (Scanner sc = new Scanner(new File(ruta))) {
            int C = sc.nextInt();
            List<Integer> listaObjetos = new ArrayList<>();
            while (sc.hasNextInt()) {
                listaObjetos.add(sc.nextInt());
            }

            int[] objetos = new int[listaObjetos.size()];
            for (int i = 0; i < listaObjetos.size(); i++) {
                objetos[i] = listaObjetos.get(i);
            }
            AlmacenajeContenedores alg = new AlmacenajeContenedores(C, objetos);
            alg.run();

        } catch (Exception e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // Capacidad máxima de cada contenedor
    private int capacidadMaxima;
    // Objectos que se busca almacenar
    private int[] objetos;

    // Carga total de cada contenedor
    private int[] cargaActual;
    // Número de objetos actuales
    private int[] numeroActual;
    // Matriz para guardar estados: [cont][obj]
    private int[][] asignacionActual;

    // Solución encontrada
    private int mejorK;
    private int[][] mejorAsignacion;
    private int[] mejorContadores;
    private long numLlamadas = 0;

    public AlmacenajeContenedores(int capacidad, int[] objetos) {
        this.capacidadMaxima = capacidad;
        this.objetos = objetos;
        this.mejorK = objetos.length + 1;

        // Inicializamos los valores sobre los que vamos a trabajar
        int n = objetos.length;
        this.cargaActual = new int[n];
        this.asignacionActual = new int[n][n];
        this.numeroActual = new int[n];
    }

    public void run() {
        solve(0, 0);
        imprimirResultado();
    }

    /**
     * ALTURA DEL ÁRBOL DE LLAMDAS : Número de objetos
     * EXTENSIÓN DE CADA LLAMADA : Dónde colocamos cada objeto en cada llamada.
     * Si tenemos dos contendores libres existen dos posibilidades de dónde colocar
     * el objeto
     * 
     * @param objIdx           objeto que vamos a introducir
     * @param contedoresUsados contenedores que se están usando
     */
    private void solve(int objIdx, int contedoresUsados) {
        // Sumamos el número de llamadas
        numLlamadas++;

        // Todos los objetos están dentro de los contenedores
        // El estado actual es solución
        if (objIdx == objetos.length) {
            // Si la solución usa menos cont que la actual mejor
            if (contedoresUsados < mejorK) {
                // Actualizamos la solución previa
                mejorK = contedoresUsados;
                guardarSol(contedoresUsados);
            }
            return;
        }

        // Intentamos colocar en contenedores ya usados
        for (int cont = 0; cont < contedoresUsados; cont++) {
            // Para cada contenedor
            int objectoActual = objetos[objIdx];
            if (cargaActual[cont] + objectoActual <= capacidadMaxima) {
                // Si el objeto actual cabe dentro del contenedor
                // Pasamos de estado y hacemos llamda recursiva
                cargaActual[cont] += objectoActual;
                // Para el contenedor actual y el objecto actual guardamos el peso
                asignacionActual[cont][numeroActual[cont]] = objectoActual;
                // Incrementamos el número de objetos
                numeroActual[cont]++;

                // Hacemos la llamada recursiva con el siguiente objeto
                solve(objIdx + 1, contedoresUsados);

                // Restauramos el estado previo
                // Decrementar el número de objectos de ese contenedor
                numeroActual[cont]--;
                // Disminuir la carga de ese contenedor
                cargaActual[cont] -= objetos[objIdx];
            }
        }

        // Intentamos colocar el objeto en un contenedor nuevo
        // Cambiamos de estado
        cargaActual[contedoresUsados] = objetos[objIdx];
        asignacionActual[contedoresUsados][numeroActual[contedoresUsados]] = objetos[objIdx];
        numeroActual[contedoresUsados]++;

        // Llamada recursiva
        solve(objIdx + 1, contedoresUsados + 1);

        // Restauramos el estado
        numeroActual[contedoresUsados]--;
        cargaActual[contedoresUsados] = 0;

    }

    private void guardarSol(int k) {
        mejorAsignacion = new int[k][objetos.length];
        mejorContadores = new int[k];
        for (int i = 0; i < k; i++) {
            mejorContadores[i] = numeroActual[i];
            for (int j = 0; j < numeroActual[i]; j++) {
                mejorAsignacion[i][j] = asignacionActual[i][j];
            }
        }
    }

    private void imprimirResultado() {
        System.out.println("Lista de contenedores y objetos contenidos:");
        for (int i = 0; i < mejorK; i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int j = 0; j < mejorContadores[i]; j++) {
                System.out.print(mejorAsignacion[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + mejorK + ".");
        System.out.println("Número de llamadas recursivas: " + numLlamadas);
    }

}