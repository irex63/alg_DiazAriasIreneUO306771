package p7;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedoresTiempos {
    public static void main(String[] args) {

        String ruta = "casosPrueba/test0";
        String extension = ".txt";
        long t1, t2, t;

        for (int i = 0; i < 10; i++) {
            String archivo = ruta + i + extension;
            try (Scanner sc = new Scanner(new File(archivo))) {
                int C = sc.nextInt();
                List<Integer> listaObjetos = new ArrayList<>();
                while (sc.hasNextInt()) {
                    listaObjetos.add(sc.nextInt());
                }

                int[] objetos = new int[listaObjetos.size()];
                for (int j = 0; j < listaObjetos.size(); j++) {
                    objetos[j] = listaObjetos.get(j);
                }
                AlmacenajeContenedoresTiempos alg = new AlmacenajeContenedoresTiempos(C, objetos);
                t1 = System.currentTimeMillis();
                alg.run();
                t2 = System.currentTimeMillis();
                t = t2 - t1;
                System.out.println("Para archivo: " + archivo +
                        "\tTiempo: " + t + " ms");

            } catch (Exception e) {
                System.err.println("Error al leer el archivo: " + e.getMessage());
            }
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

    // Soluciones encontrada
    private int mejorK;
    private int[][] mejorAsignacion;
    private int[] mejorContadores;
    private long numLlamadas = 0;

    public AlmacenajeContenedoresTiempos(int capacidad, int[] objetos) {
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
    }

    private void solve(int objIdx, int contedoresUsados) {
        // Sumamos el número de llamadas
        numLlamadas++;

        // // REALIZAMOS LA PODA
        // if (contedoresUsados >= mejorK)
        // return;

        // Todos los objetos están dentro de los contenedores, es decir, el estado
        // actual es solución
        if (objIdx == objetos.length) {
            // Si la solución usa menos contenedores que la actual
            if (contedoresUsados < mejorK) {
                // Actualizamos la solución previa
                mejorK = contedoresUsados;
                guardarSol(contedoresUsados);
            }
            return;
        }
        // DOS OPCIONES :
        // 1. Intentamos colocar en contenedores ya usados
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

        // 2. Intentar colocar en nuevo contenedor
        // if (contedoresUsados < mejorK - 1) { // PODA : solo lo hacemos si puede haber
        // mejora
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