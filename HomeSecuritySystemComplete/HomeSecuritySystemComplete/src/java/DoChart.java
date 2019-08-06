import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.jdbc.JDBCCategoryDataset;

@WebServlet(name = "DoChart", urlPatterns = {"/showChart"})
public class DoChart extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doGet(request,response);
            //PrintWriter out = response.getWriter();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("image/png"); //set response type to png image
            OutputStream os = response.getOutputStream();
            
            JFreeChart chart = getChart();
            int width = 700;
            int height = 550;
            
            ChartUtilities.writeChartAsPNG(os, chart, width, height); //invoke method to write the chart as jpg image
        } catch (Exception ex) {
            Logger.getLogger(DoChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // this method retrieves sensor dat afrom the database, to be displayed in chart form
    public JFreeChart getChart()throws Exception {
        
            // mysql query
         String query = "SELECT * from sensordata";
	       JDBCCategoryDataset dataset = new JDBCCategoryDataset(
		"jdbc:mysql://oursystem.mysql.database.azure.com:3306/projectdb??useSSL=true&requireSSL=false", 
                "com.mysql.jdbc.Driver","project835@oursystem", "Homesecure835");
	       dataset.executeQuery(query); //execute query
               
               //create chart
	       JFreeChart chart =  
                ChartFactory.createBarChart3D("Your sensor data", "Id", "Distance (cm's)",
	        dataset, PlotOrientation.VERTICAL, true, true, false);
	       try {
	       
	       } catch (Exception e) {
	       System.out.println("Problem in creating chart.");
                   System.out.println(e);
    }
               return chart;
}
}