package it.prova.gestionestazionijpamaven.service.treno;

import java.util.List;

import it.prova.gestionestazionijpamaven.dao.treno.TrenoDAO;
import it.prova.gestionestazionijpamaven.model.Treno;

public interface TrenoService {

	public List<Treno> listAll() throws Exception;

	public Treno caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Treno trenoInstance) throws Exception;

	public void inserisciNuovo(Treno trenoInstance) throws Exception;

	public void rimuovi(Treno trenoInstance) throws Exception;
	
	public List<Integer> cercaNumeroAbitantiByTreno(Long id) throws Exception;

	public void setTrenoDAO(TrenoDAO trenoDAO);

}
