import com.ibm.swat.password.cwa2;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.ibm.bluepages.BluePages;
import com.ibm.bluepages.slaphapi.LDAPAttribute;
import com.ibm.bluepages.slaphapi.LDAPEntry;
import com.ibm.bluepages.slaphapi.SLAPHAPIResults;
import com.ibm.swat.password.ReturnCode;
import com.ibm.swat.password.cwa2;

public class TestGroupPlayground {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    // TODO Auto-generated method stub
    boolean debugIt = false;    
    String group2Test = "RS_IW_D_Users";
    
    if (debugIt) {
      System.setProperty(cwa2.DEBUG, "true");
      System.setProperty(cwa2.TRACE, "true");   
    }
      
    cwa2 cwa2Obj = new cwa2("bluepages.ibm.com","bluegroups.ibm.com");
    
    cwa2Obj.setMemberFilterSize(20);   
    cwa2Obj.setUseThreaded(true);
    
    // Owner
    System.out.println("Group: " + group2Test);
    System.out.println(cwa2Obj.getOwner(group2Test));
    
    // If need to connect
    // ReturnCode connectRC = cwa2Obj.authenticate("media3@us.ibm.com","<putPwHere>");    
    
    // List admins
    //Vector<String> vectList = new Vector<String>();
    /*
    Vector vectList = new Vector();
    ReturnCode rc = cwa2Obj.listAdmins(group2Test, 0, vectList, "mail");
    System.out.println(vectList.size());
    */
    
    // List Members
    // Vector<String> memList = new Vector<String>();
    Vector<String> memList = new Vector<String>();
    ReturnCode rc = cwa2Obj.listMembers(group2Test, memList, "mail"); //"callupName");
    System.out.println(memList.size());
    for (String aValue: memList) {
      System.out.println(aValue);      
    }
    
    

    //  return getGroupOwner(_group,cwa2Obj);
  }
}
