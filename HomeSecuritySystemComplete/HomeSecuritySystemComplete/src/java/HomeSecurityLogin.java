import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/HomeSecurityLogin"})
public class HomeSecurityLogin extends HttpServlet {
    
 String enteredEmail;
 String enteredpassword;
 
 String dbEmail;
 String dbpassword;
 ResultSet result;
    
 Connection conn;
 PreparedStatement prepStat;
 Statement stat;

  //initialise database connection
public void init() throws ServletException
{
    String url = "jdbc:mysql://oursystem.mysql.database.azure.com:3306/projectdb??useSSL=true&requireSSL=false";
    String dbName = "projectdb";
    String userName = "project835@oursystem";
    String password = "Homesecure835";
    try {
        Class.forName("com.mysql.jdbc.Driver");
       conn = (Connection) DriverManager.getConnection
                  (url,userName,password); 
       
       System.out.print("testing");
       
        } catch (Exception e) {
    }
}  //  end of init() method
  
      protected void processRequest(HttpServletRequest request, 
                 HttpServletResponse response)
            throws ServletException, IOException {
           
        enteredEmail = request.getParameter("Email");
	enteredpassword = request.getParameter("Password");
        	
	try {
         
            //sql query database
          String query = "SELECT * from register where email=? and password=?";
          prepStat = (PreparedStatement) conn.prepareStatement(query);
	    prepStat.setString(1, enteredEmail); //entered email placeholder
	    prepStat.setString(2, enteredpassword); //entered password placeholder
          result = prepStat.executeQuery();
          
          while(result.next()){
              
              //set db email and password to the ones pulled from database
              dbEmail=result.getString("email"); 
              dbpassword=result.getString("password");                    
          }
          
           //used for testing
           //System.out.print("DBEMAIL" + dbEmail);
           //System.out.print("DBPASSWORDS" + dbpassword); 
           //System.out.print("ENTEREDEMAIL" + enteredEmail);
           //System.out.print("ENTEREDPASSWORD"+enteredpassword);
          
           //if details entered are correct, take user to system info page
          if(enteredEmail.equals(dbEmail)&&enteredpassword.equals(dbpassword)){
              
             response.sendRedirect("HomeSecuritySystemInformation.html");     
          }
          
          ///if details entered are incorrect, user remains on login page
          else{
              
               response.sendRedirect("HomeSecurityLogin.html"); 
          }
          
	    }//try end
        
	catch (Exception e) {
            System.err.println(e);
	}
        
          
}// end of processrequest
      


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

}