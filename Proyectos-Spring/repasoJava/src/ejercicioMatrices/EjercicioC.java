package ejercicioMatrices;

public class EjercicioC {

	public static void main(String[] args) {
		int [][] marco = new int [8][6];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 6; j++) {
				if(i == 0 || i == 7) {
					marco[i][j] = 1;
				} else if(j == 0 || j == 5) {
					marco[i][j] = 1;
				} else {
					marco[i][j] = 0;
				}
			}
		}
		System.out.println("Matriz resultante");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(marco[i][j] + " ");
			}
			System.out.println();
		}

	}

}
