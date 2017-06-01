import java.util.*;

import com.ibm.swat.password.ReturnCode;
import com.ibm.swat.password.cwa2;

import java.util.Vector;

import javax.naming.InvalidNameException;
import javax.naming.ldap.*;

import com.ibm.bluepages.BluePages;
import com.ibm.bluepages.slaphapi.LDAPEntry;

import java.util.*;
import java.util.Map.Entry;

/**
 * Just another test program, use to test 
 * @author sduffy
 *
 */

public class TestExtractAdmins {
  public static void main(String[] args) {

    System.setProperty(cwa2.DEBUG, "false");
    System.setProperty(cwa2.TRACE, "false");

    cwa2 CWA2 = new cwa2("bluepages.ibm.com","bluegroups.ibm.com");

    CWA2.setMemberFilterSize(100);
    CWA2.setUseThreaded(true);

    String groupName = "emea_bpso_ro";
    String emailAddr = "clobsrv@us.ibm.com";
    
    UserGroups ugObj = new UserGroups(false);
    
    System.out.println("GroupOwner: " + ugObj.getGroupOwner(groupName));
        
    String dnForEmail = ugObj.getDNForEmail(emailAddr);
    
    // Check if email is admin of a group
    if (CWA2.isGroupAdmin(emailAddr,groupName)) {
      System.out.println(emailAddr + " is ADMIN of group: " + groupName);
    }       
    
    System.out.println(
      "--------------- email / dn--------------------------------------------");
    System.out.println("email:" + emailAddr + ": dn:" + dnForEmail + ":");

    String[] attrList = { "cn" }; //, "cn", "ou", "o","callupName","uid" };
    String[] arList;
    Vector<Object> rtnList = CWA2.listAllGroupsMemberOf(dnForEmail, attrList);     
    for (Object aObj : rtnList) {
      if (aObj instanceof List<?>) {
        for (Object bObj : (Vector<?>)aObj) {
          System.out.println("Value in array: " + bObj.toString());
        }        
      }
    }    
  }
}
