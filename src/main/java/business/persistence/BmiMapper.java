package business.persistence;

import business.entities.BmiEntry;
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
                    BmiEntry entry = new BmiEntry(weight, height, bmi, category, gender,null,null);
                    entry.setEntryId(id);
                    entry.setCreated(created);
                    
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
    
    public List<BmiEntry> getBmiEntriesByUser(User user) throws UserException
    {
        try (Connection con = database.connect())
        {
            // TODO: Finish this. Consider using JOIN to get sport name.
            //  You can't use JOIN to get hobbies because there could be more than one per entry.
            //  You can't return an array, so there would be duplicate result-rows with only a hobby as difference.
            
            // TODO: narrow it down.
            String sql = "SELECT * FROM bmi.bmientries WHERE id = ?";
            
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
                    
                    BmiEntry entry = new BmiEntry(weight, height, bmi, category, gender,null,null);
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
}
