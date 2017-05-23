import com.ibm.bluegroup.spd.GetGroupOwnerAndMembersByGroupName;
import com.ibm.bluegroup.spd.GetGroupsOwnedByEmail;
import com.ibm.bluegroup.spd.GroupMemberBean;
import com.ibm.bluegroup.spd.GroupOwnerBean;

import java.util.ArrayList;

/**
 * This is just a stub to test the GetGroupsOwnerdByEmail class
 *  
 * @author sduffy
 * @since 2017-05-23
 */
public class TestGetGroupsOwnedByEmail {
  private static boolean includeMembers = true;
  
  /**
   * The mainline, should pass in the email address on invocation
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Pass email address to get groups for");
      return;
    }
    // Instantiate object and get all the groups this email owns
    GetGroupsOwnedByEmail groupsByEmail = new GetGroupsOwnedByEmail();
    ArrayList<GroupOwnerBean> theList = groupsByEmail.getGroups(args[0]);
    
    // Loop through list and show owner bean info
    for (int i = 0; i < theList.size(); i++) {
      GroupOwnerBean gob = theList.get(i);
      System.out.println("GroupOwnerBean: " + gob.toString());
      
      // If want members of group also then we'll create the object, call method and pass
      // in the group we want, the result will be in memberList; which we'll then loop thru
      if (includeMembers) {
        ArrayList<GroupMemberBean> memberList = new GetGroupOwnerAndMembersByGroupName()
          .getMembers(gob.getGroupName());
        for (int j = 0; j < memberList.size(); j++) {
          System.out.println("  MemberBean: " + memberList.get(j).toString());  
        }
      }
    }  
  }
}