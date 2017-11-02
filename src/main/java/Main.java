import java.io.IOException;
import java.sql.SQLException;
import org.json.simple.parser.ParseException;


/**
 * Created by burcu on 26.10.2017.
 */
public class Main {

    public static void main (String [] args) throws SQLException, ClassNotFoundException, IOException, ParseException {

        DbHelper db = new DbHelper ();
        Util.gitHubpullAndProcess(db);




    }



}
