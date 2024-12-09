package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	//S3 on n'imprime pas le ticket si le montant inséré est insuffisant
	void nImprimePasSiPasAssezArgent(){
		machine.insertMoney(PRICE -1);
		assertFalse(machine.printTicket(), "On a pu imprimer le ticket alors qu'il n'y avait pas assez d'argent");
	}

	@Test
	//S4 on imprime ticket si le montant inséré est suffisant
	void ImprimeSiAssezArgent(){
		machine.insertMoney(PRICE);
		assertTrue(machine.printTicket(), "On n'a pas pu imprimer le ticket alors qu'il y avait assez d'argent");
	}

	@Test
	//S5 Quand on imprime un ticket, la balance est décrémentée du prix du ticket
	void DecrementerBalance(){
		machine.insertMoney(PRICE);
		int ancien = machine.getBalance();
		machine.printTicket();
		assertEquals(ancien - PRICE, machine.getBalance(), "Il y a un soucis car la balance n'a pas été décrémentée");
	}

	@Test
	//S6 le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void MontantCollecte(){
		machine.insertMoney(PRICE);
		int ancienTOT = machine.getTotal();
		machine.printTicket();
		assertEquals(ancienTOT, machine.getTotal() - PRICE, "Le montant collecté n'a pas été mis à jour quand on imprime un ticket");
	}

	@Test
	//S7 refund() rend correctement la monnaie
	void RendBienMonnaie(){
		machine.insertMoney(PRICE);
		int rendu = machine.refund();
		assertEquals(rendu, machine.getBalance() , "Ne rend pas correctement la monnaie");
	}

	@Test
	//S8 refund() remet la balance à zéro
	void RemetBalanceZero(){
		machine.insertMoney(PRICE +10);
		machine.printTicket();
		machine.refund();
		assertEquals(machine.getBalance(), 0 , "Ne remet pas la balance à zero");
	}

	@Test
	//S9 on ne peut pas insérer un montant négatif
	void PasInsererMontantNegatif(){
		try {
			machine.insertMoney(-PRICE);
			fail("La machine a entrer un montant négatif");
		}
		catch (ExceptionInsertionNbNegatif e) {
		}
	}


	@Test
	//S10 on ne peut pas créer de machine qui délivre des tickets dont le prix est -
	void PasCreerMachine(){
		try{
			TicketMachine machine = new TicketMachine(-PRICE);
			fail("La machine a été crée avec prix négatif");
		}
		catch (IllegalArgumentException e) {}
	}
}
