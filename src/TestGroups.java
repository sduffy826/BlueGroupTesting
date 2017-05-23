import java.util.Vector;

import javax.naming.InvalidNameException;
import javax.naming.ldap.*;

import com.ibm.bluegroup.spd.LdapHelper;
import com.ibm.bluegroup.spd.UserGroups;
import com.ibm.bluepages.slaphapi.LDAPEntry;

import java.util.*;
import java.util.Map.Entry;

public class TestGroups {

  /**
   * This is just a mainline program to test some of the routines supporting bluepages/bluegroups
   * and ldap DN utilities
   */
  public static void main(String[] args) {
    boolean getGroupsIn = false;
    boolean getGroupsAdminOf = false;
    boolean getGroupsOwnerOf = false;
    boolean getGroupMembers = false;
    
    getGroupsIn = getGroupsAdminOf = getGroupsOwnerOf = getGroupMembers = true;
    if (args.length < 1) {
      System.err.println("Pass email address to get groups for");
      return;
    }

    // Create object we'll turn on debug info :)
    UserGroups ugObj = new UserGroups(false);
    
    // Get the distinguished name for the email address
    String userDn = ugObj.getDNForEmail(args[0]);
    
    // Get LDAPEntry and show how to extract one of the attributes
    LDAPEntry thePerson = ugObj.getPersonForEmail(args[0]);
    String personName = thePerson.getAttribute("callupName").getValue();   
    
    System.out.println("email: " + args[0] + " dn: " + userDn + " callupName: " + personName);
    
    // Show how to extract relative distinguished names (rdn) type/values from ldap string
    javax.naming.ldap.LdapName dn;
    try {
      dn = new javax.naming.ldap.LdapName(userDn);
      System.out.println(dn);      
      for(Rdn rdn : dn.getRdns()) {  // This shows values from right to left
        System.out.println("rdn type:" + rdn.getType() +": rdn value:" + rdn.getValue() + ":");
      }      
    } catch (InvalidNameException e) {
      e.printStackTrace();
    }
    
    // Show Ldap helper method that pulls the value for a given rdn type
    System.out.println("Calling helper");
    System.out.println(LdapHelper.getRdnValue(userDn, "UID")); 
    
    // This shows the ldap helper returning a map of type/values
    System.out.println("Calling helper for map");
    java.util.Map<String,String> userDnMap = LdapHelper.getMap(userDn);
    for (java.util.Map.Entry<String, String> entry : userDnMap.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
    
    // Prove map is case sensitive
    String[] keys2Check = { "uid", "UID" };
    for (String theKey : keys2Check) {
      if ( userDnMap.containsKey(theKey) ) { 
        System.out.println("Map has key: " + theKey); 
      }
      else { 
        System.out.println("Map does not have key: " + theKey);
      }
    }
    
    // Show how to get a list where each row is an rdn entry (type/value)
    System.out.println("Calling helper for list<entry>, this outputs in order");
    List<Entry<String,String>> rtnList = LdapHelper.getList(userDn);
    for (Entry<String,String> anEntry : rtnList) {
      System.out.println("key: " + anEntry.getKey() + " value: " + anEntry.getValue());      
    }
    
    // Show how to get the rdn values of the DN without the types 
    System.out.println("Rdn values with , betweend: " + LdapHelper.getRdnValues(userDn));    
    
    // Get all the groups a user is in
    if (getGroupsIn) {
      // Groups user is in
      System.out.println("\n\nGroups User Is In\n=======================");
    
      Vector<String> theGroups = ugObj.getGroupsUserIsIn(args[0]);
      for (String aGroup : theGroups) {
        System.out.println(aGroup);      
      }
    }
    
    if (getGroupsAdminOf) {
      System.out.println("\n\nGroups User Is Admin\n=======================");
      Vector<String> adminGroups = ugObj.getGroupsUserIsAdminOf(args[0]);
      for (String aGroup : adminGroups) {
        System.out.println(aGroup);      
      }  
    }
    
    if (getGroupsOwnerOf) {
      Map<String, List<String>> theList = new HashMap<String,List<String>>();
      System.out.println("\n\nGroups User Is Owner\n=======================");
      Vector<String> groupOwner = ugObj.getGroupsUserIsOwnerOf(args[0]);
      
      System.out.println("Owner of " + Integer.toString(groupOwner.size()) + " groups");
      
      // Populate the map with the groups and all the members.
      for (String aGroup : groupOwner) {
        if (getGroupMembers){
          theList.put(aGroup,ugObj.getGroupMembers(aGroup));
        }
        else {
          theList.put(aGroup,null);
        }
      }  
      
      for (Map.Entry<String, List<String>> entry : theList.entrySet()) {
        System.out.println("Group: " + entry.getKey());
        if (entry.getValue() != null) {
          for (String aValue : entry.getValue()) {
            System.out.println("  Member: " + aValue);
          }
        }
      }        
    }  
    
    System.out.println("Done");
  }

}
