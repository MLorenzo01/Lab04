package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public String[] getTuttiICorsi() {

		final String sql = "SELECT c.nome FROM corso c";

		List<String> corsi = new LinkedList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String nome = rs.getString("nome");

				corsi.add(nome);
			}

			conn.close();
			String[] cor = new String[corsi.size()];
			int i = 0;
			for(String s: corsi) {
				cor[i] = s;
				i++;
			}
 			return cor;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public String getCorso(String nome) {
		String sql = "SELECT c.codins "
				+ "FROM corso c "
				+ "WHERE c.nome = ? ";
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nome);
			ResultSet rs = st.executeQuery();
			rs.next();
			String risultato = rs.getString("codins");
			rs.close();
			st.close();
			conn.close();
			return risultato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore connessione al DB");
			e.printStackTrace();
			return null;
		}
	}
	public List<Corso> getCorsi(String matricola) {
		String sql = "SELECT c.codins, c.nome, c.crediti, c.pd\n"
				+ "FROM studente s, corso c, iscrizione i\n"
				+ "WHERE s.matricola = i.matricola AND c.codins = i.codins AND s.matricola = ?";
		List<Corso> risultato = new ArrayList<Corso>();
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricola);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				risultato.add(new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd")));
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore connessione al DB");
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(String nome) {
		String sql = "SELECT s.matricola, s.nome, s.cognome, s.CDS "
				+ "FROM studente s, corso c, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND c.codins = i.codins AND c.nome = ?";
		List<Studente> risultato = new ArrayList<Studente>();
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nome);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				risultato.add(new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS")));
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore connessione al DB");
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(int matricola, String codins) {
		String sql = "INSERT INTO iscritticorsi.iscrizione (matricola, codins) "
				+ "VALUES (?,?);";
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, codins);
			st.executeUpdate();
			st.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Errore connessione al DB");
			e.printStackTrace();
			return false;
		}
	}

}
