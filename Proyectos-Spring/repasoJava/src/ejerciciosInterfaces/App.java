package ejerciciosInterfaces;

import java.time.LocalDate;
import java.util.ArrayList;

public class App {

	public static void main(String[] args) {

		ArrayList <Producto> producto = new ArrayList <> ();
		producto.add(new Cereales("Oatmeal", tipoCereal.avena, 3, LocalDate.of(2025, 3, 11)));
		
		
	}

}
