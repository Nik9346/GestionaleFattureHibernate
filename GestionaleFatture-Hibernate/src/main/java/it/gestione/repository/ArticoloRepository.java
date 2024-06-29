package it.gestione.repository;

import java.util.List;

import it.gestione.model.Articolo;


public interface ArticoloRepository {
	
	public void registraArticolo(Articolo articolo);
	
	public List<Articolo> registraArticoli(List<Articolo> articoli);
	
	public List<Articolo> leggiArticoli();

}
