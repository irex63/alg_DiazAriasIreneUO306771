import java.util.List;

public class Ferry {

    // Longitud de los carriles del barco
    private int boatLength;
    // Lista de coches que se quieren alamacenar en el ferry
    private List<Integer> vehicles;
    // Matriz con las posibles soluciones
    private boolean[][] dp;
    // Vector que almacena un sumatorio de la longitud de los coches anteriores al
    // coche en posición i
    private int[] sumatorio;
    private int maxVehicles;

    public Ferry(int boatLength, List<Integer> vehicles) {
        this.boatLength = boatLength;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size() + 1][boatLength + 1];
        this.sumatorio = new int[vehicles.size() + 1];
        rellenarSumatorio();
    }

    private void rellenarSumatorio() {
        this.sumatorio[0] = 0;
        for (int i = 1; i < sumatorio.length; i++) {
            this.sumatorio[i] += sumatorio[i - 1] + vehicles.get(i - 1);
        }
    }

    private void imprimirSumatorio(){
        for (int i = 0; i < sumatorio.length; i++) {
            System.out.print(sumatorio[i]+" ");
        }
    }

    public void run() {
        // Caso base
        dp[0][0] = true;
        // Resto de la fila tiene que ser false pero ya se inicializa a 0
        // Recorremos los coches que van a entrar al barco
        boolean thereIsSpaceLeft = false;
        for (int i = 1; i < vehicles.size(); i++) {
            // Recorremos las distintas longitudes para ver las posibilidades
            for (int p = boatLength; p >=0 ; p--) {
                // Caso 1:
                int caso1 = p + vehicles.get(i);
                if (caso1 <= boatLength) {
                    dp[i][caso1] = true;
                    thereIsSpaceLeft = true;
                }
                // Caso 2:
                int caso2 = sumatorio[i] - p;
                if (caso2 <= boatLength) {
                    dp[i][p] = true;
                    thereIsSpaceLeft = true;
                }

            }
            // Si no se ha metido nigún coche en el barco en esa iteración
            // Entonces no hay espacio restante en el barco
            if (!thereIsSpaceLeft) {
                break;
            } else {
                thereIsSpaceLeft = false;
            }
        }
        // Aquí la matriz ya está rellenada
    }

    public void runProfe() {
        dp[0][0] = true;
        
        for (int i = 1; i <= vehicles.size(); i++) {
            for (int l = boatLength; l >=0 ; l--) {
                if(!dp[i-1][l]){
                    continue;
                }
                
                // Meter babor
                int casoBabor = l + vehicles.get(i-1);
                if (casoBabor <= boatLength) {
                    dp[i][casoBabor] = true;
                }
                // Meter estribor
                int casoEstribor= sumatorio[i] - l;
                if (casoEstribor <= boatLength) {
                    dp[i][l] = true;
                }


            }
        }
        // Aquí la matriz ya está rellenada
    }

    public void imprimirMatriz() {
        for (int i = 0; i < vehicles.size(); i++) {
            for (int p = 0; p < dp[0].length; p++) {
                System.out.print(dp[i][p]+"  ");
            }
            System.out.println();
        }

    }
}
