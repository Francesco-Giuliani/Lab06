package it.polito.tdp.meteo.bean;

import java.util.ArrayList;
import java.util.List;

public class Citta {

	private String nome;
	private List<Double> medieMensiliUmidita;
	private List<Rilevamento> rilevamenti;
	private int counter = 0;
	
	public Citta(String nome) {
		this.nome = nome;
		this.rilevamenti = new ArrayList<>();
		this.medieMensiliUmidita = new ArrayList<>();
		this.inizializzaMedieMensili();
	}
	
	public Citta(String nome, List<Rilevamento> rilevamenti) {
		this.nome = nome;
		this.rilevamenti = new ArrayList<>( rilevamenti);
		this.medieMensiliUmidita = new ArrayList<>();
		this.inizializzaMedieMensili();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rilevamento> getRilevamenti() {
		return rilevamenti;
	}

	public void setRilevamenti(List<Rilevamento> rilevamenti) {
		this.rilevamenti = new ArrayList<>(rilevamenti);
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void increaseCounter() {
		this.counter += 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Citta other = (Citta) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

	public List<Double> getMedieMensiliUmidita() {
		return medieMensiliUmidita;
	}

	public void setMedieMensiliUmidita(List<Double> medieMensiliUmidita) {
		this.medieMensiliUmidita = new ArrayList<>(medieMensiliUmidita);
	}
	private void inizializzaMedieMensili() {
		for(int i=0; i<12; i++) {
			this.medieMensiliUmidita.add(-1.0);
		}
	}
	
}
