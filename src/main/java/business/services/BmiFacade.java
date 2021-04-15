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
    
    public int deleteSportById(int id) throws UserException
    {
        return bmiMapper.deleteSportById(id);
    }
}
