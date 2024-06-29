package it.gestione.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionUtil 
{
	//istanza statica di una Session di Hibernate (verrà utilizzata per il database)
	private static final SessionFactory SESSION = buildSessionFactory();
	
	//metodo per creare l'istanza statica di Session
	private static SessionFactory buildSessionFactory() {
		
		// registro di configurazione
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.config.xml").build();
		
		//impostare metadati della sessione
		MetadataSources sources = new MetadataSources(registry);
		Metadata metadata = sources.buildMetadata();
		// creazione e ritorno della sessione
		return metadata.buildSessionFactory();
	}
	//getter per ottenere la Session di Hibernate
	public static SessionFactory getSession() {
		return SESSION;
	}
}
