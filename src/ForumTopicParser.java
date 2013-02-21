import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ForumTopicParser {
	private Document doc;
	private Elements  content;
	
	public ForumTopicParser() throws IOException{
		doc = Jsoup.connect("http://www.forum.hr").get();
		content = doc.getElementsByClass("alt1active");
	}
	
	public List<ForumTopic> getTopicList(){
		
		List<ForumTopic> topics = new ArrayList<ForumTopic>();
		Elements topicNames, topicUrls;
		String link = "http://www.forum.hr/";
		
		for (Element elem : content){

			ForumTopic topic = new ForumTopic();
			
			//get topic name
			topicNames = elem.getElementsByTag("strong");
			topic.name = topicNames.get(0).toString();
			
			
			//get topic url
			topicUrls = elem.child(0).getElementsByAttribute("href");
        	topic.uri = link + topicUrls.get(0).attr("href");
        	
        	//get topic description
        	Elements descriptions = elem.getElementsByClass("smallfont");
        	String text = descriptions.text();
        	String desc = "";
        	if (text.contains("Podforum:")){
        		desc = text.split("Podforum: ")[0];
        	}
        	else if(text.contains("Podforumi: ")){
        		desc = text.split("Podforumi: ")[0];
        	}
        	else{
        		desc = text;
        	}
        	
    		topic.description = desc;
        	
			//get subtopics list
			for (Element el : descriptions){
				Elements subTopics = el.getElementsByTag("a");
				//System.out.println("El: " + el.toString());
				for (Element e : subTopics){
					String n = "";
					String url = "";
					Elements name = e.getElementsByTag("b");
					if (name.size() == 0){
						n = e.ownText();
					}
					else{
						n = name.get(0).unwrap().toString();
					}
					url = e.getElementsByAttribute("href").get(0).attr("href");
					topic.subTopics.put(n, url);
				}
			}
    	topics.add(topic);	
		}
		return topics;
	}
}
