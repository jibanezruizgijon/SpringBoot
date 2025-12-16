package ejercicioPOO;

public class Cuenta {

	private String titular;
	private double cantidad;

	public Cuenta(String titular, double cantidad) {
		super();
		this.titular = titular;
		this.cantidad = cantidad;
	}

	public Cuenta(String titular) {
		super();
		this.titular = titular;
	}

	public void ingresar (double cantidad) {
		this.cantidad += cantidad;
		System.out.println("Dinero ingresado");
	}

	public void retirar (double cantidad) {
		if(this.cantidad< cantidad) {
			System.out.println("No se pueden retirar " + cantidad + "$ porque no tiene suficiente dinero");
		} else {
			this.cantidad -= cantidad;
			System.out.println("Dinero retirado");
		}
		
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public static void main(String[] args) {

	}

	@Override
	public String toString() {
		return "EjercicioA [titular=" + titular + ", cantidad=" + cantidad + "]";
	}

}
