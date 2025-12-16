package estructurasDinamicas;

import java.util.ArrayList;

public class Ejercicio4e {

	public static void main(String[] args) {
		 ArrayList<String> marca = new ArrayList<>();
		 
		 marca.add("sony");
		 marca.add("jbl");
		 marca.add("audi");
		 marca.add("BOSS");
		 
		 System.out.println(marca);
		 
		 marca.set(2, "tesla"); 
		 
		 System.out.println(marca);
		 
	}

}
