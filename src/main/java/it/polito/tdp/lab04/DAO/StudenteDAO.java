package it.polito.tdp.lab04.DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Studente;



public class StudenteDAO {
	public String[] getIscritto(String matricola){
		String sql = "SELECT s.nome, s.cognome "
				+ "FROM studente s "
				+ "WHERE s.matricola = ?";
		String[] risultato = new String[2];
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricola);
			ResultSet rs = st.executeQuery();
			rs.next();
			risultato[0] = rs.getString("nome");
			risultato[1] = rs.getString("cognome");
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
	public boolean verifica(String matricola, String nome) {
		String sql = "SELECT s.nome, s.cognome "
				+ "FROM studente s, corso c, iscrizione i "
				+ "WHERE s.matricola = i.matricola AND c.codins = i.codins AND s.matricola = ? AND c.nome = ?";
		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, matricola);
			st.setString(2, nome);
			ResultSet rs = st.executeQuery();
			if(!rs.next())
				return false;
			rs.close();
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
