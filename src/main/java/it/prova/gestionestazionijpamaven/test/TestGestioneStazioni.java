package it.prova.gestionestazionijpamaven.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import it.prova.gestionestazionijpamaven.dao.EntityManagerUtil;
import it.prova.gestionestazionijpamaven.model.Citta;
import it.prova.gestionestazionijpamaven.model.Stazione;
import it.prova.gestionestazionijpamaven.model.Treno;
import it.prova.gestionestazionijpamaven.service.MyServiceFactory;
import it.prova.gestionestazionijpamaven.service.citta.CittaService;
import it.prova.gestionestazionijpamaven.service.stazione.StazioneService;
import it.prova.gestionestazionijpamaven.service.treno.TrenoService;

public class TestGestioneStazioni {

	public static void main(String[] args) {

		CittaService cittaServiceInstance = MyServiceFactory.getCittaServiceInstance();
		StazioneService stazioneServiceInstance = MyServiceFactory.getStazioneServiceInstance();
		TrenoService trenoServiceInstance = MyServiceFactory.getTrenoServiceInstance();

		try {

			System.out.println("Nella tabella Citta ci sono " + cittaServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"Nella tabella Stazione ci sono " + stazioneServiceInstance.listAll().size() + " elementi.");
			System.out.println("Nella tabella treno ci sono " + trenoServiceInstance.listAll().size() + " elementi.");

			testInserimentoCitta(cittaServiceInstance);

			testInserimentoStazione(cittaServiceInstance, stazioneServiceInstance);
			
			testInserimentoTreno(trenoServiceInstance);

			testCollegamentoCitta(cittaServiceInstance, stazioneServiceInstance);

			testCollegamentoTreno(cittaServiceInstance, stazioneServiceInstance, trenoServiceInstance);

			testRimuoviCitta(cittaServiceInstance, stazioneServiceInstance, trenoServiceInstance);

			testNumeroAbitantiByTreno(cittaServiceInstance, stazioneServiceInstance, trenoServiceInstance);

			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("Nella tabella Citta ci sono " + cittaServiceInstance.listAll().size() + " elementi.");
			System.out.println(
					"Nella tabella Stazione ci sono " + stazioneServiceInstance.listAll().size() + " elementi.");
			System.out.println("Nella tabella treno ci sono " + trenoServiceInstance.listAll().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}

	}

	// INSERISCI NUOVA CITTA

	private static void testInserimentoCitta(CittaService cittaServiceInstance) throws Exception {

		System.out.println("-------------testInserimentoCitta: Inizio------------");

		Citta nuovaCitta = new Citta("Marsala", 80000);
		cittaServiceInstance.inserisciNuovo(nuovaCitta);

		if (nuovaCitta.getId() == null)
			throw new RuntimeException("testInserimentoCitta fallito: impossibile inserire la citta! ");

		System.out.println(".......testInserimentoCitta fine: TUTTO OKAY!.............");
	}

	// INSERISCI STAZIONE

	private static void testInserimentoStazione(CittaService cittaServiceInstance,
			StazioneService stazioneServiceInstance) throws Exception {

		System.out.println("---------------testInserimentoStazione inizio-----------");

		long nowTimeMilliseconds = new Date().getTime();

		Citta cittaInstance = new Citta("Milano" + nowTimeMilliseconds, 80);
		cittaServiceInstance.inserisciNuovo(cittaInstance);

		Stazione stazioneInstance = new Stazione("Lambrate" + nowTimeMilliseconds, "Via lambrate", cittaInstance);
		stazioneServiceInstance.inserisciNuovo(stazioneInstance);

		if (stazioneInstance.getId() == null)
			throw new RuntimeException("testInserimentoStazione fallito: impossibile aggiugere la stazione! ");

		System.out.println("------------------testInserimentoStazione concluso: TUTTO OKAY!-----------");
	}

	// INSERISCI NUOVO TRENO

	private static void testInserimentoTreno(TrenoService trenoServiceInstance) throws Exception {

		System.out.println(".......testInserisciTreno inizio.............");

		long nowTimeMilliseconds = new Date().getTime();

		Treno trenoInstance = new Treno("FrecciaRossa" + nowTimeMilliseconds, "AB6754");
		trenoServiceInstance.inserisciNuovo(trenoInstance);
		if (trenoInstance.getId() == null)
			throw new RuntimeException("testInserimentoTreno fallito: impossibile inserire il treno! ");

		System.out.println("---------------testInserisciTreno concluso: TUTTO OKAY!---------------");
	}

	// COLLEGA CITTA A STAZIONE

	private static void testCollegamentoCitta(CittaService cittaServiceInstance,
			StazioneService stazioneServiceInstance) throws Exception {

		System.out.println("----------------testACollegamentoCitta inizio------------");

		long nowTimeMilliseconds = new Date().getTime();

		Citta cittaInstance = new Citta("Palermo" + nowTimeMilliseconds, 100);
		cittaServiceInstance.inserisciNuovo(cittaInstance);
		Long cittaId = cittaInstance.getId();

		Stazione stazioneInstance = new Stazione("Centrale" + nowTimeMilliseconds, "Via centrale", cittaInstance);
		stazioneServiceInstance.inserisciNuovo(stazioneInstance);

		Stazione stazioneInstance2 = new Stazione("Porta Venezia" + nowTimeMilliseconds, "Via venezia", cittaInstance);
		stazioneServiceInstance.inserisciNuovo(stazioneInstance2);

		if (cittaServiceInstance.caricaSingoloElementoEagerStazione(cittaId).getStazioni().size() != 2)
			throw new RuntimeException("testCollegamentoCitta fallito: impossibile collegare citta! ");

		System.out.println("------------testCollegamentoStazioniACitta concluso: TUTTO OKAY!----------");
	}

	// COLLEGAMENTO 2 TRENI A STAZIONE

	private static void testCollegamentoTreno(CittaService cittaServiceInstance,
			StazioneService stazioneServiceInstance, TrenoService trenoServiceInstance) throws Exception {

		System.out.println("----------testCollegamentoTreno: inizio------------------");

		Citta nuovaCitta = new Citta("Milano", 1350000);
		cittaServiceInstance.inserisciNuovo(nuovaCitta);

		if (nuovaCitta.getId() == null)
			throw new RuntimeException("testCollegamentoTreno fallito: citta non inserita");

		Stazione nuovaStazione = new Stazione("Lambrate", "MILA675");
		nuovaStazione.setCitta(nuovaCitta);
		stazioneServiceInstance.inserisciNuovo(nuovaStazione);

		if (nuovaStazione.getId() == null)
			throw new RuntimeException("testCollegamentoTreno fallito: impossibile inserire la stazione! ");

		Treno nuovoTreno = new Treno("AX850", "regionale");
		trenoServiceInstance.inserisciNuovo(nuovoTreno);
		stazioneServiceInstance.aggiungiTreno(nuovaStazione, nuovoTreno);
		Treno nuovoTreno2 = new Treno("8HGT5", "Frecciarosssa");
		trenoServiceInstance.inserisciNuovo(nuovoTreno2);
		stazioneServiceInstance.aggiungiTreno(nuovaStazione, nuovoTreno2);

		Stazione stazioneReload = stazioneServiceInstance.caricaSingoloElementoEagerTreno(nuovaStazione.getId());
		if (stazioneReload.getTreni().isEmpty())
			throw new RuntimeException("testCollegamentoTreno fallito: treni non collegati ");

		System.out.println("-------testCollegamentoTreno Passato: TUTTO OKAY!----------");

	}

	// RIMUOVI CITTA'

	private static void testRimuoviCitta(CittaService cittaServiceInstance, StazioneService stazioneServiceInstance,
			TrenoService trenoServiceInstance) throws Exception {
		System.out.println(".......testRimozioneCitta inizio.............");

		long nowTimeMilliseconds = new Date().getTime();

		Citta cittaInstance = new Citta("Napoli" + nowTimeMilliseconds, 30);
		cittaServiceInstance.inserisciNuovo(cittaInstance);
		Long cittaTest = cittaInstance.getId();

		Stazione stazioneInstance = new Stazione("Pompei" + nowTimeMilliseconds, "Via centrale", cittaInstance);
		stazioneServiceInstance.inserisciNuovo(stazioneInstance);
		Long stazioneIdTest = stazioneInstance.getId();

		Treno trenoInstance1 = new Treno("FrecciaRossa" + nowTimeMilliseconds, "1540");
		trenoServiceInstance.inserisciNuovo(trenoInstance1);
		Treno trenoInstance2 = new Treno("Italo" + nowTimeMilliseconds, "1003");
		trenoServiceInstance.inserisciNuovo(trenoInstance2);

		stazioneServiceInstance.aggiungiTreno(stazioneInstance, trenoInstance1);
		stazioneServiceInstance.aggiungiTreno(stazioneInstance, trenoInstance2);

		if (stazioneServiceInstance.caricaSingoloElementoEagerTreno(stazioneIdTest).getTreni().size() != 2) {
			throw new RuntimeException("test ASSOCIAZIONE TRENI A STAZIONE fallito ");
		}

		cittaServiceInstance.deleteEager(cittaTest);

		if (cittaServiceInstance.caricaSingoloElemento(cittaTest) != null) {
			throw new RuntimeException("testRimuoviOrdine fallito: cancellazione ordine fallita");
		}

		System.out.println("..........testRimozioneCitta PASSED........");

	}

	// DAMMI LA LISTA DEL NUMERO DI ABITANTI RAGGIUNTI DA UN DETERMINATO TRENO

	private static void testNumeroAbitantiByTreno(CittaService cittaServiceInstance,
			StazioneService stazioneServiceInstance, TrenoService trenoServiceInstance) throws Exception {

		System.out.println("--------------testNumeroAbitantiByTreno inizio---------------");

		long nowTimeMilliseconds = new Date().getTime();

		// CREAZIONE CITTA 1

		Citta cittaPalermo = new Citta("Napoli" + nowTimeMilliseconds, 30);
		cittaServiceInstance.inserisciNuovo(cittaPalermo);
		Long cittaTestPalermo = cittaPalermo.getId();

		Stazione stazionePalermo = new Stazione("Aereoporto" + nowTimeMilliseconds, "Via centrale", cittaPalermo);
		stazioneServiceInstance.inserisciNuovo(stazionePalermo);
		Long stazioneIdPalermo = stazionePalermo.getId();

		Treno trenoPalermo = new Treno("FrecciaRossa" + nowTimeMilliseconds, "1540Q");
		trenoServiceInstance.inserisciNuovo(trenoPalermo);

		stazioneServiceInstance.aggiungiTreno(stazionePalermo, trenoPalermo);

		// CREAZIONE CITTA 2

		Citta cittaRoma = new Citta("Roma" + nowTimeMilliseconds, 100);
		cittaServiceInstance.inserisciNuovo(cittaRoma);
		Long cittaTestRoma = cittaRoma.getId();

		Stazione stazioneRoma = new Stazione("Termini" + nowTimeMilliseconds, "Via Termini", cittaRoma);
		stazioneServiceInstance.inserisciNuovo(stazioneRoma);
		Long stazioneIdRoma = stazioneRoma.getId();

		Treno trenoRoma = new Treno("Intercity" + nowTimeMilliseconds, "1540Q");
		trenoServiceInstance.inserisciNuovo(trenoRoma);

		stazioneServiceInstance.aggiungiTreno(stazioneRoma, trenoRoma);

		// CREAZIONE CITTA 3

		Citta cittaMilano = new Citta("Milano" + nowTimeMilliseconds, 50);
		cittaServiceInstance.inserisciNuovo(cittaMilano);
		Long cittaTestMilano = cittaMilano.getId();

		Stazione stazioneMilano = new Stazione("Lambrate" + nowTimeMilliseconds, "Via lambrate", cittaMilano);
		stazioneServiceInstance.inserisciNuovo(stazioneMilano);
		Long stazioneIdMilano = stazioneMilano.getId();

		Treno trenoMilano = new Treno("FrecciaBianca" + nowTimeMilliseconds, "1540Q");
		trenoServiceInstance.inserisciNuovo(trenoMilano);

		stazioneServiceInstance.aggiungiTreno(stazioneMilano, trenoMilano);

		// CREAZIONE CITTA 4

		Citta cittaPisa = new Citta("Pisa" + nowTimeMilliseconds, 60);
		cittaServiceInstance.inserisciNuovo(cittaPisa);
		Long cittaTestPisa = cittaPisa.getId();

		Stazione stazionePisa = new Stazione("Centrale" + nowTimeMilliseconds, "Via Centro", cittaPisa);
		stazioneServiceInstance.inserisciNuovo(stazionePisa);
		Long stazioneIdTestPisa = stazionePisa.getId();

		Treno trenoPisa = new Treno("FrecciaBianca" + nowTimeMilliseconds, "1540Q");
		trenoServiceInstance.inserisciNuovo(trenoPisa);
		Long trenoPisaId = trenoPisa.getId();

		stazioneServiceInstance.aggiungiTreno(stazionePisa, trenoPisa);
		stazioneServiceInstance.aggiungiTreno(stazioneMilano, trenoPisa);
		stazioneServiceInstance.aggiungiTreno(stazioneRoma, trenoPisa);
		stazioneServiceInstance.aggiungiTreno(stazionePalermo, trenoPisa);

		if (trenoServiceInstance.cercaNumeroAbitantiByTreno(trenoPisaId).size() == 0) {
			throw new RuntimeException("TEST NUMERO ABITANTI FALLITO ");
		}
		System.out.println("-------------TEST ABITANTI CONCLUSO: TUTTO OK"
				+ trenoServiceInstance.cercaNumeroAbitantiByTreno(cittaTestPisa));

	}
}
