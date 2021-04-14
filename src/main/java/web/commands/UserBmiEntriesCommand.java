package web.commands;

import business.entities.User;
import business.exceptions.UserException;
import business.services.BmiFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // not the cleanest
        // TODO clean up!
        request.setAttribute("bmiEntryList", bmiFacade.getBmiEntriesByUser((User)request.getSession().getAttribute("user")));
        return pageToShow;
    }
}
