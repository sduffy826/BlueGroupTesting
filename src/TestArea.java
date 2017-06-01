import java.util.*;

import com.ibm.swat.password.ReturnCode;
import com.ibm.swat.password.cwa2;

import java.util.Vector;

import javax.naming.InvalidNameException;
import javax.naming.ldap.*;

import com.ibm.bluepages.slaphapi.LDAPEntry;

import java.util.*;
import java.util.Map.Entry;

public class TestArea {
  public static void main(String[] args) {

    System.setProperty(cwa2.DEBUG, "false");
    System.setProperty(cwa2.TRACE, "false");

    cwa2 CWA2 = new cwa2("bluepages.ibm.com","bluegroups.ibm.com");

    CWA2.setMemberFilterSize(20);
    CWA2.setUseThreaded(true);

    String groupName = "emea_bpso_ro";
    
    groupName = "PRISM-User-BCRS-WW";
    groupName = "RS_IW_P_USER_ASSET_MANAGEMENT";
    groupName = "rs_iw_d_developers";
    
    Vector<String> members = new Vector<String>();
    ReturnCode rc = null;

    System.out.println("-------------------- CALLING LISTMEMBERS --------------------");

    rc = CWA2.listMembers(groupName, members);
    System.out.println("RC= " + rc);

    System.out.println("---------------------- LISTING MEMBERS ----------------------");

    if (rc.getCode() == 0) {
      for (int i = 0, n = members.size(); i < n; i++) {
        String attr = members.elementAt(i);
        System.out.println(i + ". " + attr);
      }
    }
    else {
      System.err.println(rc);
    }
    
    
    Vector<String> theGroups = new Vector<String>();
    System.out.println("-------------------- CALLING LISTGROUPS --------------------");

    rc = CWA2.listGroups(groupName, 1, theGroups);
    System.out.println("RC= " + rc + " Vector size: " + theGroups.size());
    
    
    
    
  }
}
