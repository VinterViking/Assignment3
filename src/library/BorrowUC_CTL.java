package library;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;

public class BorrowUC_CTL implements ICardReaderListener, 
									 IScannerListener, 
									 IBorrowUIListener {
	
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	//private String state;
	private int scanCount = 0;
	private IBorrowUI ui;
	private EBorrowState state; 
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	
	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;
	
	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {
		
		scanCount = 0;
		this.display = display;
		this.ui = new BorrowUC_UI(this);
		state = EBorrowState.CREATED;
		this.reader = reader;
		this.printer = printer;
		this.scanner = scanner;
		this.bookDAO = bookDAO;
		this.loanDAO = loanDAO;
		this.memberDAO = memberDAO;
		this.reader.addListener(this);
		reader.setEnabled(true);
		this.setState(EBorrowState.CREATED);
	}
	
	public void initialise() {
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");
		this.setState(EBorrowState.INITIALIZED);
	}
	
	public void close() {
		display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberID) {
		if (memberDAO.getMemberByID(memberID) != null && memberDAO.getMemberByID(memberID).getState() == EMemberState.BORROWING_ALLOWED){
			borrower = memberDAO.getMemberByID(memberID);
			
			scanner.addListener(this);
			scanner.setEnabled(true);
			reader.setEnabled(false);
			String borrowerName = (new StringBuilder(String.valueOf(borrower.getFirstName()))).append(" ").append(borrower.getLastName()).toString();

			ui.displayMemberDetails(borrower.getID(), borrowerName, borrower.getContactPhone());
			ui.displayExistingLoan(buildLoanListDisplay(borrower.getLoans()));
			scanCount = borrower.getLoans().size();
			if (borrower.hasFinesPayable()){
				ui.displayOutstandingFineMessage(borrower.getFineAmount());
			}
			this.setState(EBorrowState.SCANNING_BOOKS);
		} else if (memberDAO.getMemberByID(memberID) != null && memberDAO.getMemberByID(memberID).getState() == EMemberState.BORROWING_DISALLOWED){
			reader.setEnabled(false);
			scanner.setEnabled(false);
			ui.displayMemberDetails(memberID, borrower.getFirstName(), borrower.getContactPhone());
			ui.displayExistingLoan(borrower.getLoans().toString());
			if(borrower.hasFinesPayable()){
				ui.displayOutstandingFineMessage(borrower.getFineAmount());
			}
			if (borrower.hasOverDueLoans()){
				ui.displayOverDueMessage();
			}
			if (borrower.hasReachedLoanLimit()){
				ui.displayAtLoanLimitMessage();
			}
			ui.displayErrorMessage("Borrowing Restricted");
			this.setState(EBorrowState.BORROWING_RESTRICTED);
		}
	}
	
	
	@Override
	public void bookScanned(int barcode) {
		IBook book = bookDAO.getBookByID(barcode); 
		if	(book == null){
			ui.displayErrorMessage("Book not found");
			reader.setEnabled(false);
			scanner.setEnabled(true);
			return;
		}
		if (book.getState() != EBookState.AVAILABLE){
			ui.displayErrorMessage("Book not availabol");
			reader.setEnabled(false);
			scanner.setEnabled(true);
			return;
		}
		if (bookList.contains(book)){
			ui.displayErrorMessage("Book already scanned");
			reader.setEnabled(false);
			scanner.setEnabled(true);
			return;
		}
		bookList.add(book);
		ILoan loan = loanDAO.createLoan(borrower, book); 
		loanList.add(loan);
		reader.setEnabled(false);
		scanner.setEnabled(true);
		ui.displayScannedBookDetails(book.toString());
		ui.displayPendingLoan(buildLoanListDisplay(loanList));
		scanCount++;
		this.setState(EBorrowState.SCANNING_BOOKS);
		if (scanCount == 5){
			this.setState(EBorrowState.CONFIRMING_LOANS);
			scanner.setEnabled(false);
		}
	}

	
	private void setState(EBorrowState state) {
		this.state = state;
	}

	@Override
	public void cancelled() {
		close();
	}
	
	@Override
	public void scansCompleted() {
		ui.displayConfirmingLoan(buildLoanListDisplay(loanList));
		reader.setEnabled(false);
		scanner.setEnabled(false);
		this.setState(EBorrowState.CONFIRMING_LOANS);
	}

	@Override
	public void loansConfirmed() {
		reader.setEnabled(false);
		scanner.setEnabled(false);
		this.setState(EBorrowState.COMPLETED);
	}

	@Override
	public void loansRejected() {
		loanList.clear();
		scanCount = loanList.size();
		reader.setEnabled(false);
		scanner.setEnabled(true);
		this.setState(EBorrowState.SCANNING_BOOKS);
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}

}
