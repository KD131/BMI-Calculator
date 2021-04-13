package business.services;

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
            double weight,
            double height,
            double bmi,
            String category,
            String gender,
            int sportId,
            List<Integer> hobbyList) throws UserException
    {
        bmiMapper.insertBmiEntry(weight, height, bmi, category, gender, sportId, hobbyList);
    }
}
