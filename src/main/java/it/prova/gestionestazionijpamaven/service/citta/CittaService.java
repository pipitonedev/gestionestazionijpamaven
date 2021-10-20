package it.prova.gestionestazionijpamaven.service.citta;

import java.util.List;
import it.prova.gestionestazionijpamaven.dao.citta.CittaDAO;
import it.prova.gestionestazionijpamaven.dao.stazione.StazioneDAO;
import it.prova.gestionestazionijpamaven.model.Citta;
import it.prova.gestionestazionijpamaven.model.Stazione;
import it.prova.gestionestazionijpamaven.model.Treno;

public interface CittaService {

	public List<Citta> listAll() throws Exception;

	public Citta caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Citta cittaInstance) throws Exception;

	public void inserisciNuovo(Citta cittaInstance) throws Exception;

	public void rimuovi(Citta cittaInstance) throws Exception;

	public List<Citta> trovaCittaDiUnTreno(Treno trenoInstance) throws Exception;
	
	public void aggiungiStazioneACitta(Citta cittaInstance, Stazione stazioneInstance) throws Exception;
	
	public void deleteEager(Long id) throws Exception;
	
	public Citta caricaSingoloElementoEagerStazione(Long id) throws Exception;
	
	public void scollegaStazione(Stazione stazioneInstance, Citta cittaInstance) throws Exception;
	
	// per injection
	public void setCittaDAO(CittaDAO cittaDAO);
	public void setStazioneDAO(StazioneDAO stazioneDAO);

}
