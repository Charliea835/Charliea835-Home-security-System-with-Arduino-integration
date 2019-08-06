import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;


@WebServlet(name = "retrieveTimestamp", urlPatterns = {"/retrieve"})
public class retrieveTimestamp extends HttpServlet {

    Connection conn;
    PreparedStatement prepstat;
    Statement stat;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            init();// call init method
            
            response.setContentType("text/html");
    	    PrintWriter out = response.getWriter();
            String title = "Your alarm was activated at these times";
            String docType = "<!doctype html>";
            out.println(docType + "<html>\n" + "<br>" +
    			"<head><hr><title>" + title + "</title></head>\n" +
    			"<h1 align=\"center\">" + title + "</h1><hr>\n");
            try{
    		// sql query to retrieve time from db
    		stat = (Statement) (java.sql.Statement) conn.createStatement();
                String selectSQL = "SELECT time from alarm";
                prepstat=(PreparedStatement) conn.prepareStatement(selectSQL);
                ResultSet rs = prepstat.executeQuery();
                
            while (rs.next()) {
	     String time = rs.getString("time");
             out.println("<center><h2>Time:" + time + "</center><br></h2>");  //print out time values 
            }
                
    	}catch(Exception e){
    		e.printStackTrace();
    	} //end try
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    //initialise database connection
    public void init() throws ServletException
    {
        
       String password = "Homesecure835";
       String url ="jdbc:mysql://oursystem.mysql.database.azure.com:3306/projectdb?verifyServerCertificate=true&useSSL=true&requireSSL=false";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=(com.mysql.jdbc.Connection) DriverManager.getConnection(url, "project835@oursystem", password);
        }
        catch(Exception e)
         {
                System.out.println( e);     
         }
        
        finally{
            System.out.println("Connected to Azure Database");
        }
       
     }
}