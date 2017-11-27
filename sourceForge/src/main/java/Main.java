//
//  Main.java
//  sourceForge
//
//  Created by sule
//
import java.io.IOException;
import java.sql.SQLException;


public class Main {

    public static void main (String [] args) throws IOException, SQLException, ClassNotFoundException {
        String rootURL = "https://sourceforge.net/directory/";
        readAllPages readSourceForge = new readAllPages();
        DbHelper db = new DbHelper ();
        readSourceForge.readPages(rootURL,db);

    }
}
