import java.io.IOException;


public class Main {

    public static void main (String [] args) throws IOException {
        String rootURL = "https://sourceforge.net/directory/";
        readAllPages readSourceForge = new readAllPages();
        readSourceForge.readPages(rootURL);

    }
}
