package web.commands;

import business.entities.BmiEntry;
import business.entities.Sport;
import business.entities.User;
import business.exceptions.UserException;
import business.services.BmiFacade;
import business.services.BmiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalcBMICommand extends CommandUnprotectedPage
{
    private BmiFacade bmiFacade;
    
    public CalcBMICommand(String pageToShow)
    {
        super(pageToShow);
        this.bmiFacade = new BmiFacade(database);
    }
    
    @Override
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws UserException
    {
        
        int weight;
        int height;
        double bmi;
        String category;
        String gender = request.getParameter("gender");
        int sportId = Integer.parseInt(request.getParameter("primary-sport"));
        // TODO: error handle the parseInt();
        String[] hobbies = request.getParameterValues("hobby");
        List<Integer> hobbyList = new ArrayList<>();
        if (hobbies != null)
        {
            for (String hobby : hobbies)
            {
                hobbyList.add(Integer.parseInt(hobby));
            }
        }
        
        try
        {
            weight = Integer.parseInt(request.getParameter("weight"));
            height = Integer.parseInt(request.getParameter("height")); // convert to meter
        }
        catch (NumberFormatException e)
        {
            // also solved using HTML built-in validation, type="number"
            request.setAttribute("error","Input must be a whole number.");
            return "index";
//            throw new UserException("Input must be a number.");
        }
        
        bmi = BmiUtil.calcBMI(weight,height);
        category = BmiUtil.getCategory(bmi);
    
        
        // get User from session scope and cast it to User.
        // if not logged in, should return null.
        User user = (User)request.getSession().getAttribute("user");
        
        // you can do same for hobbies once that's also put in a List and imported from database
        Sport sport = null;
        for (Sport s : (List<Sport>)request.getServletContext().getAttribute("sportList"))
        {
            if (s.getId() == sportId)
            {
                sport = s;
            }
        }
        
        BmiEntry bmiEntry = new BmiEntry(weight, height, bmi, category, gender, sport, hobbyList, user);
        request.setAttribute("bmiEntry", bmiEntry);
        bmiFacade.insertBmiEntry(bmiEntry);
        
        return pageToShow;
    }
}
