import java.util.HashMap;


public class ForumTopic {

	public ForumTopic(){}
	
	protected String name;
	protected String uri;
	protected String description;
	protected HashMap<String, String> subTopics = new HashMap<String, String>();
	
}
