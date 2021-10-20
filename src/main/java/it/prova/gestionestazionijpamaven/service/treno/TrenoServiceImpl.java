package it.prova.gestionestazionijpamaven.service.treno;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionestazionijpamaven.dao.EntityManagerUtil;
import it.prova.gestionestazionijpamaven.dao.treno.TrenoDAO;
import it.prova.gestionestazionijpamaven.model.Treno;

public class TrenoServiceImpl implements TrenoService {

	private TrenoDAO trenoDAO;

	@Override
	public void setTrenoDAO(TrenoDAO trenoDAO) {
		this.trenoDAO = trenoDAO;

	}

	@Override
	public List<Treno> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			trenoDAO.setEntityManager(entityManager);
			return trenoDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Treno caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			trenoDAO.setEntityManager(entityManager);
			return trenoDAO.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Treno trenoInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			trenoDAO.setEntityManager(entityManager);
			trenoDAO.update(trenoInstance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void inserisciNuovo(Treno trenoInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			trenoDAO.setEntityManager(entityManager);
			trenoDAO.insert(trenoInstance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public void rimuovi(Treno trenoInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();
			trenoDAO.setEntityManager(entityManager);
			trenoDAO.delete(trenoInstance);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}

	}

	@Override
	public List<Integer> cercaNumeroAbitantiByTreno(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			trenoDAO.setEntityManager(entityManager);

			return trenoDAO.findNumeroAbitantiByTreno(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
