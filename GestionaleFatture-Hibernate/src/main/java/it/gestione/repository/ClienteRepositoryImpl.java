package it.gestione.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import it.gestione.configuration.HibernateSessionUtil;
import it.gestione.model.Cliente;



public class ClienteRepositoryImpl implements ClienteRepository {
	
	
	//Funzione utilizzata per ottenere dal database i dati di un cliente passando una Stringa con il nome	
	@Override
	public Cliente getClienteById(String nome) {
		try (Session session = HibernateSessionUtil.getSession().openSession()){
			String jpqlString = "Select c FROM Cliente c WHERE c.nome = :nome";
			Query<Cliente> query = session.createQuery(jpqlString, Cliente.class)
					.setParameter("nome", nome).setMaxResults(1);
			return query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return null;
		}
	}
	//Funzione utilizzata per ottenere dal db tutti i clienti registrati
	@Override
	public List<Cliente> getClienti(){
		try (Session session = HibernateSessionUtil.getSession().openSession()){
			String jpql= "SELECT c FROM Cliente c";
			Query<Cliente> query = session.createQuery(jpql, Cliente.class);
			return query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	//Funzione utilizzata per registrare un cliente dopo aver costruito un oggetto utilizzando la Classe Cliente
	@Override
	public Cliente registraCliente(Cliente cliente) {
		
		Transaction transaction = null;
		
		try (Session session= HibernateSessionUtil.getSession().openSession()){
			cliente.setNome(cliente.getNome());
			cliente.setCognome(cliente.getCognome());
			transaction = session.beginTransaction();
			session.persist(cliente);
			transaction.commit();
			System.out.println("Il Cliente registrato è: "+cliente.toString());
			return cliente;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if(transaction != null)
				transaction.rollback();
		}
		return null;
	}
}
