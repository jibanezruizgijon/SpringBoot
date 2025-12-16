package ejercicioPOO;

public class Facturas {
	private String c_articulo;
	private int litro_vendidos;
	private double precio;

	public Facturas(String c_articulo, int litro_vendidos, double precio) {
		super();
		this.c_articulo = c_articulo;
		this.litro_vendidos = litro_vendidos;
		this.precio = precio;
	}

	public Facturas() {
		super();
	}

	public double calcularImporte() {
		return litro_vendidos * precio;
	}

	public String getC_articulo() {
		return c_articulo;
	}

	public void setC_articulo(String c_articulo) {
		this.c_articulo = c_articulo;
	}

	public int getLitro_vendidos() {
		return litro_vendidos;
	}

	public void setLitro_vendidos(int litro_vendidos) {
		this.litro_vendidos = litro_vendidos;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Facturas [c_articulo=" + c_articulo + ", litro_vendidos=" + litro_vendidos + ", precio=" + precio + "]";
	}

}
