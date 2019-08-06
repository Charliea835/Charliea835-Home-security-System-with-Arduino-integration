import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/HomeSecurityRegister"})
public class HomeSecurityRegister extends HttpServlet {

    String Customer_first_name;
    String Customer_last_name;
    String Email;
    String Password;

    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    
    //connect to db, and create register table if none exists
   public void init() throws ServletException
    {
       String password = "Homesecure835";
       String url ="jdbc:mysql://oursystem.mysql.database.azure.com:3306/projectdb??useSSL=true&requireSSL=false";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=(com.mysql.jdbc.Connection) DriverManager.getConnection(url, "project835@oursystem", password);
            stat=(com.mysql.jdbc.Statement) conn.createStatement();
            stat.execute("CREATE TABLE IF NOT EXISTS register(firstname VARCHAR(20), lastname VARCHAR(20), Email VARCHAR(30), Password VARCHAR(20))");
        }
        catch(Exception e)
         {
                System.out.println( e);     
             
          }
        
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException  {

       // pull info from html
        Customer_first_name=request.getParameter("Customer_first_name");
        Customer_last_name=request.getParameter("Customer_last_name");
        Email=request.getParameter("Email"); 
        Password=request.getParameter("Password"); 
        
        //System.out.println(Customer_first_name+Customer_last_name+Email+Password);
        
        try{
            
            //sql query to insert values into register table
           String query="INSERT INTO register Values(?,?,?,?,?)";
           prepStat= (PreparedStatement) conn.prepareStatement(query);
           prepStat.setString(1,null);
           prepStat.setString(2,Customer_first_name);
           prepStat.setString(3,Customer_last_name);
           prepStat.setString(4,Email);
           prepStat.setString(5,Password);
           
           prepStat.execute();
           
           // wait 5 seconds to give the azure database time to update
           response.wait(5000);
          
        }
            catch(Exception e)
           {
             System.err.println(e); 
           
           }
               //redirect to server fie which will send registration email
               response.sendRedirect("https://wacsecurityproject.000webhostapp.com/email.php");
               
               //response.sendRedirect("C:\\xampp\\htdocs\\tests\\email.php");
               //response.sendRedirect("HomeSecurityLogin.html");
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
    
 
    
   
}

    

