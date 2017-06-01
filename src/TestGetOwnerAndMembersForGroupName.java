import java.util.ArrayList;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.List;


/**
 * This is another test class gets the owner and members for the group name
 * past in.
 *  
 * @author sduffy
 * @since 2017-05-09
 */
public class TestGetOwnerAndMembersForGroupName {

  private static final boolean DEBUGIT = true;
  private static final boolean DEBUGCWA = false;
  
  /**
   * The mainline, should pass in the email address on invocation
   */
  public static void main(String[] args) {
    boolean isFirst;
    if (args.length < 1) {
      System.err.println("Pass the group name in");
      return;
    }
     
    System.out.println("Group name: " + args[0]);
    // Get group owner and members 
    GetGroupOwnerAndMembersByGroupName grpNameDetail = new GetGroupOwnerAndMembersByGroupName();
      
    // Output owner and members
    isFirst = true;
    ArrayList<GroupMemberBean> groupMembers = grpNameDetail.getMembers(args[0]);
    for (GroupMemberBean groupMemberBean : groupMembers) {
      if (isFirst) System.out.println("  Owner:" + groupMemberBean.getOwnerEmail());
      System.out.println("    " + groupMemberBean.getMemberEmail());
      isFirst = false;
    }
        if (DEBUGIT) System.out.println("Done");
  }
}
