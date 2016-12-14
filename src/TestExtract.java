import java.util.*;

import com.ibm.swat.password.ReturnCode;
import com.ibm.swat.password.cwa2;

import java.util.Vector;

import javax.naming.InvalidNameException;
import javax.naming.ldap.*;

import com.ibm.bluepages.slaphapi.LDAPEntry;

import java.util.*;
import java.util.Map.Entry;

/** Test program - yep another one :)
 * 
 * @author S. Duffy
 *
 */

public class TestExtract {
  public static void main(String[] args) {

    System.setProperty(cwa2.DEBUG, "false");
    System.setProperty(cwa2.TRACE, "false");

    cwa2 CWA2 = new cwa2("bluepages.ibm.com","bluegroups.ibm.com");

    CWA2.setMemberFilterSize(20);
    CWA2.setUseThreaded(true);

    String groupName = "emea_bpso_ro";
    //groupName = "RS_IW_P_USER_ASSET_MANAGEMENT";

    Vector<String> members = new Vector<String>();
    ReturnCode rc = null;

    System.out.println(
      "-------------------- CALLING LISTMEMBERS --------------------");

    rc = CWA2.listMembers(groupName, members);
    System.out.println("RC= " + rc);

    System.out.println(
      "---------------------- LISTING MEMBERS ----------------------");

    if (rc.getCode() == 0) {
      for (int i = 0, n = members.size(); i < n; i++) {
        String attr = members.elementAt(i);
        System.out.println(i + ". " + attr);
      }
    }
    else {
      System.err.println(rc);
    }
    
    System.out.println(
      "---------------------- LISTING ADMINS ----------------------");
    Vector<String> list = new Vector<String>();
    
    String dummy = null;
    
    ReturnCode theRc = CWA2.listAdmins(groupName,3,list,"mail");
    if (theRc.getCode() == 0) {
      for (int i = 0, n = list.size(); i < n; i++) {
        String attr = list.elementAt(i);
        dummy = attr;
        System.out.println(i + ". " + attr);
      }
    }
    else {
      System.err.println(theRc);
    }
    
    System.out.println(
      "---------------------- isGroupAdmin ----------------------");
    System.out.println("Checking:" + dummy + ": against:" + groupName + ":");
    if (dummy != null) {
      if (CWA2.isGroupAdmin(dummy,groupName)) {
        System.out.println("is admin");
      }
      else {
        System.out.println("is not admin: ");
      }
    }
    
    UserGroups ugObj = new UserGroups(false);
    
    System.out.println(
      "---------------------- getGroupAdmins for group ----------------------");
    System.out.println("calling getGroupAdmins with:" + groupName + ":");
    Vector<String> grpAdmins = ugObj.getGroupAdmins(groupName);
    if (grpAdmins != null) {
      for (String aId : grpAdmins) {
        System.out.println("an admin: " + aId);
      }
    }
    else { System.out.println("No group admins"); }
    
    System.out.println(
      "---------------------- getGroupsUserIsAdminOf for emailAddr ----------------------");
    System.out.println("Checking: clobsrv@us.ibm.com (an id that should work)");
    Vector<String> userIsAdminOf = ugObj.getGroupsUserIsAdminOf("clobsrv@us.ibm.com");
    if (userIsAdminOf != null) {
      for (String aGrp : userIsAdminOf) {
        System.out.println("Admin of: " + aGrp);
      }
    }
    else { System.out.println("Not admin of any group"); }
  }
}

