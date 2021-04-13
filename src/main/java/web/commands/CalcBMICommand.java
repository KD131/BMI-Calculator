package web.commands;

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
    
        request.setAttribute("weight",weight);
        request.setAttribute("height",height);
        request.setAttribute("bmi",String.format(Locale.ENGLISH,"%.2f",bmi));  // rounds to 2 decimals.
        request.setAttribute("category",category);
        request.setAttribute("gender",gender);
        request.setAttribute("sportId",sportId);
        request.setAttribute("hobbies",hobbyList);
        
        bmiFacade.insertBmiEntry(weight, height, bmi, category, gender, sportId, hobbyList);
        
        return pageToShow;
    }
}
