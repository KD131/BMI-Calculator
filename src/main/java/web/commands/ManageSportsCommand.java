package web.commands;

import business.entities.Sport;
import business.exceptions.UserException;
import business.services.BmiFacade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ManageSportsCommand extends CommandProtectedPage
{
    BmiFacade bmiFacade;
    public ManageSportsCommand(String pageToShow, String role)
    {
        super(pageToShow, role);
        this.bmiFacade = new BmiFacade(database);
    }
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException
    {
        String deleteId = request.getParameter("delete");
        String editId = request.getParameter("edit");
        String update = request.getParameter("update");
        
        // if they're not null, then that's the button that was pressed
        
        if (deleteId != null)
        {
            int affectedRows = bmiFacade.deleteSportById(Integer.parseInt(deleteId));
            if (affectedRows > 0)
            {
                // deletion successful
                
                // imports new sportList into applicationScope from database.
                request.getServletContext().setAttribute("sportList", bmiFacade.getAllSports());
                
                 // removes from existing List in applicationScope
                /*
                List<Sport> sportList = (List<Sport>)request.getServletContext().getAttribute("sportList");
                for (Sport s : sportList)
                {
                    if (s.getId() == Integer.parseInt(deleteId))
                    {
                        sportList.remove(s);
                    }
                }
                request.getServletContext().setAttribute("sportList", sportList);
                 */
            }
            else
            {
                request.setAttribute("error", "Sport can't be deleted as it's used in a BMI entry.");
            }
        }
        else if (editId != null)
        {
            Sport sport = bmiFacade.getSportById(Integer.parseInt(editId));
            request.setAttribute("sport", sport);
            return "editsportpage";
        }
        else if (update != null)
        {
            String name = request.getParameter("name");
            int id = Integer.parseInt(request.getParameter("id"));
            
            int affectedRows = bmiFacade.updateSport(new Sport(id, name));
            if (affectedRows > 0)
            {
                // update successful
        
                // imports new sportList into applicationScope from database.
                request.getServletContext().setAttribute("sportList", bmiFacade.getAllSports());
            }
            else
            {
                request.setAttribute("error", "Sport couldn't be updated.");
            }
        }
        return pageToShow;
    }
}
