package ejercicioMatrices;


import java.util.Scanner;

public class EjercicioB {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int m = 0;
		int n = 0;
		System.out.println("Escribe el tamaño de la matriz:");
		m = s.nextInt();
		int[][] matriz1 = new int[m][m];
		int[][] matriz2 = new int[m][m];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				System.out.println("Escriba el valor para la fila " + i + " y columna " + j + " de la matriz 1:");

				n = s.nextInt();
				matriz1[i][j] = n;
				System.out.println("Escriba el valor para la fila " + i + " y columna " + j + " de la matriz 2:");
				n = s.nextInt();
				matriz2[i][j] = n;
			}
		}
		System.out.println("Matriz 1");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(matriz1[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("Matriz 2");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(matriz2[i][j] + " ");
			}
			System.out.println();
		}
		sumaMatrices(matriz1, matriz2, m);
		s.close();
	}

	public static void sumaMatrices(int m1[][], int m2[][], int m) {
		int[][] matrizSuma = new int[m][m];
		
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				matrizSuma[i][j] = m1[i][j] + m2[i][j];
			}
		}
		System.out.println("Matriz resultante");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < m; j++) {
				System.out.print(matrizSuma[i][j] + " ");
			}
			System.out.println();
		}
	}

}
