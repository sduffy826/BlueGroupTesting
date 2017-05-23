import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.ibm.bluegroup.spd.GroupMemberBean;
import com.ibm.bluegroup.spd.UserGroups;

/**
 * This is another test class gets the groups and the members of all the groups 
 * owned by the email address passed in; it's really for play/learning
 *  
 * @author sduffy
 * @since 2017-05-09
 */
public class TestGetGroupsOwnedByAndMembersForEmail {

  private static final boolean DEBUGIT = true;
  private static final boolean DEBUGCWA = false;
  
  /**
   * The mainline, should pass in the email address on invocation
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      System.err.println("Pass email address to get groups for");
      return;
    }

    // Create object, use flag to determine if tracing should be on
    UserGroups mUserGroups = new UserGroups(DEBUGCWA);

    // memberList has the key (groupName) and members of the list
    Map<String, List<String>> memberList = new HashMap<String,List<String>>();

    if (DEBUGIT) System.out.println("\n\nGroups User Is Owner\n=======================");
    Vector<String> groupsOwnerOf = mUserGroups.getGroupsUserIsOwnerOf(args[0]);

    if (DEBUGIT) System.out.println("Owner of " + Integer.toString(groupsOwnerOf.size()) + " groups");

    // Populate the map (memberList) with the key (groupName) and list of members
    for (String aGroup : groupsOwnerOf) {
      memberList.put(aGroup,mUserGroups.getGroupMembers(aGroup));
    }  

    // Have all the results; loop thru them; we create bean which will be used in a modified
    // version of this program
    for (Map.Entry<String, List<String>> entry : memberList.entrySet()) {
      if (entry.getValue() != null) {
        for (String memberEmail : entry.getValue()) {
          GroupMemberBean groupMemberBean = new GroupMemberBean();
          groupMemberBean.setGroupName(entry.getKey());
          groupMemberBean.setOwnerEmail(args[0]);
          groupMemberBean.setMemberEmail(memberEmail);
         
          if (DEBUGIT) System.out.println(groupMemberBean.toString());
        }
      }
    }        

    if (DEBUGIT) System.out.println("Done");
  }
}
