package ejerciciosInterfaces;

public class Detergente extends Producto implements EsLiquido, ConDescuento {

	private int volumen;
	private String tipoEnvase;
	private double descuento;

	public Detergente(String marca, double precio, String marca2, double precio2, int volumen, String tipoEnvase,
			double descuento) {
		super(marca, precio);
		marca = marca2;
		precio = precio2;
		this.volumen = volumen;
		this.tipoEnvase = tipoEnvase;
		this.descuento = descuento;
	}

	@Override
	public void setVolumen(int v) {
		this.volumen = v;

	}

	@Override
	public int getVolumen() {
		return volumen;
	}

	@Override
	public void setTipoEnvase(String env) {
		this.tipoEnvase = env;

	}

	@Override
	public String getTipoEnvase() {
		return tipoEnvase;
	}

	@Override
	public void setDescuento(double des) {
		this.descuento = des;

	}

	@Override
	public double getDescuento() {
		return descuento;
	}

	@Override
	public double getPrecioDescuento() {
		return precio - (precio * descuento / 100);
	}

	@Override
	public String toString() {
		return "Detergente [volumen=" + volumen + ", tipoEnvase=" + tipoEnvase + ", descuento=" + descuento + ", marca="
				+ marca + ", precio=" + precio + "]";
	}

}
