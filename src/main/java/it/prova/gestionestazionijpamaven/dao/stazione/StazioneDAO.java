package it.prova.gestionestazionijpamaven.dao.stazione;

import java.util.List;

import it.prova.gestionestazionijpamaven.dao.IBaseDAO;
import it.prova.gestionestazionijpamaven.model.Stazione;

public interface StazioneDAO extends IBaseDAO<Stazione> {

	public Stazione findByIdFetchingTreni(Long id) throws Exception;

	public Stazione caricaSingoloElementoEager(Long stazioneId) throws Exception;
	
	public List<Stazione> getStazioniByTreno(Long idTreno) throws Exception;

}
