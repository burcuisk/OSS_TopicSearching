import com.mysql.cj.api.xdevapi.JsonValue;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
