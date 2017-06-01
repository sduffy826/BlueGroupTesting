import java.util.*;

import com.ibm.swat.password.ReturnCode;
import com.ibm.swat.password.cwa2;

import java.util.Vector;

import javax.naming.InvalidNameException;
import javax.naming.ldap.*;

import com.ibm.bluepages.BluePages;
import com.ibm.bluepages.slaphapi.LDAPAttribute;
import com.ibm.bluepages.slaphapi.LDAPEntry;
import com.ibm.bluepages.slaphapi.SLAPHAPIResults;

import java.util.*;
import java.util.Map.Entry;

/**
 * This class was used to test getting the admin's or owner's of a particular
 * group, did it hear before moving the functionality into the UserGroups
 * class; you shouldn't need this now, but left as a future playground.
 * 
 * @author S. Duffy
 *
 */
public class TestBluePagesCall {
  public static void main(String[] args) {
    
    String emailAddr = "clobsrv@us.ibm.com";
    emailAddr = "ndaher@us.ibm.com";
    UserGroups ugObj = new UserGroups(false);    
    String dnForEmail = ugObj.getDNForEmail(emailAddr);

    System.setProperty(BluePages.DEBUG,"true");
      
    // Empty parms for this call
    String filter = "(owner=" + dnForEmail + ")";  // If want admin's make this (admin=
    String theArgs = "ou=metadata,ou=ibmgroups,o=ibm.com/";
    String[] attrList = new String[] { "cn" };  // Attribute we want, just CN
      
    // Tell it we're override the base search argument
    Map<String, Object> parms = new HashMap<String, Object>();
    parms.put(BluePages.SLAPHAPI_SEARCH_BASE, "base"); 
    
    // Make bluepages call
    SLAPHAPIResults results = BluePages.callSLAPHAPI(theArgs+filter,attrList,parms);
    // Make sure the method didn't fail unexpectedly.
    if (results.succeeded()) {               
      // Check to see whether SLAPHAPI returned any results.
      if (results.getSize() > 0) {
        LDAPEntry entry;      
        // Iterate over values
        for (Enumeration<LDAPEntry> e = results.getEntries(); e.hasMoreElements(); ) {
          entry = e.nextElement();        
          try {
            System.out.println("cnValue: " + entry.getAttribute("cn").getValue());
          }
          catch(NullPointerException nullPtrE) { /* Just to show how to catch null ptr */ }
          catch(Exception exc) { }                   
        }
      }
    }
  }  
}
