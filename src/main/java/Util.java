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

    public static void gitHubpullAndProcess(DbHelper db) throws SQLException {
        int i;
        ArrayList<String[]> gitHubDatasOnOnePage ;
        gitHubDatasOnOnePage = getGitRepositoryDatas(1);
        System.out.print(gitHubDatasOnOnePage.get(gitHubDatasOnOnePage.size()-8)[1]);
        for (i =0 ; i<gitHubDatasOnOnePage.size() ; i++) {
            addDatabase(gitHubDatasOnOnePage.get(i),db);
        }
        int j;
        while(!(gitHubDatasOnOnePage == null || gitHubDatasOnOnePage.size() == 0)) {
            j = Integer.parseInt(gitHubDatasOnOnePage.get(gitHubDatasOnOnePage.size()-1)[0])+1;
            gitHubDatasOnOnePage = getGitRepositoryDatas(j);

            for (i =0 ; i<gitHubDatasOnOnePage.size() ; i++) {
                addDatabase(gitHubDatasOnOnePage.get(i),db);
            }
        }


    }

    public static void addDatabase (String[] databaseColumns, DbHelper db) throws SQLException {
        db.addTable(Long.parseLong(databaseColumns[0]),databaseColumns[1],databaseColumns[2],databaseColumns[3]);
    }

    public static ArrayList<String[]> getGitRepositoryDatas(long i) {
        ArrayList<String[]> dataListOnePage=new ArrayList<String[]>();
       // String [] datas = new String[4];
        JSONParser parser = new JSONParser();

        try {
            URL github = new URL("https://api.github.com/repositories?access_token=02da237edfc9776e04677f03997351f7be34eacf&since="+i); // URL to Parse
            URLConnection yc = github.openConnection();
            yc.setRequestProperty("Accept","application/vnd.github.mercy-preview+json");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JSONArray a = (JSONArray) parser.parse(inputLine);

                // Loop through each item
                for (Object o : a) {
                    String[] datas = new String[4];
                    JSONObject tutorials = (JSONObject) o;

                    // get topics of the repo if no topics continue
                    String ownerRepo = (String) tutorials.get("full_name");
                    datas[2] = getTopics(ownerRepo);

                    // get repo description
                    String description = (String) tutorials.get("description");
                    datas[3] = description;

                    // if no description and no topics for repository no db process
                    System.out.println(datas[2]+ "  -  "+datas[3]);
                    if(datas[2].equals("[[]]") && (datas[3] == null || datas[3].equals("")) )
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
                    System.out.print("geldis");

                    System.out.println("\n");
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataListOnePage;
    }

    public static String getTopics(String ownerRepo) throws IOException, ParseException {
        String topics="";
        JSONParser parser = new JSONParser();
        try {
            URL github = new URL("https://api.github.com/repos/" + ownerRepo + "/topics?access_token=02da237edfc9776e04677f03997351f7be34eacf"); // URL to Parse
            URLConnection yc = github.openConnection();
            yc.setRequestProperty("Accept", "application/vnd.github.mercy-preview+json");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject a = (JSONObject) parser.parse(inputLine);;
                // System.out.println("Post ID : " + a.toJSONString());
                 topics = ""+a.values();
                 topics = topics.replaceAll("[\"]" ,"");
                 System.out.println("topics:" + topics);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return topics;
    }

}
