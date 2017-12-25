package com.oss;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by burcu on 25.12.2017.
 */
public class NLPProcesses {
    public static final String[] SET_VALUES = new String[] {"a","about","above","all","again","against","am","an","and","any","are","are'nt",
            "as","at","be","because","been","before","being","below","between","but","by","can't","cannot","could",
            "couldn't","did","didn't","do","does","doesn't","during","each","few","for","from","further","had","has",
            "have","he","her","here","him","his","how","i","if","in","into","itself","let's","me","more","most","my",
            "myself","no","nor","of","or","only","other","ought","our","ourselves","out","over","own","same","she","should",
            "so","some","such","than","that","the","their","them","themselves","then","there","these","they","this","those",
            "through","to","too","under","until","up","very","was","we","were","what","when","where","which","while","who",
            "whom","why","with","would","you","your","yourself","yourselves"  };

    public static final double minProbability = 0.5;

    public ArrayList<String> processAndResults (String topics, String repo,String website) {
       ArrayList<String> result = new ArrayList<String>();

       // training set
        ArrayList<String> rowTopics = dropStopWords(dropPunctations(topics));

        for (int i=0 ; i<rowTopics.size() ; i++ ) {
            // porterstemmer ile yeniden oluştur

        }


        if (website.equalsIgnoreCase("github")) {
            // test set (database datas) fetch 1000 for all loop
            // yukarıda yapılan işlemler tekrarlanıp row data her bir satır için
        }


        else if(website.equalsIgnoreCase("sourceForge")) {
            // test set (database datas) fetch 1000 for all loop
        }





       ArrayList<String> topicStems = new ArrayList<String>();


       return result;
    }

    public ArrayList<String> dropStopWords( ArrayList<String> topics) {
        // **********


        return topics;
    }

    public ArrayList<String> dropPunctations(String topics) {
        ArrayList<String> rowdata = null;
        // **********
        return rowdata;

    }

    // belirlenen probabilty üzerinde ya da altında
    public boolean JaccardIndex () {
        boolean ok = false;
        // **************
        return ok;
    }


}
