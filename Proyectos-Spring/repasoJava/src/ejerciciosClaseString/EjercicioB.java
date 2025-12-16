package ejerciciosClaseString;

import java.util.Scanner;

public class EjercicioB {

	public static void main(String[] args) throws Exception {
		Scanner s = new Scanner(System.in);
		String fecha1 = "";
		String fecha2 = "";
		String primeraFecha = "";
		String segundaFecha = "";
		int opcion = 0;
		int dias = 0;
		
		System.out.println("Bienvenido al restador de fechas. EL formato de una fecha es d: dd/mm/yyy");
		System.out.println("Escribe la primera fecha:");
		fecha1 = s.nextLine();
		System.out.println("Escribe la segunda fecha:");
		fecha2 = s.nextLine();
		System.out.println("¿Cual de las 2 fechas será la primera?(Elija 1 o 2)");
		opcion = s.nextInt();
		
		if(opcion == 2) {
			primeraFecha = fecha2;
			segundaFecha = fecha1;
		} else {
			primeraFecha = fecha1;
			segundaFecha = fecha2;
		}
		
		System.out.println("La primera fecha es entonces:" + primeraFecha);
		System.out.println("La segunda fecha es entonces:" + segundaFecha);
		
		dias = diferenciaDias(primeraFecha, segundaFecha);
		s.close();
	}

	
	public static int diferenciaDias(String f1, String f2) throws Exception {
        int[] fecha1 = convertirFecha(f1);
        int[] fecha2 = convertirFecha(f2);

        // Convertir ambas fechas a días
        int dias1 = fecha1[0] + fecha1[1] * 30 + fecha1[2] * 365;
        int dias2 = fecha2[0] + fecha2[1] * 30 + fecha2[2] * 365;
        
        // Comprobar que siempre da positivo
        if(dias1> dias2) {
        	return (dias1-dias2);
        } else {
        	return (dias2-dias1);
        }
    }
	
	// Convierte lso string en número eliminando los símbolos "/"
	 public static int[] convertirFecha(String fecha) throws Exception {
	        String[] partes = fecha.split("/");

	        if (partes.length != 3) {
	            throw new NumberFormatException("Formato inválido: " + fecha);
	        }

	        int dia = Integer.parseInt(partes[0]);
	        int mes = Integer.parseInt(partes[1]);
	        int anio = Integer.parseInt(partes[2]);

	        return new int[]{dia, mes, anio};
	    }
}
