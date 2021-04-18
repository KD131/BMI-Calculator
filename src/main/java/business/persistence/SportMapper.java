package business.persistence;

import business.entities.Sport;
import business.exceptions.UserException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SportMapper
{
    private Database database;
    
    public SportMapper(Database database)
    {
        this.database = database;
    }
    
    // TODO: It would be ever so slightly more efficient as a SortedTreeMap. Makes it easier to get Sport by id.
    public List<Sport> getAllSports() throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "SELECT * FROM bmi.sports";
            
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                List<Sport> sportList = new ArrayList<>();
                
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    
                    Sport sport = new Sport(id, name);
                    sportList.add(sport);
                }
                return sportList;
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }
    
    public Sport getSportById(int id) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "SELECT `name` FROM bmi.sports WHERE id = ?";
            
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                
                if (rs.next())
                {
                    String name = rs.getString("name");
                    
                    Sport sport = new Sport(id, name);
                    return sport;
                }
                else
                {
                    throw new UserException("Could not find sport. ID = " + id);
                }
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }
    
    public int deleteSportById(int id) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "DELETE FROM bmi.sports " +
                    "WHERE id = ? AND id NOT IN (" +
                    "SELECT sport_id FROM bmi.bmientries)";
            
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, id);
                int affectedRows = ps.executeUpdate();
                return affectedRows;
                
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }
    
    public int updateSport(Sport sport) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "UPDATE bmi.sports SET `name` = ? WHERE id = ?";
            
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, sport.getName());
                ps.setInt(2, sport.getId());
                int affectedRows = ps.executeUpdate();
                return affectedRows;
                
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }
    
    public int insertSport(Sport sport) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "INSERT INTO bmi.sports (name) VALUES (?)";
            
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setString(1, sport.getName());
                int affectedRows = ps.executeUpdate();
                ResultSet ids = ps.getGeneratedKeys();
                ids.next();
                int id = ids.getInt(1);
                sport.setId(id);
                return affectedRows;
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }
}
