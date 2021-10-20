package it.prova.gestionestazionijpamaven.dao.citta;

import java.util.List;

import it.prova.gestionestazionijpamaven.dao.IBaseDAO;
import it.prova.gestionestazionijpamaven.model.Citta;
import it.prova.gestionestazionijpamaven.model.Treno;

public interface CittaDAO extends IBaseDAO<Citta> {

	public Citta findByIdFetchingAll(Long id) throws Exception;

	public List<Citta> findAllByTreno(Treno trenoInstance);

	public Citta findByIdFetchingStazione(Long id) throws Exception;

}
