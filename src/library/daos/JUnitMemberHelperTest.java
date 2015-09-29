package library.daos;

import org.junit.Test;

public class JUnitMemberHelperTest {

	MemberHelper helper = new MemberHelper();
	
	@Test
	public void testMakeMember() {
		helper.makeMember("Marcel", "Wiles", "0420203190", "mwiles03@csu.edu.au", 1);
	}

}
