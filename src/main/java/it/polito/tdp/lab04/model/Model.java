package it.polito.tdp.lab04.model;

import java.util.List;


import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	private CorsoDAO corsoDAO;
	private StudenteDAO studenteDAO;
	
	public Model() {
		this.corsoDAO = new CorsoDAO();
		this.studenteDAO = new StudenteDAO();
	}
	public String[] getTuttiICorsi(){
		return this.corsoDAO.getTuttiICorsi();
	}
	public String[] getIscritto(String matricola) {
		return this.studenteDAO.getIscritto(matricola);
	}
	public List<Studente> getIscrittiCorso(String codins){
		return this.corsoDAO.getStudentiIscrittiAlCorso(codins);
	}
	public List<Corso> getCorsi(String matricola){
		return this.corsoDAO.getCorsi(matricola);
	}
	public boolean verifica(String matricola, String nome) {
		return this.studenteDAO.verifica(matricola, nome);
	}
	public boolean inscriviStudenteACorso(int matricola, String nome) {
		return this.corsoDAO.inscriviStudenteACorso(matricola, this.corsoDAO.getCorso(nome));
	}
}
