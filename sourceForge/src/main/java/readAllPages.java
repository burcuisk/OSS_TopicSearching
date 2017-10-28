import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class readAllPages {

    public void readPages(String rootURL) throws IOException {
        readFromSource readerF = new readFromSource();
        String url;
        ArrayList<HashMap<String,String>> page = new ArrayList<HashMap<String, String>>();

        for(int pageNumber=0; pageNumber <= 5; pageNumber++) {
            url = rootURL+"?page="+Integer.toString(pageNumber+1);
             page.addAll(readerF.getUrlSource(url));
        }

        for(int i = 0; i < page.size(); i++){
            System.out.println((i+1)+".Name: "+page.get(i).get("Name"));
            System.out.println("Description: "+page.get(i).get("Description"));
            System.out.println(page.get(i).get("Link"));
        }
    }
}
