package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	@SuppressWarnings("unused")
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	@SuppressWarnings("unused")
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO meteoDAO;
	private List<Citta> listaCitta;
	private List<SimpleCity> listaSimpleCity;
	private List<SimpleCity> migliore;
	private Double costoMigliore;
	private List<String> risultati;

	public Model() {
		this.meteoDAO = new MeteoDAO();
		this.listaCitta = new ArrayList<>(this.meteoDAO.getCitta());
		this.migliore = new ArrayList<>();
		this.risultati = new ArrayList<>();
	}

	private List<SimpleCity> inizializzaListaSimpleCity() {
		List<SimpleCity> res= new ArrayList<>();
		for(Citta c : this.listaCitta)
			res.add(new SimpleCity(c.getNome()));
		return res;
	}

	public String getUmiditaMedia(int mese) {
		
		List<Citta> medieMeseCitta = new ArrayList<>(meteoDAO.getAvgUmiditaTutteLocalitaMese(mese));
		StringBuilder sb = new StringBuilder();
		for(Citta c : medieMeseCitta)
			sb.append(c.getNome() + " "+ c.getMedieMensiliUmidita().get(mese-1)+"%\n");
		
		return sb.toString();
	}

	public String trovaSequenza(int mese) {

		this.listaSimpleCity = this.inizializzaListaSimpleCity();
		this.costoMigliore = COST*14 + (double)this.getSommaUmiditaMassima(mese);
		this.setRilevamentiCittaMese(mese);
		
		for(Citta c: this.listaCitta)
			c.setCounter(0);
		
		this.risultati.clear();
		this.migliore.clear();
		
		this.recursive(0, migliore);
		
		return this.migliore.toString()+ " costo totale = "+this.costoMigliore;
	}

	private void recursive(int livello, List<SimpleCity> parziale) {

		//La list di SimpleCity è di 15 elementi (15 giorni) ogni oggetto SimpleCity al suo interno rappresesnta dunque
		//il fatto che il tecnico nel giorno n.mo dei 15 giorni si trova nella citta indicata dal SimpleCity
		
		//un livello è un giorno del mese
		
		if(livello >= NUMERO_GIORNI_TOTALI && controllaParziale(parziale)) { //soluzione completa
			double costoCandidata = this.punteggioSoluzione(parziale); 
			if(costoCandidata<this.costoMigliore) {
				this.migliore = new ArrayList<>(parziale);
				this.costoMigliore = costoCandidata;
			}
			this.risultati.add(parziale.toString()+" costo = "+costoCandidata+"\n");
			return;
		}
		
		for(Citta c: this.listaCitta) {
			
			if(!controllaParziale(parziale)) //controllo sol. parziale
				return;
			
			parziale.add(new SimpleCity(c.getNome(), c.getRilevamenti().get(livello).getUmidita()));
			c.increaseCounter();
			
			recursive(livello+1, parziale);
			
			parziale.remove(livello);
			c.setCounter(c.getCounter()-1);
		}
	}
	private int getSommaUmiditaMassima(int mese) {
		int maxAvg = 100;
		int somma;
		for(Citta c: this.listaCitta) {
			somma = 0;
			for(Rilevamento r : c.getRilevamenti())
				somma += r.getUmidita();
			if(somma>maxAvg)
				maxAvg=somma;
		}
		return maxAvg;
	}

	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		int contoSpostamenti=0;
		
		for(int i =0 ; i< soluzioneCandidata.size()-1; i++)
			if(soluzioneCandidata.get(i).getNome().compareTo(soluzioneCandidata.get(i+1).getNome())!=0)
				contoSpostamenti++;
		
		for(SimpleCity sc: soluzioneCandidata)
			score += sc.getCostoUmidita();
		
		return score+100*contoSpostamenti;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {
		int	giorniStessaCitta=1; 
		boolean cambioCitta =false;
		
		if(parziale.size() == 15 && !(parziale.containsAll(this.listaSimpleCity))) //tutte citta almeno una volta
			return false;
		
		for(int i =0; i<parziale.size()-1; i++) {
			if(parziale.get(i).equals(parziale.get(i+1)))
				giorniStessaCitta++;
			else
				cambioCitta = true;
			
			if(cambioCitta && giorniStessaCitta<3) //min 3 gg
				return false;
		}
		
		for(Citta c: this.listaCitta)
			if(c.getCounter() > 6)
				return false;
	
		return true;
	}
	
	private void setRilevamentiCittaMese(int mese) {
		for(Citta c: this.listaCitta)
			c.setRilevamenti(this.meteoDAO.getRilevamentiPerCittaMese(c, mese));
	}
	public String stampaRisultati() {
		return this.risultati.toString();
	}

}
