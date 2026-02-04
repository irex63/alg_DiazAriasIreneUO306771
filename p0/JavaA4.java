package p0;

import java.util.ArrayList;

public class JavaA4 {

   //IMPLEMENTACIÓN DEL CUARTO ALGORITMO EN JAVA
   //Calcula y devuelve todos los primos hasta n
   public static void primosA4(int n) {
      boolean[] listaNumeros= new boolean[n+1];
      for(int i=0;i<=n;i++){
         listaNumeros[i]=true;
      }

      int x=2;
      while ((x*x)<=n){
         if(listaNumeros[x]){
            int paso=2*x;
            while (paso <=n){
               listaNumeros[paso]=false;
               paso=paso+x;
            }
         }
         x++;
      }
      ArrayList<Integer> listaSal = new ArrayList<>();
      int contarPrimos =0;
      for(int i=2;i<=n;i++){
         if(listaNumeros[i]){
            listaSal.add(i);
            contarPrimos++;
         }
      }
      System.out.println("Hay " + contarPrimos + " primos hasta " + n);
   }

   public static void main(String arg[]) {

      System.out.println("TIEMPO EN JAVA DEL ALGORITMO A4");

      long t1, t2; // obligatoriamente de tipo long para no desbordar

      for (int n = 5000; n <= 1000000; n *= 2) {
         t1 = System.currentTimeMillis(); // milisegundos (sin decimales)

         primosA4(n);

         t2 = System.currentTimeMillis();

         System.out.println(t1 + "///" + t2);

         System.out.println("n=" + n + "**** tiempo = " + (t2 - t1) + " milisegundos \n");

      }
   } // de main

} // de clase
