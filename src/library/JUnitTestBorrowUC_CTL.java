package library;

import static org.junit.Assert.*;

import org.junit.Test;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;

public class JUnitTestBorrowUC_CTL {
	
	//incomplete
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	//private String state;
	private EBorrowState state; 
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	
	BorrowUC_CTL ctl = new BorrowUC_CTL(reader, scanner, printer, display, 
			 bookDAO, loanDAO, memberDAO);
	
	@Test
	public void testCardSwiped() {
		ctl.initialise();
		ctl.cardSwiped(1);
	}

	@Test
	public void testBookScanned() {
		fail("Not yet implemented");
	}

	@Test
	public void testScansCompleted() {
		fail("Not yet implemented");
	}

}
