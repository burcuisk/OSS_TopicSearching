//
//  readAllPages.java
//  sourceForge
//
//  Created by sule
//
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class readAllPages {

    public void readPages(String rootURL, DbHelper db) throws IOException, SQLException {
        readFromSource readerF = new readFromSource();
        String url;
        ArrayList<HashMap<String,String>> page = new ArrayList<HashMap<String, String>>();

        for(int pageNumber=0; pageNumber <= 999; pageNumber++) {
            System.out.println("pagenumber:" + pageNumber);
            url = rootURL+"?page="+Integer.toString(pageNumber+1);
            System.out.println(url);
            page.addAll(readerF.getUrlSource(url));
        }

        for(int i = 0; i < page.size(); i++){
            /*System.out.println((i+1)+".Name: "+page.get(i).get("Name"));
            System.out.println("Description: "+page.get(i).get("Description"));
            System.out.println(page.get(i).get("Link"));
            System.out.println("Features: "+page.get(i).get("Features"));
            System.out.println("Categories: "+page.get(i).get("Categories"));
            System.out.println("PL: "+page.get(i).get("PL")); */
            db.addTable(page.get(i).get("Link"),page.get(i).get("Name"),page.get(i).get("Description"),
                    page.get(i).get("Features"),page.get(i).get("PL"));
        }
    }
}
