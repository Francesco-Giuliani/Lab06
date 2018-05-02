package Tempo;
import java.security.InvalidParameterException;

public class DataAAAAMMGG {
	
	private int anno, mese, giorno;
	private boolean bisestile;
	private String annoStr, meseStr, giornoStr;
	private int ultimoGiornoMese;

	public DataAAAAMMGG(int anno, int mese, int giorno, boolean bisestile) {
		super();
		this.anno = anno;
		this.mese = mese;
		this.giorno = giorno;
		this.bisestile = bisestile; //TODO aggiungi check automatico bisestilita
		this.annoStr = Integer.toString(this.anno);
		this.meseStr = this.eleboraIntMese();
		this.giornoStr = this.elaboraIntData();
		if(!this.verificaValiditaData())
			throw new InvalidParameterException("Data non valida. Fornire valori corretti per giorno mese e anno.\n"
					+"Rispettare il formato aaaa-mm-gg.");
		this.ultimoGiornoMese = this.calcolaUltimoGiornoMese();
	}

	

	private int calcolaUltimoGiornoMese() {
		
		switch(this.mese) {
			case 1: return 31;
			case 2: if(this.bisestile)
						return 29;
					else 
						return 28;
			case 3: return 31;
			case 4: return 30;
			case 5: return 31;
			case 6: return 30;
			case 7: return 31;
			case 8: return 31;
			case 9: return 30;
			case 10: return 31;
			case 11: return 30;
			case 12: return 31;
		}
		return 0;
	}



	public DataAAAAMMGG(int anno, int mese, boolean bisestile) {
		super();
		this.anno = anno;
		this.mese = mese;
		this.bisestile = bisestile;
	}



	public DataAAAAMMGG(String annoStr, String meseStr, String giornoStr) {
		super();
		this.annoStr = annoStr;
		this.meseStr = meseStr;
		this.giornoStr = giornoStr;
	}

	@Override
	public String toString() {
		return annoStr + "-" + meseStr + "-" + giornoStr;
	}
	
	private String eleboraIntMese() {
		String mese;
		if(this.mese <10)
			mese =  "0"+Integer.toString(this.mese);
		else
			mese = Integer.toString(this.mese);
		return mese;
	}
	
	
	private String elaboraIntData() {
		String giorno;
		if(this.giorno <10)
			giorno =  "0"+Integer.toString(this.giorno);
		else
			giorno = Integer.toString(this.giorno);
		return giorno;
	}
	
	private boolean  verificaValiditaData() {
		//TODO
		return true;
	}



	public int getAnno() {
		return anno;
	}



	public int getMese() {
		return mese;
	}



	public int getGiorno() {
		return giorno;
	}



	public boolean isBisestile() {
		return bisestile;
	}



	public String getAnnoStr() {
		return annoStr;
	}



	public String getMeseStr() {
		return meseStr;
	}



	public String getGiornoStr() {
		return giornoStr;
	}



	public int getUltimoGiornoMese() {
		return ultimoGiornoMese;
	}
	
	
}
