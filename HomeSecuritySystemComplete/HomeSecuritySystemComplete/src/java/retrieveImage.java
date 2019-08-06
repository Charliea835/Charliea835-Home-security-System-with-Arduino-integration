import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "retrieveImage", urlPatterns = {"/show"})
public class retrieveImage extends  HttpServlet{
    
 public void doGet(HttpServletRequest request, HttpServletResponse 
    response) throws ServletException, IOException{
     
    //PrintWriter pw = response.getWriter();
   String password = "Homesecure835";
   String url ="jdbc:mysql://oursystem.mysql.database.azure.com:3306/projectdb??useSSL=true&requireSSL=false";
   
    java.sql.Connection con=null;
    
        //connect to database
    try{  
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con=DriverManager.getConnection(url,"project835@oursystem", password);  
        
        System.out.println("connected to Azure database ");
        
        Statement st1=con.createStatement();
        ResultSet rs1 = st1.executeQuery("select * from camera"); 
        String imgLen="";
        
        if(rs1.next()){
            imgLen = rs1.getString(1); //get image length
            System.out.println(imgLen.length());
        }  
    
    rs1 = st1.executeQuery("select * from camera"); //select camera data
    
        if(rs1.next()){
            int len = imgLen.length();
            byte [] rb = new byte[len];
            InputStream readImg = rs1.getBinaryStream(1);
            int index=readImg.read(rb, 0, len);  
            
            System.out.println("index"+index);
            
            st1.close();
            response.reset();
            response.setContentType("image/jpg");
            response.getOutputStream().write(rb,0,len);
            response.getOutputStream().flush();  
        }
    
  }// end of try
    catch (Exception e){
    e.printStackTrace();
  }
    
  }
}