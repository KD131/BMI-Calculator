package business.services;

import business.entities.Sport;
import business.exceptions.UserException;
import business.persistence.Database;
import business.persistence.SportMapper;

import java.util.List;

public class SportFacade
{
    
    private SportMapper sportMapper;
    
    public SportFacade(Database database)
    {
        sportMapper = new SportMapper(database);
    }
    
    public List<Sport> getAllSports() throws UserException
    {
        return sportMapper.getAllSports();
    }
    
    public Sport getSportById(int id) throws UserException
    {
        return sportMapper.getSportById(id);
    }
    
    public int deleteSportById(int id) throws UserException
    {
        return sportMapper.deleteSportById(id);
    }
    
    public int updateSport(Sport sport) throws UserException
    {
        return sportMapper.updateSport(sport);
    }
    
    public int insertSport(Sport sport) throws UserException
    {
        return sportMapper.insertSport(sport);
    }
}
