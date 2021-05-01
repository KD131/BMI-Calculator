package business.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

public class BmiEntry
{
    private int entryId;
    private int weight;
    private int height;
    private double bmi;
    private String bmiString;
    private String category;
    private String gender;
    private Sport sport;
    private List<Integer> hobbyList;
    private User user;
    private Timestamp created;
    
    // TODO: decide if hobbyList and user should be in the constructor. There's a good case to be made that they shouldn't.
    //  But they could always be null when instantiated.
    //  But you could just as easily, perhaps more cleanly, just set it after the instantiation.
    public BmiEntry(int weight, int height, double bmi, String category, String gender, Sport sport, List<Integer> hobbyList, User user)
    {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = category;
        this.gender = gender;
        this.sport = sport;
        this.hobbyList = hobbyList;
        this.user = user;
        
        this.bmiString = String.format(Locale.ENGLISH,"%.2f",bmi);
    }
    
    public int getEntryId()
    {
        return entryId;
    }
    
    public void setEntryId(int entryId)
    {
        this.entryId = entryId;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public double getBmi()
    {
        return bmi;
    }
    
    public String getBmiString()
    {
        return bmiString;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public String getGender()
    {
        return gender;
    }
    
    public Sport getSport()
    {
        return sport;
    }
    
    public void setSport(Sport sport)
    {
        this.sport = sport;
    }
    
    public List<Integer> getHobbyList()
    {
        return hobbyList;
    }
    
    public void setHobbyList(List<Integer> hobbyList)
    {
        this.hobbyList = hobbyList;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
    }
    
    public Timestamp getCreated()
    {
        return created;
    }
    
    public void setCreated(Timestamp created)
    {
        this.created = created;
    }
}
