package library;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Member implements IMember{
	
	String firstName;
	String lastName;
	String contactPhone;
	String emailAddress;
	int id;
	float fines = 0.0f;
	List<ILoan> loans = new ArrayList<ILoan>();
	EMemberState state = EMemberState.BORROWING_ALLOWED;
	
	public Member (String firstName, String lastName, String contactPhone,
		String emailAddress, int id) throws IllegalArgumentException {
	
		if (firstName == null || firstName.length() == 0){
			throw new IllegalArgumentException("First name is empty");
		} else {
			this.firstName = firstName;
		}
		
		if (lastName == null || lastName.length() == 0){
			throw new IllegalArgumentException("Last name is empty");
		} else {
			this.lastName = lastName;
		}
		
		if (contactPhone == null || contactPhone.length() == 0){
			throw new IllegalArgumentException("Contact phone number is empty");
		} else {
			this.contactPhone = contactPhone;
		}
		
		if (emailAddress == null || emailAddress.length() == 0){
			throw new IllegalArgumentException("Email address is empty");
		} else {
			this.emailAddress = emailAddress;
		}
		if (id <= 0){
			throw new IllegalArgumentException("Id number is equal to or less than zero");
		} else {
			this.id = id;
		}
		
	}

	@Override
	public boolean hasOverDueLoans() {
		for(int i = 0; i < this.getLoans().size(); i++){
			if(this.getLoans().get(i).isOverDue()){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasReachedLoanLimit() {
		if(this.getLoans().size() == Member.LOAN_LIMIT){
			return true;
		}
		return false;
	}

	@Override
	public boolean hasFinesPayable() {
		if(this.getFineAmount() > 0){
			return true;
		}
		return false;
	}

	@Override
	public boolean hasReachedFineLimit() {
		if(this.getFineAmount() >= Member.FINE_LIMIT){
			return true;
		}
		return false;
	}

	@Override
	public float getFineAmount() {
		return fines;
	}

	@Override
	public void addFine(float fine) throws IllegalArgumentException{
		if(fine < 0){
			throw new IllegalArgumentException("Fine is not a legal value");
		} else {
		fines += fine;
		}
	}

	@Override
	public void payFine(float payment) {
		if(payment < 0){
			throw new IllegalArgumentException("Payment is not a legal value");
		} else if (payment > this.getFineAmount()){
			throw new IllegalArgumentException("Payment is exceeds fines owing");
		} else {
		fines -= payment;
		}
	}

	@Override
	public void addLoan(ILoan loan) throws IllegalArgumentException{
		if(loan == null){
			throw new IllegalArgumentException("Loan is null");
		} else if(this.state == EMemberState.BORROWING_DISALLOWED){
			throw new IllegalArgumentException("Member is not allowed to loan anymore books");
		} else {
			loans.add(loan);
		}
	}

	@Override
	public List<ILoan> getLoans() {
		return this.loans;
	}

	@Override
	public void removeLoan(ILoan loan) throws IllegalArgumentException{
		if(loan == null){
			throw new IllegalArgumentException("Loan is null");
		} else if(!this.loans.contains(loan)){
			throw new IllegalArgumentException("Loan doesn't exist in member's list of loans");
		} else {
			this.loans.remove(loan);
		}
	}

	@Override
	public EMemberState getState() {
		return this.state;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String getContactPhone() {
		return this.contactPhone;
	}

	@Override
	public String getEmailAddress() {
		return this.emailAddress;
	}

	@Override
	public int getID() {
		return this.id;
	}
	
}
