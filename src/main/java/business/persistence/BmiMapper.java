package business.persistence;

import business.exceptions.UserException;

import java.sql.*;
import java.util.List;

public class BmiMapper
{
    private Database database;
    
    public BmiMapper(Database database)
    {
        this.database = database;
    }
    
    public void insertBmiEntry(
            double weight,
            double height,
            double bmi,
            String category,
            String gender,
            int sportId,
            List<Integer> hobbyList) throws UserException
    {
        try (Connection con = database.connect())
        {
            String sql = "INSERT INTO bmi.bmientries(weight, height, bmi, category, gender, sport_id) VALUES (?,?,?,?,?,?)";
    
            try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setDouble(1, weight);
                ps.setDouble(2, height);
                ps.setDouble(3, bmi);
                ps.setString(4, category);
                ps.setString(5, gender);
                ps.setInt(6, sportId);
                
                int res = ps.executeUpdate();
                ResultSet ids = ps.getGeneratedKeys();
                ids.next();
                int entryId = ids.getInt(1);
                
                for (int hobbyId : hobbyList)
                {
                    insertEntryHobbies(entryId, hobbyId);
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
}
