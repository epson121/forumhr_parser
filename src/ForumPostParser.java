import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ForumPostParser {

	private Document doc;
	private Elements  postList;
	
	//catch socket timeout connection
	
	public ForumPostParser() throws IOException{
		//doc = Jsoup.connect("http://www.forum.hr/showthread.php?t=767743").get();
		doc = Jsoup.connect("http://www.forum.hr/showthread.php?t=177508&page=314").get();
		//postList = doc.getElementById("posts").select("table[id~=post[0-9]+");
		postList = doc.getElementById("posts").select("div[id~=edit[0-9]+");
	}
	
	public void getPostList(){
		//get Date
		
		System.out.println("called");
		System.out.println(postList.size());
		for(Element el : postList){

			Element table = el.select("table[id~=post[0-9]+]").first();
			Iterator<Element> ite = table.select("tr").iterator();
			ForumPost fp = new ForumPost();
			
			//first <tr> is the date
			//time of the post
			fp.postDate = ite.next().text().split("#")[0];
			System.out.println("Post date: " + fp.postDate);
			
			Elements secondTR = ite.next().getElementsByTag("td");
			
			//get post authors name
			//some can be empty
			fp.postAuthor = secondTR.select("a[class=bigusername]").text();
			System.out.println("Post author: " + fp.postAuthor);
			
			//get author avatar url 
			String avatarUrl = secondTR.select("div[class=smallfont]").select("img").attr("src");
			if (avatarUrl.isEmpty())
				fp.postAuthorAvatarPath = "";
			else
				fp.postAuthorAvatarPath = avatarUrl;
			System.out.println("Avatar uri: " + fp.postAuthorAvatarPath);
			
			//get post text in form like this:
			/*
			 * <quote> Quoted text goes here </quote> Unquoted text goes here <quote> ... </quote> ..
			 * or
			 * <quote> quoted text 1 </quote> <quote> quoted text 2 </quote>
			 * or 
			 * plain text, no quotes
			 * 
			 */
			Element a = secondTR.select("[id~=post_message_[0-9]+]").first();
			String wholeText = a.text(); 
			Elements quotes = a.select("table");
			for (Element elem : quotes){
				if (wholeText.contains(elem.text())){
					wholeText = wholeText.replace(elem.text(), "<quote>" + elem.text() + "</quote>").replace("Quote:", "");
				}
			}
			System.out.println(wholeText);
			//probat parse sa regexom (?:<quote>.+</quote>.+)*
			
			//get HTML
			String wholeHtml = a.html();
			fp.postHtml = wholeHtml;
			System.out.println("HTML: " + fp.postHtml);
			
			System.out.println("#########################################################################");
		}
		
	}
	
}
