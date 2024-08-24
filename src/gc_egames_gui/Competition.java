/*
File Name : Competition.java
Authors : Jeetendra Karki
Date: 21-Aug- 2014
Version: 1.0
Notes
 */
package gc_egames_gui;

/**
 *
 * @author jeetendrakarki
 */
public class Competition 
{
    //League of Legends Tafe Coomera, 21 August 2024, BioHazards, 2
    private String game;
    private String location;
    private String competitionDate;
    private String team;
    private int points;
    
    
    
    // Constructor method (parameterised)
    public Competition (String game, String location, String competitionDate, String team, int points)
    {
        this.game = game;
        this.location = location;
        this.competitionDate = competitionDate;
        this.team = team;
        this.points = points;
        
    }
    
    // Public get methods
    public String getGame()
    {
        return game;
        
    }
    public String getLocation()
    {
        return location;
        
    }
    public String getCompetitionDate()
    {
        return competitionDate;
    }
    public String getTeam()
    {
        return team;
    }
    public int getPoints()
    {
        return points;
    }
    
    
    // set methods( changes the instance variable)
    public void setGame (String game)
    {
        this.game = game;
        
    }
    public void setLocation (String location)
    {
        this.location = location;
    }
    public void setCompetitionDate(String competitionDate)
    {
        this.competitionDate = competitionDate;
    }
    public void setTeam(String team)
    {
        this.team = team;
    }
    public void setPoints(int points)
    {
        this.points = points;
        
    }
    
    
    
    // override toString() method
    // override the toString() method of the object class
    
    @Override
    public String toString()
    {
        // return csv string format for writing(saving) competition data
        // League of Legends, Tafe Coomera 21 Aug, BioHazard, 2
        
        return game + "," + location + "," + competitionDate + "," +
                team + "," + points;
    }
    
    
}
