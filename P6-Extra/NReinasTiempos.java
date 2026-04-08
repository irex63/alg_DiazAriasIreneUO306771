import java.util.ArrayList;
import java.util.List;

/**
 * Implementaremos en esta clase una resolución para el Problema de las N-Reinas
 * utilizando un algoritmo de Backtracking.
 * El objetivo es colocar N reinas en un tablero de ajedrez de NxN sin que
 * ninguna se ataque entre sí; es decir, que no compartan fila, columna ni
 * diagonal.
 * Vamos a evitar recorrer el tablero iterativamente para validar posiciones.
 * En su lugar, vamos a registrar las columnas y diagonales que ya están bajo
 * ataque utilizando tres arreglos booleanos:
 * uno para las columnas, otro para las diagonales principales (de arriba a
 * abajo, izquierda a derecha) y otro para las diagonales secundarias (de arriba
 * a abajo, derecha a izquierda).
 */
public class NReinasTiempos {

    private List<List<String>> soluciones; // cada lista interna de Strings representa un tablero completo donde cada
                                           // String es una fila.

    private boolean[] columnas; // indica si una columna específica (de 0 a N-1) ya tiene una reina.
    private boolean[] diagonalesPrincipales; // de arriba a abajo, izda a decha (\)
    private boolean[] diagonalesSecundarias; // de arriba a abajo, decha a izda (/)

    /**
     * Método principal que inicia el algoritmo.
     * 
     * @param n de tipo int. El tamaño del tablero y el número de reinas.
     * @return soluciones, de tipo List<List<String>>. Una lista con todas las
     *         configuraciones válidas del tablero
     */
    public List<List<String>> resolverNReinas(int n) {
        soluciones = new ArrayList<>();

        columnas = new boolean[n];
        diagonalesPrincipales = new boolean[2 * n - 1];
        diagonalesSecundarias = new boolean[2 * n - 1];
        // Inicializamos los rastreadores de ataques en columnas y diagonales (los
        // atributos de arriba)
        for (int i = 0; i < diagonalesPrincipales.length; i++) {
            if (i < n) {
                columnas[i] = true;
            }
            diagonalesPrincipales[i] = true;
            diagonalesSecundarias[i] = true;
        }

        // Inicializar el tablero vacío con puntos '.' (os doy un método)
        char[][] tablero = crearTablero(n);

        // Iniciar el backtracking
        // COMENZAMOS EN LA FILA 0
        backtracking(0, n, tablero);

        return soluciones;
    }

    /**
     * Método de backtracking para colocar las reinas en el tablero.
     * 
     * @param fila,    de tipo int. La fila actual en la que intentamos colocar una
     *                 reina.
     * @param n,       de tipo int. El tamaño del tablero y el número de reinas.
     * @param tablero, de tipo char[][]. La representación actual del tablero con
     *                 las reinas colocadas.
     */
    private void backtracking(int fila, int n, char[][] tablero) {
        // Caso base: si logramos llegar a la fila n, colocamos todas las reinas con
        // éxito
        if (fila == n) { // Si el estado actual es solución la guardamos y volvemos
            soluciones.add(construirSolucion(tablero));
            return;
        }

        for (int col = 0; col < n; col++) { // PARA CADA COLUMNA

            // Fórmulas matemáticas para identificar la diagonal a la que pertenece una
            // celda
            // Sirve para idetificar que índice del array se mete para identificar la
            // diagonal
            int d1 = fila - col + (n - 1); // Diagonal principal (\)
            int d2 = fila + col; // Diagonal secundaria (/)

            // 1. Avanzar -> Colocar la reina (meter un 'Q') y marcar la columna y
            // diagonales como atacadas
            // Para avanzar tenemos que comprobar haya hueco en la columna, diagoPrincipal y
            // diagoSecundaria
            if (columnas[col] && diagonalesPrincipales[d1] && diagonalesSecundarias[d2]) {
                tablero[fila][col] = 'Q';
                columnas[col] = false;
                diagonalesPrincipales[d1] = false;
                diagonalesSecundarias[d2] = false;
                // 2. backtracking
                backtracking(fila + 1, n, tablero);
                // 3. Retroceder
                tablero[fila][col] = '.';
                columnas[col] = true;
                diagonalesPrincipales[d1] = true;
                diagonalesSecundarias[d2] = true;
            }
        }
    }

    /**
     * Método auxiliar para crear un tablero vacío representado como una matriz de
     * caracteres
     * 
     * @param n, de tipo int. El tamaño del tablero (NxN)
     * @return tablero, de tipo char[][]. Un tablero inicializado con '.' indicando
     *         casillas vacias
     */
    private char[][] crearTablero(int n) {
        char[][] tablero = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = '.';
            }
        }
        return tablero;
    }

    /**
     * Método auxiliar para convertir la matriz de caracteres en una lista de
     * Strings
     */
    private List<String> construirSolucion(char[][] tablero) {
        List<String> solucion = new ArrayList<>();
        for (char[] fila : tablero) {
            solucion.add(new String(fila));
        }
        return solucion;
    }

    /**
     * Método auxiliar para pintar el tablero por consola
     */
    private static void pintarTablero(List<List<String>> resultado) {
        for (int i = 0; i < resultado.size(); i++) {
            System.out.println("Solución " + (i + 1) + ":");
            for (String fila : resultado.get(i)) {
                System.out.println(fila);
            }
            System.out.println();
        }
    }

    /**
     * Método principal para ejecutar el programa
     * 
     * @param args, el primer argumento es el valor de N, o se usará 4 por defecto.
     */
    public static void main(String[] args) {
        NReinas algoritmo = new NReinas();

        long t1, t2, t;
        for (int n = 4; n <= 20; n += 2) {
            t1 = System.currentTimeMillis();
            algoritmo.resolverNReinas(n);
            t2 = System.currentTimeMillis();
            t = t2 - t1;
            System.out.println("Para Tablero Tamaño : " + n +
                    "\tTiempo: " + t + " ms");
        }
    }

}