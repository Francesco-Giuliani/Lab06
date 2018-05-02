package Tempo;

import java.time.*;
public class TestData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		java.time.LocalDate d = LocalDate.of(2013, 1, 1);
		
		LocalDate ultimoGiornoMese = d.with(java.time.temporal.TemporalAdjusters.lastDayOfMonth());


		System.out.println("primo giorno: "+d+"ultimo giorno: "+ultimoGiornoMese);

	}

}
