package web.commands;

import business.entities.Sport;
import business.exceptions.UserException;
import business.persistence.BmiMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ManageSportsCommand extends CommandProtectedPage
{
    BmiMapper bmiMapper;
    public ManageSportsCommand(String pageToShow, String role)
    {
        super(pageToShow, role);
        this.bmiMapper = new BmiMapper(database);
    }
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws UserException
    {
        String deleteId = request.getParameter("delete");
        if (deleteId != null)
        {
            int affectedRows = bmiMapper.deleteSportById(Integer.parseInt(deleteId));
            if (affectedRows > 0)
            {
                // deletion successful
                
                // imports new sportList into applicationScope from database.
                request.getServletContext().setAttribute("sportList", bmiMapper.getAllSports());
                
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
        return pageToShow;
    }
}
