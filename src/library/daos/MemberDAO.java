package library.daos;

import java.util.ArrayList;
import java.util.List;

import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IMember;

public class MemberDAO implements IMemberDAO{
	
	MemberHelper helper;
	List<IMember> members = new ArrayList<IMember>();
	
	public MemberDAO(MemberHelper helper){
		this.helper = helper;
	}
	
	@Override
	public IMember addMember(String firstName, String lastName, String contactPhone, String emailAddress) {
		IMember newMember = helper.makeMember(firstName, lastName, contactPhone, emailAddress, members.size() + 1);
		members.add(newMember);
		return newMember;
	}

	@Override
	public IMember getMemberByID(int id) {
		for(int i = 0; i < members.size(); i++){
			if(members.get(i).getID() == id){
				return members.get(i);
			}
		}
		return null;
	}

	@Override
	public List<IMember> listMembers() {
		return members;
	}

	@Override
	public List<IMember> findMembersByLastName(String lastName) {
		List<IMember> membersFound = new ArrayList<IMember>();

		for(int i = 0; i < members.size(); i++){
			if(members.get(i).getLastName() == lastName){
				membersFound.add(members.get(i));
			}
		}
		return membersFound;
	}

	@Override
	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		List<IMember> membersFound = new ArrayList<IMember>();

		for(int i = 0; i < members.size(); i++){
			if(members.get(i).getEmailAddress() == emailAddress){
				membersFound.add(members.get(i));
			}
		}
		return membersFound;
	}

	@Override
	public List<IMember> findMembersByNames(String firstName, String lastName) {
		List<IMember> membersFound = new ArrayList<IMember>();

		for(int i = 0; i < members.size(); i++){
			if(members.get(i).getFirstName() == firstName && members.get(i).getLastName() == lastName){
				membersFound.add(members.get(i));
			}
		}
		return membersFound;
	}

}
