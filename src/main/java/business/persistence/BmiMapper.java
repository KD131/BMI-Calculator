package business.persistence;

import business.entities.BmiEntry;
import business.entities.Sport;
import business.entities.User;
import business.exceptions.UserException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BmiMapper
{
    private Database database;
    
    public BmiMapper(Database database)
    {
        this.database = database;
    }
    
    public void insertBmiEntry(BmiEntry bmiEntry) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "INSERT INTO bmi.bmientries(weight, height, bmi, category, gender, sport_id) VALUES (?,?,?,?,?,?)";
    
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setDouble(1, bmiEntry.getWeight());
                ps.setDouble(2, bmiEntry.getHeight());
                ps.setDouble(3, bmiEntry.getBmi());
                ps.setString(4, bmiEntry.getCategory());
                ps.setString(5, bmiEntry.getGender());
                ps.setInt(6, bmiEntry.getSport().getId());
                
                int res = ps.executeUpdate();
                ResultSet ids = ps.getGeneratedKeys();
                ids.next();
                int entryId = ids.getInt(1);
                
                for (int hobbyId : bmiEntry.getHobbyList())
                {
                    insertEntryHobbies(entryId, hobbyId);
                }
                
                // user logged in, i.e. entry has user
                if (bmiEntry.getUser() != null)
                {
                    insertUserId(entryId, bmiEntry.getUser().getId());
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
    
    public void insertEntryHobbies(int entryId, int hobbyId) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "INSERT INTO bmi.link_bmientries_hobbies (bmientry_id, hobby_id) VALUES (?, ?)";
    
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, entryId);
                ps.setInt(2, hobbyId);
                ps.executeUpdate();
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
    
    public void insertUserId(int entryId, int userId) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "UPDATE bmi.bmientries SET user_id = ? WHERE id = ?";
        
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, userId);
                ps.setInt(2, entryId);
                ps.executeUpdate();
            }
            catch (SQLException ex)
            {
                throw new UserException(ex.getMessage());
            }
        }
        catch (SQLException | UserException ex)
        {
            throw new UserException(ex.getMessage());
        }
    }
    
    public List<BmiEntry> getAllBmiEntries() throws UserException
    {
        try (Connection con = database.connect())
        {
            // TODO: narrow it down
            // You might be able to use a LEFT JOIN instead. I'm not sure. It might make it super messy, though.
            String sql = "SELECT * FROM bmi.bmientries";
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                List<BmiEntry> entries = new ArrayList<>();
                
                while (rs.next())
                {
                    // TODO: if refactoring the data types from double to int, change here as well
                    int id = rs.getInt("id");
                    double weight = rs.getDouble("weight");
                    double height = rs.getDouble("height");
                    double bmi = rs.getDouble("bmi");
                    String category = rs.getString("category");
                    String gender = rs.getString("gender");
                    int sportId = rs.getInt("sport_id");
                    int userId = rs.getInt("user_id");
                    Timestamp created = rs.getTimestamp("created");
                    
                    // it's debatable whether user should be in the constructor or always set later
                    // you could also wait with instantiating the entry until after you've fetched the user.
                    BmiEntry entry = new BmiEntry(weight, height, bmi, category, gender,null,null, null);
                    entry.setEntryId(id);
                    entry.setCreated(created);
    
                    Sport sport = getSportById(sportId);
                    entry.setSport(sport);
                    
                    // entry had no user logged in, i.e. user_id field in database was null.
                    
                    // could also do it for everyone, and if it throws an exception for user not found because id == 0
                    // then set it to null, or just move on because it's already null anyway.
                    if (userId != 0)
                    {
                        UserMapper userMapper = new UserMapper(database);
                        User user = userMapper.getUserById(userId);
                        entry.setUser(user);
                    }
                    
                    // TODO: add methods for getting Sport and Hobbies.
                    entries.add(entry);
                }
                return entries;
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
    
    // could also take int and be ByUserId()
    public List<BmiEntry> getBmiEntriesByUser(User user) throws UserException
    {
        try (Connection con = database.connect())
        {
            // TODO: Finish this. Consider using JOIN to get sport name.
            //  You can't use JOIN to get hobbies because there could be more than one per entry.
            //  You can't return an array, so there would be duplicate result-rows with only a hobby as difference.
            
            // TODO: narrow it down.
            String sql = "SELECT * FROM bmi.bmientries WHERE user_id = ?";
            
            List<BmiEntry> bmiEntryList = new ArrayList<>();
            try (PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, user.getId());
                ResultSet rs = ps.executeQuery();
                
                while (rs.next())
                {
                    int id = rs.getInt("id");
                    double weight = rs.getDouble("weight");
                    double height = rs.getDouble("height");
                    double bmi = rs.getDouble("bmi");
                    String category = rs.getString("category");
                    String gender = rs.getString("gender");
                    int sportId = rs.getInt("sport_id");
                    Timestamp created = rs.getTimestamp("created");
                    
                    Sport sport = getSportById(sportId);
                    
                    // TODO get List of Hobbies
                    
                    BmiEntry entry = new BmiEntry(weight, height, bmi, category, gender,sport,null, user);
                    entry.setEntryId(id);
                    entry.setCreated(created);
    
                    bmiEntryList.add(entry);
                }
                
                return bmiEntryList;
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
