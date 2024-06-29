package it.gestione.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import it.gestione.configuration.HibernateSessionUtil;
import it.gestione.model.Articolo;
import it.gestione.model.Cliente;
import it.gestione.model.Fattura;

public class FatturaRepositoryImpl implements FatturaRepository {

	private static ClienteRepository clienteRepository = new ClienteRepositoryImpl();
	private static ArticoloRepository articoloRepository = new ArticoloRepositoryImpl();
	
	//Funzione utilizzata per registrare una nuova fattura, creando prima il cliente, la lista degli articoli ed infine la fattura con i relativi collegamenti
	@Override
	public void registraFattura(Cliente cliente, List<Articolo> articoli, Fattura fattura) {

		clienteRepository.registraCliente(cliente);

		articoloRepository.registraArticoli(articoli);

		Transaction transaction = null;
		try (Session session = HibernateSessionUtil.getSession().openSession()) {
			List<Articolo> articoligestiti = new ArrayList<>();
			for (Articolo a : articoli) {
				Articolo articologestito = session.merge(a);
				articoligestiti.add(articologestito);
			}
			Cliente clienteGestito = session.merge(cliente);
			fattura.setArticoli(articoligestiti);
			fattura.setCliente(clienteGestito);
			fattura.setDataDiEmissione(fattura.getDataDiEmissione());
			fattura.setImponibile(fattura.getImponibile());
			fattura.setIva(fattura.getIva());
			fattura.setTotale(fattura.getTotale());
			transaction = session.beginTransaction();
			session.persist(fattura);
			transaction.commit();
			System.out.println("--------------------------------\nFattura Registrata con successo \n--------------------------------\n" + fattura.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (transaction != null)
				transaction.rollback();
		}
	}
	
	//Funzione utilizzata per chiedere al db tutta la lista delle fatture presenti
	@Override
	public List<Fattura> getFatture() {
		try (Session session = HibernateSessionUtil.getSession().openSession()){
			String jpql = "SELECT f FROM Fattura f";
			Query<Fattura> query = session.createQuery(jpql, Fattura.class);
			return query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
			}
	}

}
