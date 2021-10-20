package it.prova.gestionestazionijpamaven.service.citta;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionestazionijpamaven.dao.EntityManagerUtil;
import it.prova.gestionestazionijpamaven.dao.citta.CittaDAO;
import it.prova.gestionestazionijpamaven.dao.stazione.StazioneDAO;
import it.prova.gestionestazionijpamaven.model.Citta;
import it.prova.gestionestazionijpamaven.model.Stazione;
import it.prova.gestionestazionijpamaven.model.Treno;

public class CittaServiceImpl implements CittaService {
	private CittaDAO cittaDAO;
	private StazioneDAO stazioneDAO;

	@Override
	public List<Citta> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			cittaDAO.setEntityManager(entityManager);

			return cittaDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Citta caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			cittaDAO.setEntityManager(entityManager);

			return cittaDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Citta cittaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			cittaDAO.setEntityManager(entityManager);

			cittaDAO.update(cittaInstance);

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
	public void inserisciNuovo(Citta cittaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			cittaDAO.setEntityManager(entityManager);

			cittaDAO.insert(cittaInstance);

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
	public void rimuovi(Citta cittaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			cittaDAO.setEntityManager(entityManager);

			cittaDAO.delete(cittaInstance);

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
	public void setCittaDAO(CittaDAO cittaDAO) {
		this.cittaDAO = cittaDAO;
	}

	public void setStazioneDAO(StazioneDAO stazioneDAO) {
		this.stazioneDAO = stazioneDAO;
	}

	@Override
	public List<Citta> trovaCittaDiUnTreno(Treno trenoInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			cittaDAO.setEntityManager(entityManager);

			return cittaDAO.findAllByTreno(trenoInstance);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiungiStazioneACitta(Citta cittaInstance, Stazione stazioneInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			cittaDAO.setEntityManager(entityManager);

			stazioneInstance = entityManager.merge(stazioneInstance);
			cittaInstance = entityManager.merge(cittaInstance);

			cittaInstance.getStazioni().add(stazioneInstance);
			stazioneInstance.setCitta(cittaInstance);

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
	public void deleteEager(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			cittaDAO.setEntityManager(entityManager);
			stazioneDAO.setEntityManager(entityManager);
			Citta cittaInstance = cittaDAO.findByIdFetchingAll(id);

			for (Stazione stazioneItem : cittaInstance.getStazioni()) {
				stazioneItem.removeFromTreno(stazioneItem.getTreni());
				stazioneDAO.delete(stazioneItem);
			}

			cittaDAO.delete(cittaInstance);

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
	public Citta caricaSingoloElementoEagerStazione(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			cittaDAO.setEntityManager(entityManager);

			return cittaDAO.findByIdFetchingStazione(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void scollegaStazione(Stazione stazioneInstance, Citta cittaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			cittaDAO.setEntityManager(entityManager);

			stazioneInstance = entityManager.merge(stazioneInstance);

			cittaInstance.getStazioni().remove(stazioneInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}
}
