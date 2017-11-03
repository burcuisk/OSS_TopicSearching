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
    static int tokennum = 0 ;

    static String token2 = "3b54455a36c11d5eb2beef6402c8f1407982a0a5";
    static String token1 = "9cade6ebca7776ef9e65a256d1b9c2d3116a686a";
    static String token3 ="b57d5554b7761b265a01d48ffaf07926cae57fb7";
    static String token4 = "eed3e9aab5f1622f42625b87b3f31ec9e9a6ff2e";


    static String token5 = "7a6e511115a356180c51826669ff7a0e1648ec36";
    static String token6 = "22d3226db4aa75fa620aa986ab80969b111f4a1b";
    static String token7 = "19e0d60b42c88118e4e9a6b3b917b6b5ded3f967";
    static String token8 = "20758ce61455f15d698344e35ff224b2c6e20323";
    static  String token9 = "f842e95cfb90fc3f8d8c88b78f2478f39132280b";
    static String token10 = "8fafac2c0824d9f463b73654705ef7a9b3e4106a";
    static String token11 = "b50b8ad5527a9202abc2f3ba34a29357777618c3";
    static String token12 = "68a6a419614d771a1c83fb919c166a10c3b8d916";
    static String token13 = "1fd215fa34ac10ac57e9b4f29f9cf81cc9e67049";
    static String token14 ="2a1ff87e1cb7c351fb7958dd165c1d20f4bad17c";
    static String token15 = "1dde15387ee2daffceb9caf31a70196001b754c8";
    static String [] tokens = {token6,token7,token8,token9,token10,token11,token12,token13,token14};

    public static void gitHubpullAndProcess(DbHelper db) throws SQLException {
        int i;

        ArrayList<String[]> gitHubDatasOnOnePage ;
        gitHubDatasOnOnePage = getGitRepositoryDatas(103762);
        System.out.println(gitHubDatasOnOnePage.size());
        for (i = 0 ; i<gitHubDatasOnOnePage.size() ; i++)
            addDatabase(gitHubDatasOnOnePage.get(i),db);

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
        db.addTable(Long.parseLong(databaseColumns[0]),databaseColumns[1],databaseColumns[2],databaseColumns[3],databaseColumns[4]);
    }

    public static ArrayList<String[]> getGitRepositoryDatas(long i) {

        ArrayList<String[]> dataListOnePage=new ArrayList<String[]>();
        JSONParser parser = new JSONParser();

        try {
            URL github = new URL("https://api.github.com/repositories?access_token="+token3+"&since="+i); // URL to Parse
            URLConnection yc = github.openConnection();
            yc.setRequestProperty("Accept","application/vnd.github.mercy-preview+json");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                JSONArray a = (JSONArray) parser.parse(inputLine);
                // Loop through each item
                for (Object o : a) {
                    String[] datas = new String[5];
                    JSONObject tutorials = (JSONObject) o;

                    // get topics of the repo if no topics continue
                    String ownerRepo = (String) tutorials.get("full_name");
                    datas[2] = getTopics(ownerRepo);

                    // get repo description
                    String description = (String) tutorials.get("description");
                    datas[3] = description;

                    // if no description and no topics for repository no db process
                    System.out.println(datas[2]+ "  -  "+datas[3]);
                    if(datas[2]== null && (datas[3] == null || datas[3].equals("")) )
                        continue;

                    // get repo language
                    datas[4] = getLanguage(ownerRepo);
                    // if no pl it says that repo is empty
                    if(datas[4] == null || datas[4].equals(""))
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
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("hello");
            e.printStackTrace();
            tokennum++;
            System.out.println("çıkıyoz");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataListOnePage;
    }

    public static String getLanguage(String ownerRepo) {
        String language ="";
        JSONParser parser = new JSONParser();
        try {
            URL github = new URL("https://api.github.com/repos/" + ownerRepo + "/languages?access_token="+token3); // URL to Parse
            URLConnection yc = github.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject a = (JSONObject) parser.parse(inputLine);
                String lan = a.toString().replaceAll("[\"]","");
                lan = lan.toString().replaceAll("[{}]","");
                lan = lan.toString().replaceAll(":"," ");

                String langs[] = lan.split(",");

                double max=0;
                for ( int i= 0; i<a.size(); i++) {
                   String data[] = langs[i].split(" ");

                   if (max < Double.parseDouble(data[data.length-1])) {
                       language ="";
                       max = Double.parseDouble(data[data.length-1]);
                       for (int k=0; k<data.length-1 ; k++) {
                           if (k == data.length-2)
                               language += data[k];
                           else
                               language += data[k]+" ";
                       }

                   }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            tokennum++;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return language;
    }

    public static String getTopics(String ownerRepo) throws IOException, ParseException {
        String topics="";
        JSONParser parser = new JSONParser();
        try {
            URL github = new URL("https://api.github.com/repos/" + ownerRepo + "/topics?access_token="+token3); // URL to Parse
            URLConnection yc = github.openConnection();
            yc.setRequestProperty("Accept", "application/vnd.github.mercy-preview+json");
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject a = (JSONObject) parser.parse(inputLine);
                topics = ""+a.values();

                topics = topics.replaceAll("[\"]" ,"");
                topics = topics.replaceAll("]" ,"");
                topics = topics.replaceAll("\\[" ,"");

                if (topics.equals("")) topics = null;
                 System.out.println("topics:" + topics);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            tokennum++;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return topics;
    }

}
