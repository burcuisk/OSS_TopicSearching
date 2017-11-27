import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by burcu on 3.11.2017.
 */
public class DbHelper {

    Connection conn = null;

    // database connection
    public DbHelper() throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/oss_repos?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "vZtDs8tMkA#3");
                System.out.println("Bağlantı başarılı.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void addTable (String url,String projectName,String description,String features,String pl) throws SQLException {

            String query = " insert into sourceforge (url, projectName, description, features,pl)"
                    + " values (?, ?, ?, ?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, url);
            preparedStmt.setString (2, projectName);
            preparedStmt.setString (3, description);
            preparedStmt.setString(4, features);
            preparedStmt.setString(5,pl);

            // execute the preparedstatement
            preparedStmt.execute();
        }

        public void closeConnection() {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

}
