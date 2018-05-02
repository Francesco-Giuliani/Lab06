package it.polito.tdp.meteo.bean;

import java.time.LocalDate;

public class Rilevamento {

	private Citta localita;
	private LocalDate data;
	private int umidita;

	public Rilevamento(Citta localita, LocalDate data, int umidita) {
		super();
		this.localita = localita;
		this.data = data;
		this.umidita = umidita;
	}

	public Citta getLocalita() {
		return localita;
	}

	public void setLocalita(Citta localita) {
		this.localita = localita;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getUmidita() {
		return umidita;
	}

	public void setUmidita(int umidita) {
		this.umidita = umidita;
	}

	// @Override
	// public String toString() {
	// return localita + " " + data + " " + umidita;
	// }

	@Override
	public String toString() {
		return String.valueOf(umidita);
	}

}
