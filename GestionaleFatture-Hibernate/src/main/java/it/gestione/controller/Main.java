package it.gestione.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import it.gestione.model.Articolo;
import it.gestione.model.Cliente;
import it.gestione.model.Fattura;
import it.gestione.repository.ArticoloRepository;
import it.gestione.repository.ArticoloRepositoryImpl;
import it.gestione.repository.ClienteRepository;
import it.gestione.repository.ClienteRepositoryImpl;
import it.gestione.repository.FatturaRepository;
import it.gestione.repository.FatturaRepositoryImpl;

public class Main {

	private static ClienteRepository clienteRepository = new ClienteRepositoryImpl();
	private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	private static ArticoloRepository articoloRepository = new ArticoloRepositoryImpl();
	private static FatturaRepository fatturaRepository = new FatturaRepositoryImpl();

	public static void main(String[] args) throws IOException {

		controller();

	}

	static void controller() throws IOException {
		System.out.println("Cosa vuoi fare?");
		System.out.println("N - Nuova Fattura " + "\nL - Registro Fatture " + "\nRA - Registra Articolo "
				+ "\nA - Elenco Articoli " + "\nC - Elenco Clienti " + "\nE - Esci");
		String scelta = bufferedReader.readLine();
		switch (scelta.toUpperCase()) {
		case "N": {
			System.out.println("Hai deciso di registrare una nuova fattura");
			registraFattura();
			break;
		}
		case "C": {
			clienteRepository.getClienti().forEach(c -> System.out.println(c.toString()));
			controller();
			break;
		}
		case "L": {
			fatturaRepository.getFatture().forEach(f -> System.out.println(f.toString() + "\n\n"));
			break;
		}
		case "RA": {
			articoloRepository.registraArticolo(registraArticolo());
			break;
		}case "A":{
			System.out.println("***************LISTA DEGLI ARTICOLI***************");
			articoloRepository.leggiArticoli().forEach(a->System.out.println(a.toString()));
			break;
		}default:
			System.out.println("Scelta non consentita, inserisci una scelta valida");
			;
		}

	}
	private static Cliente registraCliente() {
		try {
			Cliente cliente = new Cliente();

			System.out.println("Inserisci il nome del cliente");
			String nomeCliente = bufferedReader.readLine();
			cliente.setNome(nomeCliente);

			System.out.println("Inserisci il cognome del cliente");
			String cognomeCliente = bufferedReader.readLine();
			cliente.setCognome(cognomeCliente);

			return cliente;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private static Articolo registraArticolo() {

		try {
			Articolo articolo = new Articolo();

			System.out.println("Inserisci la descrizione dell'articolo");
			String descrizioneString = bufferedReader.readLine();
			articolo.setDescrizione(descrizioneString);

			System.out.println("Inserisci il prezzo della singola unità");
			String prezzoUnitario = bufferedReader.readLine();
			prezzoUnitario = prezzoUnitario.replace(",", ".");
			float prezzoUni = Float.parseFloat(prezzoUnitario);
			articolo.setPrezzoUnitario(prezzoUni);

			System.out.println("Inserisci la quantità del prodotto");
			int quantita = Integer.parseInt(bufferedReader.readLine());
			articolo.setQuantita(quantita);

			return articolo;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	private static List<Articolo> registraArticoli(){
		System.out.println("Per Uscire dalla registrazione dell'articolo premere Q, altrimenti premi N");

		try {
			String sceltaString = bufferedReader.readLine();
			List<Articolo> articoli = new ArrayList<>();
			while (sceltaString.equalsIgnoreCase("N")) {
				Articolo articolo = registraArticolo();
				articoli.add(articolo);

				System.out.println("Vuoi inserire un altro prodotto? premi N altrimenti altro per uscire");
				sceltaString = bufferedReader.readLine();
			}
			return articoli;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private static void registraFattura() {

		try {
			Fattura fattura = new Fattura();
			System.out.println("CREA UNA NUOVA FATTURA \nInserisci la data in formato aaaa-mm-gg");
			String dataInput = bufferedReader.readLine();

			// Funzione utilizzata per la verifica dell'input Data inserita dall'utente.
			if (dataInput.matches("^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parseDate;

				try {
					parseDate = dateFormat.parse(dataInput);
					java.sql.Date sqlDate = new java.sql.Date(parseDate.getTime());
					fattura.setDataDiEmissione(sqlDate);
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				}

				System.out.println("Inserisci l'aliquota IVA");
				int iva = Integer.parseInt(bufferedReader.readLine());
				fattura.setIva(iva);
				
				fattura.setCliente(registraCliente());
				fattura.setArticoli(registraArticoli());
				
				fatturaRepository.registraFattura(fattura.getCliente(), fattura.getArticoli(), fattura);
				}
			else {
				System.out.println("La data inserita non è corretta");
				registraFattura();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
