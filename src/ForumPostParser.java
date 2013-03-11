import java.io.IOException;
import java.util.Iterator;
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
		String url = "http://www.forum.hr/showthread.php?t=758171";
		doc = Jsoup.connect(url).get();
		//postList = doc.getElementById("posts").select("table[id~=post[0-9]+");
		Element th_list = doc.getElementById("posts");
		if (th_list == null){
			url = url.replace("show.hr/forum", "forum.hr");
			doc = Jsoup.connect(url + "&iframed=1#").timeout(15*1000).get();
			postList = doc.getElementById("posts").select("div[id~=edit[0-9]+");
		}
		else{
			postList = th_list.getElementsByTag("tr");
		}
		postList = doc.getElementById("posts").select("div[id~=edit[0-9]+");
	}
	
	public int getSize(){
		return postList.size();
	}
	
	public ForumPost[] getPostList(){
		//get Date
		ForumPost fp;
		ForumPost[] fpList = new ForumPost[getSize()];
		int counter = 0;
		for(Element el : postList){
			Element table = el.select("table[id~=post[0-9]+]").first();
			Iterator<Element> ite = table.select("tr").iterator();
			fp = new ForumPost();
			
			//first <tr> is the date
			//time of the post
			fp.postDate = ite.next().text().split("#")[0];
			System.out.println("Post date: " + fp.postDate);
			
			//get part of the post where name and text are located (<tr>)
			Elements secondTR = ite.next().getElementsByTag("td");
			
			//get post authors name
			//some can be empty
			Element author = secondTR.select("a[class=bigusername]").get(0);
			fp.postAuthor = author.text();
			fp.postAuthorUri = "www.forum.hr/" + author.attr("href");
			System.out.println("Post author: " + fp.postAuthor);
			System.out.println("Post author url: " +  fp.postAuthorUri);
			
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
			fp.postText = wholeText;
			
			a.select("a[rel=nofollow]").remove();
			
			//get HTML
			String wholeHtml = a.html();
			fp.postHtml = wholeHtml;
			System.out.println("HTML: " + fp.postHtml);
			
			
			
			System.out.println("#########################################################################");
			
			fpList[counter] = fp;
			counter += 1;
		}
		
		return fpList;
	}
	
}
