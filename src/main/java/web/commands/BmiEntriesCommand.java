package web.commands;

import business.services.BmiFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BmiEntriesCommand extends CommandProtectedPage
{
    private BmiFacade bmiFacade;
    
    public BmiEntriesCommand(String pageToShow, String role)
    {
        super(pageToShow, role);
        bmiFacade = new BmiFacade(database);
    }
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
    {
        return pageToShow;
    }
}
