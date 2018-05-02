package it.polito.tdp.meteo;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		
		System.out.println("******** MIGLIORE MESE: 5 *********\n");
		System.out.println(m.trovaSequenza(5));
		/*System.out.println("\n******** Tutti i risultati mese: 5 *********\n");
		System.out.println(m.stampaRisultati());
		*/
		System.out.println("\n******** MIGLIORE MESE: 4 *********\n");
		System.out.println(m.trovaSequenza(4));
		/*System.out.println("\n******** Tutti i risultati mese: 4 *********\n");
		System.out.println(m.stampaRisultati());*/
	}

}
