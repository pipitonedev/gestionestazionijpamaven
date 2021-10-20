package it.prova.gestionestazionijpamaven.service.stazione;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionestazionijpamaven.dao.EntityManagerUtil;
import it.prova.gestionestazionijpamaven.dao.stazione.StazioneDAO;
import it.prova.gestionestazionijpamaven.model.Stazione;
import it.prova.gestionestazionijpamaven.model.Treno;

public class StazioneServiceImpl implements StazioneService {

	private StazioneDAO stazioneDAO;

	@Override
	public void setStazioneDAO(StazioneDAO stazioneDAO) {
		this.stazioneDAO = stazioneDAO;

	}

	@Override
	public List<Stazione> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			stazioneDAO.setEntityManager(entityManager);

			return stazioneDAO.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Stazione caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			stazioneDAO.setEntityManager(entityManager);

			return stazioneDAO.get(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Stazione caricaSingoloElementoEagerTreno(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			stazioneDAO.setEntityManager(entityManager);

			return stazioneDAO.findByIdFetchingTreni(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Stazione stazioneInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			stazioneDAO.setEntityManager(entityManager);

			stazioneDAO.update(stazioneInstance);

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
	public void inserisciNuovo(Stazione stazioneInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			stazioneDAO.setEntityManager(entityManager);

			stazioneDAO.insert(stazioneInstance);

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
	public void rimuovi(Stazione stazioneInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			stazioneDAO.setEntityManager(entityManager);

			stazioneDAO.delete(stazioneInstance);

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
	public void aggiungiTreno(Stazione stazioneInstance, Treno trenoInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();

			stazioneDAO.setEntityManager(entityManager);

			stazioneInstance = entityManager.merge(stazioneInstance);

			trenoInstance = entityManager.merge(trenoInstance);

			stazioneInstance.getTreni().add(trenoInstance);

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
	public void creaECollegaStazioneETreno(Stazione articoloTransientInstance, Treno trenoTransientInstance)
			throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			entityManager.getTransaction().begin();
			stazioneDAO.setEntityManager(entityManager);

			articoloTransientInstance.getTreni().add(trenoTransientInstance);

			stazioneDAO.insert(articoloTransientInstance);
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
	public Stazione caricaSingoloElementoEager(Long stazioneId) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {

			// uso l'injection per il dao
			stazioneDAO.setEntityManager(entityManager);

			return stazioneDAO.caricaSingoloElementoEager(stazioneId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void dissociaTreno(Treno treno) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			stazioneDAO.setEntityManager(entityManager);

			treno = entityManager.merge(treno);
			List<Stazione> listaCategorie = stazioneDAO.getStazioniByTreno(treno.getId());

			for (Stazione stazione : listaCategorie) {
				stazione.removeFromTreno(treno);
			}

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
