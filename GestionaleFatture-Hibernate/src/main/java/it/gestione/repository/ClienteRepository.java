package it.gestione.repository;

import java.util.List;

import it.gestione.model.Cliente;

public interface ClienteRepository {
	
	public Cliente getClienteById(String nome);
	
	public List<Cliente> getClienti();
	
	public Cliente registraCliente(Cliente cliente);
	
}
