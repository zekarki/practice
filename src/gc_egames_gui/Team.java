/*
File Name : Team.java
Authors : Jeetendra Karki
Date: 21-Aug- 2014
Version: 1.0
Notes:
 */
package gc_egames_gui;


public class Team 
{
    // BioHazards, zheng Lee, 0413456789, zhenglee99@gmail.com
    // Private data fields are initialised for the team instance
    private String teamName;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    
    
    // Constructors method with parameters
    public Team (String teamName, String contactName, String contactPhone, String contactEmail)
    {
        this.teamName = teamName;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }
    
    // get methods
    
    public String getTeamName()
    {
        return teamName;
    }
    public String getContactName()
    {
     return contactName;   
    }
    public String getContactPhone()
    {
        return contactPhone;
    }
    public String getContactEmail()
    {
        return contactEmail;
    }
    
    
    // set methods
     public void getTeamName(String teamName)
     {
         this.teamName = teamName;
       
     }
     public void getContactName(String contactName)
     {
         this.contactName = contactName;
         
     }
     public void getContactPhone(String contactPhone)
     {
         this.contactPhone = contactPhone;
         
     }
     public void getContactEmail(String contactEmail)
     {
         this.contactEmail = contactEmail;
         
     }
            
    
    // override toString() method
    // override the toString() method of the object class
    
    @Override
    public String toString()
    {
     return teamName + ","   + contactName + "," + contactPhone + "," + contactEmail;
     
    }
    
}
