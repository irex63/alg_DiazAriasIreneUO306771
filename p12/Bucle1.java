package p12;

public class Bucle1 {
	public static long bucle1(long n) { //O(n*log (n))
		long cont = 0;
		long n1 = 1;
		while (n1 <= n * n) { //O(log3 (n^2)) = O (2*log3 (n))
			for (long i = 1; i <= 2 * n; i += 3) {//O(n)
				cont++;
			}
			n1 = 3 * n1;
		}

		return cont;
	}

	public static void main(String arg[]) {
		long t1, t2;
		int nVeces = Integer.parseInt(arg[0]);

		System.out.println("n\ttiempo\trepeticiones\tcontador");

		for (long n = 100; n <= 819200; n *= 2) {
			long c = 0;
			t1 = System.currentTimeMillis();

			for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) {
				c = bucle1(n);
			}

			t2 = System.currentTimeMillis();

			System.out.println(n + "\t" + (t2 - t1) + "\t" + nVeces + "\t\t" + c);
		} // for
	} // main
} // class