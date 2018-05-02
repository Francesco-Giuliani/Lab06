package it.polito.tdp.meteo.db;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Citta c = new Citta(rs.getString("Localita"));
				Rilevamento r = new Rilevamento(c, rs.getDate("Data").toLocalDate(), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

public List<Citta> getAvgUmiditaTutteLocalitaMese(int mese){	
		
		LocalDate dataIniziale = LocalDate.of(2013, mese, 1);
		LocalDate dataFinale = dataIniziale.with(TemporalAdjusters.lastDayOfMonth());
		Date dataInizialeSQL = Date.valueOf(dataIniziale);
		Date dataFinaleSQL = Date.valueOf(dataFinale);
		
		String sql = "SELECT Localita, AVG(Umidita) as u FROM situazione as s WHERE s.Data >= ? and s.Data <= ? GROUP BY localita";
		List<Citta> cittaConMedie = new ArrayList<Citta>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setDate(1, dataInizialeSQL);
			st.setDate(2, dataFinaleSQL);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Citta c = new Citta(rs.getString("Localita"));
				c.getMedieMensiliUmidita().set(mese-1, rs.getDouble("u"));
				
				cittaConMedie.add(c);
			}

			conn.close();
			return cittaConMedie;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public List<Citta> getCitta(){
		
		String CittaSQL= "select localita from situazione group by(localita)";
		List<Citta> listaCitta = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st= conn.prepareStatement(CittaSQL);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Citta c = new Citta(res.getString("localita"));
				listaCitta.add(c);
			}
		
			conn.close();
			return listaCitta;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("Errore DB");
		}
		return null;
	}
	public List<Rilevamento> getRilevamentiPerCittaMese(Citta c, int mese){
		Date inizioMese = Date.valueOf(LocalDate.of(2013, mese, 1));
		Date fineMese = Date.valueOf(LocalDate.of(2013, mese, 15));
		
		String RilevamentiSQL = "select Data, Umidita FROM situazione WHERE localita = ? and data >= ? and data <= ?";
		List<Rilevamento> listaRilevamenti = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st= conn.prepareStatement(RilevamentiSQL);
	
			st.setString(1, c.getNome());
			st.setDate(2, inizioMese);
			st.setDate(3, fineMese);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Rilevamento r = new Rilevamento(c, res.getDate("Data").toLocalDate(), res.getInt("Umidita"));
				listaRilevamenti.add(r);
			}
		
			conn.close();
			return listaRilevamenti;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("Errore DB");
		}
		return null;
	}
	
}
