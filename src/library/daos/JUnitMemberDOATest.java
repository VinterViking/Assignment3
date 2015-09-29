package library.daos;

import static org.junit.Assert.*;

import org.junit.Test;

public class JUnitMemberDOATest {

	MemberHelper helper = new MemberHelper();
	MemberDAO members = new MemberDAO(helper);

	@Test
	public void testAddMember() {
		members.addMember("Marcel", "Wiles", "0420203190", "mwiles03@csu.edu.au");
		members.addMember("Marc", "Wallace", "0420203190", "mwiles01@csu.edu.au");
		members.addMember("Lecram", "Welis", "0420203190", "mwiles02@csu.edu.au");
		members.addMember("Carmel", "Silew", "0420203190", "mwiles04@csu.edu.au");
		assertEquals(members.getMemberByID(1).getID(), 1);
	}

	@Test
	public void testGetMemberByID() {
		members.addMember("Marcel", "Wiles", "0420203190", "mwiles03@csu.edu.au");
		members.addMember("Marc", "Wallace", "0420203190", "mwiles01@csu.edu.au");
		members.addMember("Lecram", "Welis", "0420203190", "mwiles02@csu.edu.au");
		members.addMember("Carmel", "Silew", "0420203190", "mwiles04@csu.edu.au");
		assertEquals(members.getMemberByID(1).getID(), 1);
	}

	@Test
	public void testFindMembersByLastName() {
		members.addMember("Marcel", "Wiles", "0420203190", "mwiles03@csu.edu.au");
		members.addMember("Marc", "Wallace", "0420203190", "mwiles01@csu.edu.au");
		members.addMember("Lecram", "Welis", "0420203190", "mwiles02@csu.edu.au");
		members.addMember("Carmel", "Silew", "0420203190", "mwiles04@csu.edu.au");
		assertEquals(members.findMembersByLastName("Wiles").get(0).getID(), 1);
	}

	@Test
	public void testFindMembersByEmailAddress() {
		members.addMember("Marcel", "Wiles", "0420203190", "mwiles03@csu.edu.au");
		members.addMember("Marc", "Wallace", "0420203190", "mwiles01@csu.edu.au");
		members.addMember("Lecram", "Welis", "0420203190", "mwiles02@csu.edu.au");
		members.addMember("Carmel", "Silew", "0420203190", "mwiles04@csu.edu.au");
		assertEquals(members.findMembersByEmailAddress("mwiles04@csu.edu.au").get(0).getID(), 4);
	}

	@Test
	public void testFindMembersByNames() {
		members.addMember("Marcel", "Wiles", "0420203190", "mwiles03@csu.edu.au");
		members.addMember("Marc", "Wallace", "0420203190", "mwiles01@csu.edu.au");
		members.addMember("Lecram", "Welis", "0420203190", "mwiles02@csu.edu.au");
		members.addMember("Carmel", "Silew", "0420203190", "mwiles04@csu.edu.au");
		assertEquals(members.findMembersByNames("Marc", "Wallace").get(0).getID(), 2);
	}

}
