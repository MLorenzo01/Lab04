/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class FXMLController {
	
	private Model model;
	
    @FXML
    private Button btnCerca;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btn"
    private Button btn; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="btnOk"
    private Button btnOk; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCorsi"
    private ComboBox<String> cmbCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void CompletAuto(ActionEvent event) {
    	String input = txtMatricola.getText();
    	int inputNum = 0;
    	try {
    		inputNum = Integer.parseInt(input);
    	} catch (NumberFormatException e) {
    		txtRisultato.setText("Inserted Value is not an integer value");
    		return;
		}
    	String[] result = new String[2];
    	result = model.getIscritto(input);
    	txtNome.setText(result[0]);
    	txtCognome.setText(result[1]);
    }

    @FXML
    void btnReset(ActionEvent event) {
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtRisultato.clear();
    }

    @FXML
    void cercaCorsi(ActionEvent event) {
    	String matricola = this.txtMatricola.getText();
    	if(matricola.isEmpty()) {
    		txtRisultato.setText("Inserire una matricola");
    		return;
    	}
    	if(model.getIscritto(matricola) == null) {
    		txtRisultato.setText("Utente non esistente");
    	}
    	List<Corso> risultato = new ArrayList<Corso>();
    	risultato = this.model.getCorsi(matricola);
    	txtRisultato.clear();
    	for(Corso s: risultato) {
    		txtRisultato.appendText(String.format("%-10s %-20s %-30s %-20s\n",s.getCodins(),s.getCrediti(),s.getNome(),s.getPd()));
    	}
    }

    @FXML
    void cercaIscritti(ActionEvent event) {
    	String codins = this.cmbCorsi.getValue().toString();
    	if(codins.isEmpty()) {
    		txtRisultato.setText("Inserire il codice di un corso");
    		return;
    	}
    	List<Studente> risultato = new ArrayList<Studente>();
    	risultato = this.model.getIscrittiCorso(codins);
    	txtRisultato.clear();
    	for(Studente s: risultato) {
    		txtRisultato.appendText(String.format("%-10s %-20s %-20s %-20s\n",s.getMatricola(),s.getNome(),s.getCognome(),s.getCDS()));
    	}
    }

    @FXML
    void iscrivi(ActionEvent event) {
    	String matricola = this.txtMatricola.getText();
    	String nome = this.cmbCorsi.getValue();
    	int inputNum = 0;
    	try {
    		inputNum = Integer.parseInt(matricola);
    	} catch (NumberFormatException e) {
    		txtRisultato.setText("Inserted Value is not an integer value");
    		return;
		}
    	if(model.getIscritto(matricola) == null) {
    		txtRisultato.setText("Utente non esistente");
    		return;
    	}
    	if(this.model.verifica(matricola, nome) == true) {
    		txtRisultato.setText("Studente già iscritto al corso");
    		return;
    	}
    	if(this.model.inscriviStudenteACorso(inputNum, nome) == true) {
    		txtRisultato.setText("Studente iscritto al corso correttamente");
    		return;
    	}
    		
    	
    }
    
    @FXML
    void cerca(ActionEvent event) {
    	String matricola = this.txtMatricola.getText();
    	String nome = this.cmbCorsi.getValue();
    	int inputNum = 0;
    	try {
    		inputNum = Integer.parseInt(matricola);
    	} catch (NumberFormatException e) {
    		txtRisultato.setText("Inserted Value is not an integer value");
    		return;
		}
    	if(model.getIscritto(matricola) == null) {
    		txtRisultato.setText("Utente non esistente");
    		return;
    	}
    	if(model.getCorsi(matricola) == null) {
    		txtRisultato.setText("Studente non iscritto a nessun corso");
    		return;
    	}
    	if(model.getIscrittiCorso(nome) == null) {
    		txtRisultato.setText("Corso senza iscritti");
    		return;
    	}
    	if(this.model.verifica(matricola, nome) == true) {
    		txtRisultato.setText("Studente già iscritto al corso");
    		return;
    	}
    	txtRisultato.setText("Studente non iscritto");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btn != null : "fx:id=\"btn\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnOk != null : "fx:id=\"btnOk\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbCorsi.getItems().addAll(model.getTuttiICorsi());
    	this.cmbCorsi.getItems().add("");
    	this.txtRisultato.setFont(Font.font("Monospaced", 16));
    }
}