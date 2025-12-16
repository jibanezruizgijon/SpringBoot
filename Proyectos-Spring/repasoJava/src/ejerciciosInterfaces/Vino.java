package ejerciciosInterfaces;

import java.time.LocalDate;

public class Vino extends Producto implements EsLiquido, EsAlimento, ConDescuento {

	private String tipoVino;
	private double grados;
	private int volumen;
	private String tipoEnvase;
	private LocalDate caducidad;
	private double descuento;

	public Vino(String marca, double precio, String tipoVino, double grados, int volumen, String tipoEnvase,
			LocalDate caducidad, double descuento) {
		super(marca, precio);
		this.tipoVino = tipoVino;
		this.grados = grados;
		this.volumen = volumen;
		this.tipoEnvase = tipoEnvase;
		this.caducidad = caducidad;
		this.descuento = descuento;
	}

	public String getTipoVino() {
		return tipoVino;
	}

	public void setTipoVino(String tipoVino) {
		this.tipoVino = tipoVino;
	}

	public double getGrados() {
		return grados;
	}

	public void setGrados(double grados) {
		this.grados = grados;
	}

	@Override
	public void setCaducidad(LocalDate fc) {
		this.caducidad = fc;
	}

	@Override
	public LocalDate getCaducidad() {
		return caducidad;
	}

	@Override
	public int getCalorias() {
		return (int) (grados * 10);
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
		return "Vino [tipoVino=" + tipoVino + ", grados=" + grados + ", volumen=" + volumen + ", tipoEnvase="
				+ tipoEnvase + ", caducidad=" + caducidad + ", descuento=" + descuento + ", marca=" + marca
				+ ", precio=" + precio + "]";
	}
}
