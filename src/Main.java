import java.io.IOException;


public class Main extends ForumTopic{

	public static void main(String[] args) throws IOException {
		/*
		ForumTopicParser m = new ForumTopicParser();
		List<ForumTopic> ftl = m.getTopicList();
		for (ForumTopic topic : ftl){
			System.out.println("Topic name: " + topic.name);
			System.out.println("Topic url : " + topic.uri);
			System.out.println("Description : " + topic.description);
			for(Entry<String, String> subtopic : topic.subTopics.entrySet()){
				System.out.println("Subtopic name: " + subtopic.getKey());
				System.out.println("Subtopic url: " + subtopic.getValue());
			}
			System.out.println("-----------------------------------------");
		}
		*/
		
		//ForumThreadParser fthp = new ForumThreadParser();
		//fthp.getThreadList();
		
		ForumPostParser fpp = new ForumPostParser();
		fpp.getPostList();
		
	}
}
