package web.commands;

import business.exceptions.UserException;
import business.services.BmiUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class CalcBMICommand extends CommandUnprotectedPage
{
    public CalcBMICommand(String pageToShow)
    {
        super(pageToShow);
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
        request.setAttribute("hobbies",hobbies);
        return pageToShow;
    }
}