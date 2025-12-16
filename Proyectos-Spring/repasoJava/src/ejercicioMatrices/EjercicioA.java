package ejercicioMatrices;

import java.util.Scanner;

public class EjercicioA {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int columna = 0;
		int fila = 5;
		int nAleatorio = 0;

		System.out.println("Introduce el número de columnas:");
		columna = s.nextInt();
		int[][] matriz = new int[fila][columna];

		System.out.println();
		for (int i = 0; i < fila; i++) {
			for (int j = 0; j < columna; j++) {
				matriz[i][j] = aleatorio();
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}

		s.close();
	}

	public static int aleatorio() {
		int n = (int) (Math.random() * 11);
		return n;

	}

}
