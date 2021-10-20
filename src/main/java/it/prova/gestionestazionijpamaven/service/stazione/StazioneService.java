package it.prova.gestionestazionijpamaven.service.stazione;

import java.util.List;

import it.prova.gestionestazionijpamaven.dao.stazione.StazioneDAO;
import it.prova.gestionestazionijpamaven.model.Stazione;
import it.prova.gestionestazionijpamaven.model.Treno;

public interface StazioneService {

	public List<Stazione> listAll() throws Exception;

	public Stazione caricaSingoloElemento(Long id) throws Exception;

	public Stazione caricaSingoloElementoEagerTreno(Long id) throws Exception;

	public void aggiorna(Stazione stazioneInstance) throws Exception;

	public void inserisciNuovo(Stazione stazioneInstance) throws Exception;

	public void rimuovi(Stazione stazioneInstance) throws Exception;

	public void aggiungiTreno(Stazione stazioneInstance, Treno trenoInstance) throws Exception;

	public void creaECollegaStazioneETreno(Stazione articoloTransientInstance, Treno trenoTransientInstance)
			throws Exception;

	public Stazione caricaSingoloElementoEager(Long stazioneId) throws Exception;
	
	public void dissociaTreno(Treno treno) throws Exception;

	public void setStazioneDAO(StazioneDAO stazioneDAO);

}
