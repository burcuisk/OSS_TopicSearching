import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by burcu on 27.10.2017.
 */

public class Util {
    static int tokennum = 0;
    static boolean change= false;
    static String token1 = "9cade6ebca7776ef9e65a256d1b9c2d3116a686a";
    static String token2 = "3b54455a36c11d5eb2beef6402c8f1407982a0a5";
    static String token3 ="b57d5554b7761b265a01d48ffaf07926cae57fb7";
    static String token4 = "eed3e9aab5f1622f42625b87b3f31ec9e9a6ff2e";

    static String[] tokens = {token1,token2,token3,token4};

    public static void gitHubpullAndProcess(DbHelper db) throws SQLException {
        int i;
        long j = 0;
        ArrayList<String[]> gitHubDatasOnOnePage = null;

        while(true) {
            if (!change)
                j= db.getLastRepoId();
            try {
                gitHubDatasOnOnePage = getGitRepositoryDatas(j);

                if (gitHubDatasOnOnePage == null || gitHubDatasOnOnePage.size() == 0) {
                    //tokennum = (tokennum + 1) % 4;
                    System.out.println("girdi");
                    j +=100;
                    change = true;
                    continue;
                }

                for (i =0 ; i<gitHubDatasOnOnePage.size() ; i++) {
                    addDatabase(gitHubDatasOnOnePage.get(i),db);
                    change = false;
                }
                gitHubDatasOnOnePage = null;
            } catch (IOException e) {
                tokennum = (tokennum + 1) % 4;
                continue;
            }
        }
    }

    public static void addDatabase (String[] databaseColumns, DbHelper db) throws SQLException {
        db.addTable(Long.parseLong(databaseColumns[0]),databaseColumns[1],databaseColumns[2],databaseColumns[3],databaseColumns[4]);
    }

    public static ArrayList<String[]> getGitRepositoryDatas(long i) throws IOException{

        ArrayList<String[]> dataListOnePage = new ArrayList<String[]>();
        JSONParser parser = new JSONParser();
        BufferedReader in = null;

        try {
            URL github = new URL("https://api.github.com/repositories?access_token="+tokens[tokennum]
                    +"&since="+i); // URL to Parse
            URLConnection yc = github.openConnection();
            yc.setRequestProperty("Accept", "application/vnd.github.mercy-preview+json");

            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        } catch (Exception e) {
            System.out.println("E1 : " + e.getStackTrace());
            return null;
        }

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            JSONArray a;

            try {
                a = (JSONArray) parser.parse(inputLine);
            } catch (Exception e) {
                System.out.println("E2 : " + e.getStackTrace());
                continue;
            }

            // Loop through each item
            for (Object o : a) {
                try {
                    String[] datas = new String[5];
                    JSONObject tutorials = (JSONObject) o;
                    String ownerRepo = (String) tutorials.get("full_name");

                    datas[2] = getTopics(ownerRepo);
                    datas[3] = (String) tutorials.get("description");

                    // if no description and no topics for repository no db process
                    System.out.println(datas[2] + "  -  " + datas[3]);
                    if (datas[2] == null && (datas[3] == null || datas[3].equals("")))
                        continue;

                    // get repo language
                    datas[4] = getLanguage(ownerRepo);
                    // if no pl it says that repo is empty
                    if (datas[4] == null || datas[4].equals(""))
                        continue;

                    // get id of repo
                    Long id = (Long) tutorials.get("id");
                    System.out.println("Post ID : " + id);
                    i = id;
                    datas[0] = id.toString();

                    // get repo url
                    String html_url = (String) tutorials.get("html_url");
                    System.out.println("Post Title : " + html_url);
                    datas[1] = html_url;

                    dataListOnePage.add(datas);

                    System.out.println("\n");
                } catch (IOException e) {
                    tokennum = (tokennum + 1) % 4;
                    continue;
                } catch (Exception e) {
                    System.out.println("E3 " + e.getStackTrace());
                }
            }
        }

        return dataListOnePage;
    }

    public static String getLanguage(String ownerRepo) throws IOException{
        String language = "";
        JSONParser parser = new JSONParser();
        BufferedReader in = null;

        try {
            URL github = new URL("https://api.github.com/repos/" + ownerRepo + "/languages?access_token=" + tokens[tokennum]); // URL to Parse
            URLConnection yc = github.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        } catch (Exception e) {
            throw new IOException();
        }

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            try {
                JSONObject a = (JSONObject) parser.parse(inputLine);
                JSONObject tutorials = (JSONObject) a;

                String lan = a.toString().replaceAll("[\"]", "");
                lan = lan.toString().replaceAll("[{}]", "");
                lan = lan.toString().replaceAll(":", " ");

                String langs[] = lan.split(",");

                double max = 0;
                for (int i = 0; i < a.size(); i++) {
                    String data[] = langs[i].split(" ");

                    if (max < Double.parseDouble(data[data.length - 1])) {
                        language = "";
                        max = Double.parseDouble(data[data.length - 1]);
                        for (int k = 0; k < data.length - 1; k++) {
                            if (k == data.length - 2)
                                language += data[k];
                            else
                                language += data[k] + " ";
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("E5 : " + e.getStackTrace());
            }
        }

        return language;
    }

    public static String getTopics(String ownerRepo) throws IOException {
        String topics="";
        JSONParser parser = new JSONParser();
        BufferedReader in = null;

        try {
            URL github = new URL("https://api.github.com/repos/" + ownerRepo + "/topics?access_token=" + tokens[tokennum]);
            URLConnection yc = github.openConnection();
            yc.setRequestProperty("Accept", "application/vnd.github.mercy-preview+json");
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        } catch (Exception e) {
            throw new IOException();
        }

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            try {
                JSONObject a = (JSONObject) parser.parse(inputLine);
                topics = "" + a.values();
                JSONObject tutorials = (JSONObject) a;

                topics = topics.replaceAll("[\"]", "");
                topics = topics.replaceAll("]", "");
                topics = topics.replaceAll("\\[", "");

                if (topics.equals("")) topics = null;
                System.out.println("topics:" + topics);
            } catch (Exception e) {
                System.out.println("E4 : " + e.getStackTrace());
            }
        }

        return topics;
    }

}
