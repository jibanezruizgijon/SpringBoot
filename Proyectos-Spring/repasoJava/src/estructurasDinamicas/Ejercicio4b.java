 package estructurasDinamicas;

import java.util.ArrayList;

public class Ejercicio4b {

	public static void main(String[] args) {
		ArrayList <String> deportes =  new ArrayList<> ();

		deportes.add("baloncesto");
		deportes.add("futbol");
		deportes.add("voley");
		deportes.add("padel");
		
		
		System.out.println(deportes);
	
		
		deportes.clear();
  
            if(deportes.isEmpty()) {
            	System.out.println("El ArrayList está vacío.");            	
            } else {
            	System.out.println("El ArrayList no está vacío."); 
            }
	}
}
