//
//  readFromSource.java
//  sourceForge
//
//  Created by sule
//
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class readFromSource {

    public ArrayList<HashMap<String,String>> getUrlSource(String url) throws IOException {

        Document doc = Jsoup.connect(url).followRedirects(true).referrer("http://www.google.com")
                .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                //.timeout(0)
                .get();

        Elements projectsInPage = doc.select("ul.projects > li[itemtype=\"http://schema.org/SoftwareApplication\"]");
        Elements project = projectsInPage.select("div.project_info");
        Elements name = project.select("span[itemprop=name]");
        Elements link = project.select("a[href]");
        Elements description = project.select("p[itemprop=description]");


        ArrayList<HashMap<String,String>> all = new ArrayList<HashMap<String,String>>();
        for(int x = 0; x < project.size(); x++){
            HashMap projectAttributes = new HashMap();
            projectAttributes.put("Name", name.get(x).text());
            projectAttributes.put("Description", description.get(x).text());
            projectAttributes.put("Link", "https://sourceforge.net"+link.get(x).toString().split("\"")[1]);

            url = "https://sourceforge.net"+link.get(x).toString().split("\"")[1];
            doc = Jsoup.connect(url).followRedirects(true).referrer("http://www.google.com")
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    //.timeout(0)
                    .get();

            Elements pfeatures = doc.select("section[id=project-features]");
            Elements features = pfeatures.select("ul[class=features]");
            Elements pcategories = doc.select("section[id=project-categories-and-license]");
            Elements categories = pcategories.select("a[itemprop=softwareApplicationSubCategory]");
            Elements PLs = doc.select("section[class=project-info]");
            Elements PL = null;

            for(int i = 0; i < PLs.size(); i++){
                if(PLs.get(i).select("header > h3").text().equals("Programming Language")) {
                    PL = PLs.get(i).select("a[href]");
                }
            }

            projectAttributes.put("Features", features.text());
            projectAttributes.put("Categories", categories.text());
            if(PL != null)
                projectAttributes.put("PL", PL.text());
            else
                projectAttributes.put("PL", "");

            all.add(projectAttributes);
        }

        return all;
    }


}
