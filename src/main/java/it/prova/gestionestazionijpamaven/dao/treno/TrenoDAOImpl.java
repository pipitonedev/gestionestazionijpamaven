package it.prova.gestionestazionijpamaven.dao.treno;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestionestazionijpamaven.model.Treno;

public class TrenoDAOImpl implements TrenoDAO {

	private EntityManager entityManager;

	@Override
	public List<Treno> list() throws Exception {
		return entityManager.createQuery("from Treno", Treno.class).getResultList();
	}

	@Override
	public Treno get(Long id) throws Exception {
		return entityManager.find(Treno.class, id);
	}

	@Override
	public void update(Treno input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);

	}

	@Override
	public void insert(Treno input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);

	}

	@Override
	public void delete(Treno input) throws Exception {
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
	public List<Integer> findNumeroAbitantiByTreno(Long id) throws Exception {
		TypedQuery<Integer> query = entityManager.createQuery(
				"select c.numeroAbitanti FROM Citta c left join c.stazioni s left join s.treni t where t.id = :idTreno",
				Integer.class);
		query.setParameter("idTreno", id);
		return query.getResultList();
	}

}
