import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mysql.jdbc.PreparedStatement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/HomeSecurityUpdateDetails"})
public class HomeSecurityUpdateDetails extends HttpServlet {
    
    String NewCustomer_first_name;
    String NewCustomer_last_name;
    String NewEmail;
    String NewPassword;
    
    String OldCustomer_first_name;
    String OldCustomer_last_name;
    String OldEmail;
    String OldPassword;

    Connection conn;
    PreparedStatement prepStat;
    Statement stat;
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException  {
        
        init(); //initialise db connection
        response.setContentType("text/html;charset=UTF-8");
        getHTML(request);
        
        try{
          
           stat=(com.mysql.jdbc.Statement) conn.createStatement();
           stat.execute("DELETE FROM register WHERE email='"+OldEmail+"';"); //delete old user details
           
           //insert new user details
           String query="INSERT INTO register VALUES (?,?,?,?,?)";
           prepStat= (PreparedStatement) conn.prepareStatement(query);
           prepStat.setString(1, null);
           prepStat.setString(2, NewCustomer_first_name);
           prepStat.setString(3, NewCustomer_last_name);
           prepStat.setString(4, NewEmail);
           prepStat.setString(5, NewPassword);
           
           prepStat.execute();
        }
            catch(Exception e)
           {
             System.err.println(e); 
           }
        
        response.sendRedirect("HomeSecurityLogin.html"); //redirect to login page
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
    
    //this method pulls the relevant user entered information from the html page
    public void getHTML(HttpServletRequest request){
        NewCustomer_first_name = request.getParameter("firstname");
        NewCustomer_last_name = request.getParameter("lastname");
        NewEmail = request.getParameter("Email");
        NewPassword = request.getParameter("Password");
        
        
        OldCustomer_first_name = request.getParameter("oldfirstname");
        OldCustomer_last_name = request.getParameter("oldlastname");
        OldEmail = request.getParameter("oldEmail");
        OldPassword = request.getParameter("oldPassword");
       
    }
    
    @Override
    //initialise database connection
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
        
        finally{
            System.out.println("function completed");
            
        }
    }
    
  
}

    

