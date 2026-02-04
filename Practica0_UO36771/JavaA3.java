package p0;

import java.util.ArrayList;

public class JavaA3 {

   //IMPLEMENTACIÓN DEL TERCER ALGORITMO EN JAVA
   public static boolean primoA3(int m) {
      for(int i=2;i<=m/2;i++){
         if(m%i==0){
            return false;
         }
      }
      return true;
   }

   public static void listadoPrimos(int n) {
      ArrayList<Integer> lista = new ArrayList<Integer>();
      int contPrimos = 0;

      for (int i = 2; i <= n; i++)
         if (primoA3(i)) {
            lista.add(i);
            contPrimos++;
         }

      System.out.println("Hay " + contPrimos + " primos hasta " + n);
      // System.out.println(lista);

   }

   public static void main(String arg[]) {

      System.out.println("TIEMPO EN JAVA DEL ALGORITMO A3");

      long t1, t2; // obligatoriamente de tipo long para no desbordar

      for (int n = 5000; n <= 1000000; n *= 2) {
         t1 = System.currentTimeMillis(); // milisegundos (sin decimales)

         listadoPrimos(n);

         t2 = System.currentTimeMillis();

         System.out.println(t1 + "///" + t2);

         System.out.println("n=" + n + "**** tiempo = " + (t2 - t1) + " milisegundos \n");

      }
   } // de main

} // de clase
