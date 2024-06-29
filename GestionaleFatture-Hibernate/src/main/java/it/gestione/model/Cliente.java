package it.gestione.model;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // server per farla diventare un'entità
@Table(name="clienti") //Specifica il nome della tabella di riferimento
public class Cliente {
	
	//Attributi di istanza del Cliente
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String nome;
	
	@Column
	private String cognome;
	
	@OneToMany(
			mappedBy = "cliente", // deve essere lo stesso nome della variabile proprietaria del link manytoOne in Fattura
			fetch = FetchType.EAGER,
			orphanRemoval = true)
	private List<Fattura> fatture = new ArrayList<>();
	
	//Getter and Setter
	public List<Fattura> getFatture() {
		return fatture;
	}
	public void setFatture(List<Fattura> fatture) {
		this.fatture = fatture;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	//ToString dell'oggetto Cliente
	@Override
	public String toString() {
		return "Cliente: " + nome + " "+  cognome;
	}
	
	
	
	
	
	

}
