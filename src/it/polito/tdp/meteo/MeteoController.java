package it.polito.tdp.meteo;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MeteoController {

	private Model model;
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Integer> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		this.txtResult.clear();
		String res;
		try {
			int selected = this.boxMese.getValue(); 

			if(selected == 0 ||	selected<1 || selected>12)
				throw new Exception("Nessun mese selezionato."); 
			
			res = this.model.getUmiditaMedia(this.boxMese.getValue());
			this.txtResult.appendText(res+"\n");
		
		} catch (Exception e) {
			this.txtResult.appendText("Nessun mese selezionato. Selezionare un valore valido dalla choice box.");
			e.printStackTrace();
		}
		
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
		this.inizializzaMesi();
	}

	private void inizializzaMesi() {
		List<Integer> mesi = new LinkedList<>();
		for(int i =0 ; i< 12; i++) {
			mesi.add(i+1);
		}
		this.boxMese.getItems().setAll(mesi);
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
	public Model getModel() {
		return this.model;
	}

}
