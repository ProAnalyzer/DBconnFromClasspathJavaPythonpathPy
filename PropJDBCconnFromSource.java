import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PropJDBCconnFromSource {

    public static void main(String[] args) throws  Exception{
        ClassLoader loader = PropJDBCconnFromSource.class.getClassLoader();
        URL urls = loader.getResource("DBconn.properties");

        File filePath = new File(urls.toURI());
        System.out.println("Class Path : "+filePath);

        try (InputStream input = loader.getResourceAsStream("DBconn.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find DBConn.properties");
                return;
            }

            prop.load(input);

            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");

            System.out.println("url : "+url);
            System.out.println("user : "+user);
            System.out.println("password : "+password);

            try (Connection conn = DriverManager.getConnection(url,user,password)) {

                if (conn != null) {
                    System.out.println("Connected to the database!");
                } else {
                    System.out.println("Failed to make connection!");
                }

            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}