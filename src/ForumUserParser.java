import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class ForumUserParser {
	
	private Document doc;
	private Element  userMiniStats;
	private Element mainUserInfo;
	
	public ForumUserParser() throws IOException{
		String url = "http://www.forum.hr/member.php?u=271568";
		doc = Jsoup.connect(url).timeout(15 * 1000).get();
		userMiniStats = doc.getElementById("stats_mini").select("[class=smallfont list_no_decoration profilefield_list]").get(0);
		mainUserInfo = doc.getElementById("main_userinfo");
	}
	


	public ForumUser getUserData(){
		
		ForumUser fUser = new ForumUser();
		fUser.userDefinedInfo = new HashMap<String, String>();
		
		Element temp = mainUserInfo.select("h1").get(0);
		
		System.out.println(temp.text());
		if (!temp.text().equals("")){
			fUser.userName = temp.text();
		}
		
		fUser.avatarUri = "www.forum.hr/" + doc.getElementById("user_avatar").attr("src");
		
		temp = mainUserInfo.select("div[id=last_online]").get(0);
		
		if (temp.text() != null){
			fUser.userLastActivity = temp.text();
		}
		
		int i = 0;
		for (Element el : userMiniStats.children()){
			System.out.println(el);
			System.out.println("______________________________");
			//check only evens for keys (on odds are values)
			if (i % 2 == 0){
				temp = userMiniStats.children().get(i);
				if (!temp.text().equals(""))
					fUser.userDefinedInfo.put(temp.text(), userMiniStats.children().get(i+1).text());
			}
			i += 1;
		}

		System.out.println("USername: " + fUser.userName);
		System.out.println("Last activity: " + fUser.userLastActivity);
		System.out.println("Avatar URI: " + fUser.avatarUri);
		System.out.println("Other info: " + fUser.userDefinedInfo);
		
		
		return fUser;
	}
	
	
}
