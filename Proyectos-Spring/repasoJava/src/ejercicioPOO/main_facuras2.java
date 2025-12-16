package ejercicioPOO;

public class main_facuras2 {

	public static void main(String[] args) {
		double facturaTotal = 0;
		int factura600 = 0;
		Factura2 factura1 = new Factura2 ("1", 55 );
		Factura2 factura2 = new Factura2 ("1", 100);
		Factura2 factura3 = new Factura2 ("2", 83);
		Factura2 factura4 = new Factura2 ("2", 43);
		Factura2 factura5 = new Factura2 ("3", 28);
		
		Factura2[] factura  = {factura1, factura2, factura3, factura4, factura5};
		
		for (Factura2 f : factura) {
		    facturaTotal += f.calcularImporte();
		}
		System.out.println("La facturación total es de " + facturaTotal);
		for (Factura2 f : factura) {
		    if( f.calcularImporte() > 600) {
		    	factura600 ++;
		    }
		}
		System.out.println("La cantidad de facturas que superon los 600€ son: " + factura600);
		

	}

}
