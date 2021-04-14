package web.commands;

import business.entities.BmiEntry;
import business.entities.Sport;
import business.entities.User;
import business.exceptions.UserException;
import business.services.BmiFacade;
import business.services.BmiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
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
        
        double weight;
        double height;
        double bmi;
        String category;
        String gender = request.getParameter("gender");
        int sportId = Integer.parseInt(request.getParameter("primary-sport"));
        // TODO: error handle the parseInt();
        String[] hobbies = request.getParameterValues("hobby");
        List<Integer> hobbyList = null;
        if (hobbies != null)
        {
            hobbyList = new ArrayList<>();
            for (String hobby : hobbies)
            {
                hobbyList.add(Integer.parseInt(hobby));
            }
        }
        
        try
        {
            // TODO: save as double but parse as int so you can do arithmetic but prevent users from inputting decimals
            // TODO: put the meter conversion in the calculation formula instead to preserve height as whole number in cm
            // TODO: set "step" in the HTML to 1 to prevent decimals. ACTUALLY, default is 1, so it works right now.
            weight = Double.parseDouble(request.getParameter("weight"));
            height = Double.parseDouble(request.getParameter("height")) / 100; // convert to meter
        }
        catch (NumberFormatException e)
        {
            // also solved using HTML built-in validation, type="number"
            request.setAttribute("error","Input must be a number.");
            return "index";
//            throw new UserException("Input must be a number.");
        }
        
        bmi = BmiUtil.calcBMI(weight,height);
        category = BmiUtil.getCategory(bmi);
    
        // TODO: you can consolidate this to just saving the bmiEntry instead of every variable separately.
        request.setAttribute("weight",weight);
        request.setAttribute("height",height);
        request.setAttribute("bmi",String.format(Locale.ENGLISH,"%.2f",bmi));  // rounds to 2 decimals.
        request.setAttribute("category",category);
        request.setAttribute("gender",gender);
        request.setAttribute("sportId",sportId);
        request.setAttribute("hobbies",hobbyList);
        
        // get User from session scope and cast it to User.
        // if not logged in, should return null.
        User user = (User)request.getSession().getAttribute("user");
    
        BmiEntry bmiEntry = new BmiEntry(weight, height, bmi, category, gender, hobbyList, user);
        // TODO: make a nicer way to instantiate Sports, or add them to the database.
        //  or at least think about it.
        //  I guess you can do it in the Mapper so you query the name as well.
        bmiEntry.setSport(new Sport(sportId));
        
        bmiFacade.insertBmiEntry(bmiEntry);
        
        return pageToShow;
    }
}
