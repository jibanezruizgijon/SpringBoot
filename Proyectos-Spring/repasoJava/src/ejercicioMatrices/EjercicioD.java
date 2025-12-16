package ejercicioMatrices;

import java.util.Scanner;

public class EjercicioD {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = 0;
        int tabla[] = new int[10]; 

       
        System.out.println("Introduce 5 números:");
        for (int i = 0; i < 5; i++) {
            tabla[i] = s.nextInt();
        }

       
        System.out.println("Introduce un número más:");
        n = s.nextInt();
        tabla[5] = n;

        
        ordenamientoBurbuja(tabla, 6);

        
        System.out.println("Array ordenado:");
        for (int i = 0; i < 6; i++) {
            System.out.println(tabla[i]);
        }
    }

    // Método de ordenamiento burbuja
    public static void ordenamientoBurbuja(int[] tabla, int elementosUsados) {
        for (int pasada = elementosUsados - 1; pasada > 0; pasada--) {
            for (int i = 0; i < pasada; i++) {
                if (tabla[i] > tabla[i + 1]) {
                    int temp = tabla[i];
                    tabla[i] = tabla[i + 1];
                    tabla[i + 1] = temp;
                }
            }
        }
    }
}
