package it.gestione.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import it.gestione.configuration.HibernateSessionUtil;
import it.gestione.model.Articolo;



public class ArticoloRepositoryImpl implements ArticoloRepository {

	@Override
	public void registraArticolo(Articolo articolo) {
		
		Transaction transaction = null;

		try (Session session = HibernateSessionUtil.getSession().openSession()) {
			articolo.setDescrizione(articolo.getDescrizione());
			articolo.setPrezzoUnitario(articolo.getPrezzoUnitario());
			articolo.setQuantita(articolo.getQuantita());
			transaction = session.beginTransaction();
			session.persist(articolo);
			transaction.commit();
			System.out.println("L'articolo registrato è: " + articolo.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (transaction != null)
				transaction.rollback();
		}
	}

	@Override
	public List<Articolo> registraArticoli(List<Articolo> articoli) {
		
		Transaction transaction = null;
		
		try (Session session = HibernateSessionUtil.getSession().openSession()){
			transaction = session.beginTransaction();
			for(Articolo a: articoli) {
				session.persist(a);
				System.out.println("L'articolo registrato è: " + a.toString());
			}
			transaction.commit();
			return articoli;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if(transaction !=null)
				transaction.rollback();
		}
		return null;
	}

	@Override
	public List<Articolo> leggiArticoli() {
		try (Session session = HibernateSessionUtil.getSession().openSession()){
			String jpql= "SELECT a FROM Articolo a";
			Query<Articolo> query = session.createQuery(jpql,Articolo.class);
			return query.list();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

}
