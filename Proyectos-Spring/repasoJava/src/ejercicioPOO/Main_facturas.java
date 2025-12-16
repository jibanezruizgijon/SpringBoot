package ejercicioPOO;

public class Main_facturas {

	public static void main(String[] args) {
		double facturaTotal = 0;
		int factura600 = 0;
		Facturas factura1 = new Facturas ("r234", 55,3.3 );
		Facturas factura2 = new Facturas ("l824", 100,7 );
		Facturas factura3 = new Facturas ("m025", 83,4.5 );
		Facturas factura4 = new Facturas ("s742", 43,5.7 );
		Facturas factura5 = new Facturas ("p693", 28,2.7 );
		
		Facturas[] factura  = {factura1, factura2, factura3, factura4, factura5};
		
		for (Facturas f : factura) {
		    facturaTotal += f.calcularImporte();
		}
		System.out.println("La facturación total es de " + facturaTotal);
		for (Facturas f : factura) {
		    if( f.calcularImporte() > 600) {
		    	factura600 ++;
		    }
		}
		System.out.println("La cantidad de facturas que superon los 600€ son: " + factura600);
		
		System.out.println("La cantidad de litros vendidos del árticulo 1 es de: " + factura1.getLitro_vendidos());
	}

	

}
