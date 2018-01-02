package com.oss;

import org.omg.CORBA.StringHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by burcu on 25.12.2017.
 */
public class NLPProcesses {

    public static final String[] SET_VALUES = new String[] {"a","about","above","all","again","against","am","an","and","any","are","are'nt",
            "as","at","be","because","been","before","being","below","between","but","by","can't","cannot","could",
            "couldn't","did","didn't","do","does","doesn't","during","each","few","for","from","further","had","has",
            "have","he","her","here","him","his","how","i","if","in","im","into","itself","let's","me","more","most","my",
            "myself","no","nor","of","or","only","other","ought","our","ourselves","out","over","own","same","she","should",
            "so","some","such","than","that","the","their","them","themselves","then","there","these","they","this","those",
            "through","to","too","under","until","up","very","was","we","were","what","when","where","which","while","who",
            "whom","why","with","would","you","your","yourself","yourselves","application"  };

    public static final Set <String> STOP_WORDS = new HashSet<String>(Arrays.asList(SET_VALUES));
    public static double minProbability ;
    public static String dbFunction;

    DbHelper db = new DbHelper();
    Repository repo = null;

    public NLPProcesses() throws SQLException, ClassNotFoundException {}

    public ArrayList<String> getLangs () {
        return db.getLanguages();
    }
    public ArrayList<ArrayList<String >> processAndResults (String topics, String pl,String website) {
        db.lastSelectionId = 1;
        ArrayList<Repository> result = new ArrayList<Repository>();
        setRepoSettings(website);

       //drop stop punctations get stem and drop stop words
        Set <String> rowTopics = dropStopWords(dropPunctations(topics));

        // test set (database datas) fetch 1000 for all loop
        // yukarıda yapılan işlemler tekrarlanıp row data her bir satır için
        while (true) {
            ArrayList<ArrayList<String>> datas;
            if (website.equalsIgnoreCase("github"))
                datas = db.selectRowsGithub(pl);
            else
                datas = db.selectRowsSourceForge();

            // db row bitti sonucu döndür
            if (datas.size () == 0)
                return sortByProbability(result);

            else {
                for (int i = 1; i< datas.size() ; i++ ) {
                    System.out.println("jaccarda gidiyoz");
                    // get description process it get set of words
                    Set <String> rowDescription = dropStopWords(dropPunctations(datas.get(i).get(2)));

                    double prob = JaccardIndex(rowDescription,rowTopics);

                    System.out.println(prob);

                    if (prob >=  minProbability) {
                        repo = new Repository();
                        repo.probability = prob*100;
                        repo.repo_url = datas.get(i).get(1);
                        result.add(repo);
                    }
                }

            }
        }
    }

    public void setRepoSettings (String website) {
        if (website.equalsIgnoreCase("sourceforge")) {
            minProbability = 0.1;
        }
        else {
            minProbability= 0.2;
        }

    }

    public ArrayList<ArrayList<String >> sortByProbability (ArrayList<Repository> result) {
        int min;
        ArrayList<ArrayList<String >> res = new ArrayList<ArrayList<String>>();
        ArrayList<String> a ;
        for (int i = 0; i < result.size(); i++) {
            // Assume first element is min
            min = i;
            for (int j = i + 1; j < result.size(); j++) {
                if (result.get(j).probability < result.get(min).probability) {
                    min = j;
                }
            }
            if (min != i) {
                final Repository temp = result.get(i);
                result.set(i, result.get(min));
                result.set(min, temp);
            }
        }

        for (int i = result.size()-1 ; i>0 ; i--) {
            a = new ArrayList<String>();
            a.add(result.get(i).repo_url);
            a.add(Double.toString(result.get(i).probability));
            res.add(a);
        }
        return res;
    }

    public Set<String> dropStopWords( Set<String> topics) {
        topics.removeAll(STOP_WORDS);
        return topics;
    }

    public Set<String> dropPunctations(String topics) {

        String[] words = topics.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");

        Set<String> rowData= new HashSet<String>();

        for (int i = 0 ; i<words.length ; i++) {
            rowData.add (getStem(words[i]));
        }
        return rowData;

    }

    // olasılığı döndür
    public double JaccardIndex (Set<String> test , Set<String> training) {

        double prob = 0;

        int first = test.size();
        int second = training.size();

        test.retainAll(training);

        int intersect = test.size();

        // kesişim bölü birleşim
        prob = (double)(intersect) / (double)(first + second - intersect); 

        return prob;
    }

    public static String getStem (String word) {

        char[] w = new char[501];
        PorterStemmer s = new PorterStemmer();
        char c;
        int j = 0;
        int k= 0;

        while (k<=word.length()){
            if (k == word.length()) {
                for (int d = 0; d < j; d++) s.add(w[d]);
                s.stem();
                {
                    String u;
                    u = s.toString();
                    return u;
                }
            }

            if (Character.isLetter(word.charAt(k))){
                c = Character.toLowerCase(word.charAt(k));
                w[j] = c;
                if (j < 500) j++;
            }

            k++;
        }
        return word;
    }


}
