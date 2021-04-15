package business.services;

import business.entities.BmiEntry;
import business.entities.Sport;
import business.entities.User;
import business.exceptions.UserException;
import business.persistence.BmiMapper;
import business.persistence.Database;

import java.util.List;

public class BmiFacade
{
    private BmiMapper bmiMapper;
    
    public BmiFacade(Database database)
    {
        this.bmiMapper = new BmiMapper(database);
    }
    
    public void insertBmiEntry(
            BmiEntry bmiEntry) throws UserException
    {
        bmiMapper.insertBmiEntry(bmiEntry);
    }
    
    public List<BmiEntry> getAllBmiEntries() throws UserException
    {
        return bmiMapper.getAllBmiEntries();
    }
    
    public List<BmiEntry> getBmiEntriesByUser(User user) throws UserException
    {
        return bmiMapper.getBmiEntriesByUser(user);
    }
    
    public List<Sport> getAllSports() throws UserException
    {
        return bmiMapper.getAllSports();
    }
    
    public Sport getSportById(int id) throws UserException
    {
        return bmiMapper.getSportById(id);
    }
    
    public int deleteSportById(int id) throws UserException
    {
        return bmiMapper.deleteSportById(id);
    }
    
    public int updateSport(Sport sport) throws UserException
    {
        return bmiMapper.updateSport(sport);
    }
    
    public int insertSport(Sport sport) throws UserException
    {
        return bmiMapper.insertSport(sport);
    }
}
