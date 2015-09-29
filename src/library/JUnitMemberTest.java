package library;

import static org.junit.Assert.*;

import org.junit.Test;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;

public class JUnitMemberTest {

	@Test (expected = IllegalArgumentException.class)
	public void testExceptionsMember() {
		Member member1 = new Member("", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		Member member2 = new Member("Marcel", "", "0420203190", "mwiles03@student.csu.edu.au", 15);
		Member member3 = new Member("Marcel", "Wiles", "", "mwiles03@student.csu.edu.au", 15);
		Member member4 = new Member("Marcel", "Wiles", "0420203190", "m", 15);
		Member member5 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", -10);
	}
	
	@Test
	public void testMember() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals(member1.firstName, "Marcel");
		assertEquals(member1.lastName, "Wiles");
		assertEquals(member1.contactPhone, "0420203190");
		assertEquals(member1.emailAddress, "mwiles03@student.csu.edu.au");
		assertEquals(member1.id, 15);
	}
	
	@Test
	public void testHasOverDueLoans() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		if(member1.hasOverDueLoans()){
			fail("Should not have any loans");
		}
	}

	@Test
	public void testHasReachedLoanLimit() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		if(member1.hasReachedLoanLimit()){
			fail("Should not have any loans");
		}
	}

	@Test
	public void testHasFinesPayable() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		if(member1.hasFinesPayable()){
			fail("Should not have any fines");
		}
	}

	@Test
	public void testHasReachedFineLimit() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		if(member1.hasReachedFineLimit()){
			fail("Should not have any fines");
		}
	}

	@Test
	public void testGetFineAmount() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals(0.0f, member1.getFineAmount(), 0.0);
	}

	@Test
	public void testAddFine() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		member1.addFine(5.0f);
		assertEquals(5.0f, member1.getFineAmount(), 0.0);
	}

	@Test
	public void testPayFine() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		member1.addFine(5.0f);
		assertEquals(5.0f, member1.getFineAmount(), 0.0);
		member1.payFine(2.0f);
		assertEquals(3.0f, member1.getFineAmount(), 0.0);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testAddLoan() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		ILoan loan = null;
		member1.addLoan(loan);
	}

	@Test
	public void testGetState() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals(member1.getState(), EMemberState.BORROWING_ALLOWED);
	}

	@Test
	public void testGetFirstName() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals("Marcel", member1.getFirstName());
	}

	@Test
	public void testGetLastName() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals("Wiles", member1.getLastName());
	}

	@Test
	public void testGetContactPhone() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals("0420203190", member1.getContactPhone());
	}

	@Test
	public void testGetEmailAddress() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals("mwiles03@student.csu.edu.au", member1.getEmailAddress());
	}

	@Test
	public void testGetID() {
		Member member1 = new Member("Marcel", "Wiles", "0420203190", "mwiles03@student.csu.edu.au", 15);
		assertEquals(15, member1.getID());
	}

}
