/*
 * This class is the controller of this web application. It takes HTTP GET 
 * request. It takes the search word from the request, uses the model to 
 * get the JSON and generate the JSON string as response. The response the GET
 * request is a JSON string.
 */
package youtubevideo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Caobonan
 */
@WebServlet(name = "YoutubeVideoServlet",
        urlPatterns = {"/getAYoutubeVideo"})
public class YoutubeVideoServlet extends HttpServlet {
    
    YoutubeVideoModel yvm = null;
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
    @Override
    public void init() {
        yvm = new YoutubeVideoModel();
    }
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the search parameter if it exists
        String search = request.getParameter("searchWord");
        // If search is null, return null.
        if (search != null) {
            yvm.doYoutubeSearch(search);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String responseJson = yvm.getResponseJson();
        PrintWriter out = response.getWriter();
        out.println(responseJson);
    }
}
