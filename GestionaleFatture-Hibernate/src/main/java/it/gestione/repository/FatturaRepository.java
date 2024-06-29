package it.gestione.repository;

import java.util.List;

import it.gestione.model.Articolo;
import it.gestione.model.Cliente;
import it.gestione.model.Fattura;

public interface FatturaRepository {
	
	public void registraFattura(Cliente cliente, List<Articolo> articoli, Fattura fattura);
	
	public List<Fattura> getFatture();

}
