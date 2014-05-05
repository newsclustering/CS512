import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Clustering {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		//set up folder called news
		// run this program on the directory above news

		BufferedReader br = null;

		try {
			ArrayList<Article> news = new ArrayList<Article>();
			String sCurrentLine;

			String[] vers = {"a","b","c","d","e"};
			for(int i=1; i <= 20; i++){

				for(int j=0; j < vers.length; j++){
					
					String id = "" + i + vers[j];
					String file = ".\\news\\" + id + ".txt";
					br = new BufferedReader(new FileReader(file));

					int counter =0;
					String newstext= "";
					while ((sCurrentLine = br.readLine()) != null) {
						if(counter==0 || counter ==1){

						}
						else{
							newstext = newstext + sCurrentLine;
						}
						counter++;
					}
					//System.out.print(file);
				//	System.out.println(newstext);
					
					String ret = Tag(newstext);
					System.out.println(ret);
					Article cur = new Article(id, newstext);
					news.add(cur);
				}
			}
			

			cluster(news);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	
	
	public static void cluster(ArrayList<Article> news){
		
		System.out.println(news.size());
		
		for(int i=0; i < news.size(); i++){
			System.out.println(news.get(i).id);
			System.out.println(news.get(i).text);
		}
	}
	
	 private static String Tag(String input) {

		    /*
		     * Choosing the model to tag the documents with. 
		     */

		    MaxentTagger tagger = new MaxentTagger("models/wsj-0-18-bidirectional-nodistsim.tagger");
		    
		    List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(input));
		    
		    String retval = "";

		    for (List<HasWord> sentence : sentences) 
		    {
		      ArrayList<TaggedWord> tSentence = tagger.tagSentence(sentence);
		      retval = retval + Sentence.listToString(tSentence, false);
		    }

		    return retval;
		  }  
}

class Article{
	public String id="";
	public String text="";
	Article(String id, String text){
		this.id = id;
		this.text = text;
	}
}
