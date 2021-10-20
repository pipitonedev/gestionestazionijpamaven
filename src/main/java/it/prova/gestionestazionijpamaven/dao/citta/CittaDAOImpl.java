package it.prova.gestionestazionijpamaven.dao.citta;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestionestazionijpamaven.model.Citta;
import it.prova.gestionestazionijpamaven.model.Treno;

public class CittaDAOImpl implements CittaDAO {

	private EntityManager entityManager;

	@Override
	public List<Citta> list() throws Exception {
		return entityManager.createQuery("from Citta", Citta.class).getResultList();
	}

	@Override
	public Citta get(Long id) throws Exception {
		return entityManager.find(Citta.class, id);
	}

	@Override
	public void update(Citta input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);

	}

	@Override
	public void insert(Citta input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);

	}

	@Override
	public void delete(Citta input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public List<Citta> findAllByTreno(Treno trenoInstance) {
		TypedQuery<Citta> query = entityManager.createQuery(
				"select c from Citta c join c.stazioni s join s.treni t where t.codice like ?1", Citta.class);
		return query.setParameter(1, trenoInstance.getCodice()).getResultList();
	}

	@Override
	public Citta findByIdFetchingAll(Long id) throws Exception {
		TypedQuery<Citta> query = entityManager.createQuery(
				"select c FROM Citta c left join fetch c.stazioni s left join fetch s.treni t where c.id = :idCitta",
				Citta.class);
		query.setParameter("idCitta", id);
		return query.getSingleResult();
	}

	@Override
	public Citta findByIdFetchingStazione(Long id) throws Exception {
		TypedQuery<Citta> query = entityManager
				.createQuery("select c FROM Citta c left join fetch c.stazioni s where c.id = :idCitta", Citta.class);
		query.setParameter("idCitta", id);
		return query.getResultList().stream().findFirst().orElse(null);

}
}
