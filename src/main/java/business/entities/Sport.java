package business.entities;

public class Sport
{
    private int id;
    private String name;
    
    public Sport(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
