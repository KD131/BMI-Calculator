package web.commands;

import business.entities.BmiEntry;
import business.entities.User;
import business.exceptions.UserException;
import business.services.BmiFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

// could be Protected, but it might make it impossible for employees to act as a customer.
public class UserBmiEntriesCommand extends CommandUnprotectedPage
{
    private BmiFacade bmiFacade;
    
    public UserBmiEntriesCommand(String pageToShow)
    {
        super(pageToShow);
        this.bmiFacade = new BmiFacade(database);
    }
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException
    {
        User user = (User)request.getSession().getAttribute("user");
        List<BmiEntry> bmiEntryList = bmiFacade.getBmiEntriesByUser(user);
        request.setAttribute("bmiEntryList", bmiEntryList);
        return pageToShow;
    }
}
