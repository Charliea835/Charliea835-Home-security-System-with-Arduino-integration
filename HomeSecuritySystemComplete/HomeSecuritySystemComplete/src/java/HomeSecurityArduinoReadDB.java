import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/HomeSecurityArduinoReadDB"})
public class HomeSecurityArduinoReadDB extends HttpServlet {

  Connection conn;
  Statement stat;
      
    //initialise database connection
public void init() throws ServletException
{
   String password = "Homesecure835";
       String url ="jdbc:mysql://oursystem.mysql.database.azure.com:3306/projectdb??useSSL=true&requireSSL=false";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=(com.mysql.jdbc.Connection) DriverManager.getConnection(url, "project835@oursystem", password);
            stat=(com.mysql.jdbc.Statement) conn.createStatement();
           
           
        } catch (Exception e) {
    }
}
    
            
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
          
    	Statement stmt;

    	// Set response content type
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	String title = " This is how close someone got to your door in centimeters";
    	String docType = "<!doctype html>";
    	out.println(docType + "<html>\n"  +
    			"<head><title>" + title + "</title></head>\n" +
    			"<h1 align=\"center\">" + title + "</h1>\n");
             
        
    	try{
    		// Execute SQL query
    		stmt = (Statement) conn.createStatement();

                String sql;
    		
                sql = "SELECT value FROM sensordata";
    		ResultSet rs = stmt.executeQuery(sql);
    		// Extract data from result set
                
    		while(rs.next()){
                    
    			//Retrieve by column name
                        String value =rs.getString("value");
               
                        //Display values      
                        out.println("object detected at:" + value + "CM" + "<br><hr>");
                }
                
    		out.println("</body></html>");
                
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
        
