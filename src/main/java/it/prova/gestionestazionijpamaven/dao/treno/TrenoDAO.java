package it.prova.gestionestazionijpamaven.dao.treno;

import java.util.List;

import it.prova.gestionestazionijpamaven.dao.IBaseDAO;
import it.prova.gestionestazionijpamaven.model.Treno;

public interface TrenoDAO extends IBaseDAO<Treno> {
	
	public List<Integer> findNumeroAbitantiByTreno(Long id) throws Exception;

}
