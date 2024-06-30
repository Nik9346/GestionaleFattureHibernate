package it.gestione.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.Table;

@Entity //serve per farla diventare un'entità
@Table(name="articoli") // Specifica il nome della tabella di riferimento
public class Articolo {
	
	//Attributi di istanza di classe Articolo
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column 
	private String descrizione;
	
	@Column
	private float prezzoUnitario;
	
	@Column
	private int quantita;
	
	
	// collegamento alla tabella fatture molti a molti che passa per una jointable articoli_fatture	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "articoli_fatture",
	joinColumns = @JoinColumn(name="ID_Articolo",referencedColumnName = "id"),
	inverseJoinColumns =@JoinColumn(name="ID_Fattura",referencedColumnName = "id"))
	private List<Fattura> fatture = new ArrayList<>();
	
	//Getter and Setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public float getPrezzoUnitario() {
		return prezzoUnitario;
	}
	public void setPrezzoUnitario(float prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	
	public List<Fattura> getFatture() {
		return fatture;
	}
	public void setFatture(List<Fattura> fatture) {
		this.fatture = fatture;
	}
	
	//ToString dell'oggetto
	@Override
	public String toString() {
		return "Articolo: " + descrizione + " Prezzo: " + prezzoUnitario + "€ " + " Quantità: N°" + quantita;
	}
	
	
	

}
