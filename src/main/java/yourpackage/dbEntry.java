package yourpackage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import javax.servlet.annotation.WebServlet;

@WebServlet("/dbEntry")
public class dbEntry extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
       
            String name = request.getParameter("t1");
            String dateStr = request.getParameter("t2");
            java.sql.Date sqlDate = java.sql.Date.valueOf(dateStr);
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = System.getenv("DB_URL");
            String username = System.getenv("DB_USER");
             String password = System.getenv("DB_PASSWORD");

            Connection con = DriverManager.getConnection(url, username, password);

           
            String query = "INSERT INTO birthdays (name, birth_date) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setDate(2, sqlDate);

           
            int rows = ps.executeUpdate();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (rows > 0) {
                out.println("<h3>Record inserted successfully!</h3>");
            } else {
                out.println("<h3>Insertion failed.</h3>");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
