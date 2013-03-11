import java.util.HashMap;


public class ForumUser {

	// all users have them
	protected String userName;
	protected String userRegistrationDate;
	protected String userPostCount;
	protected String userLastActivity;
	
	//some may have different things
	protected HashMap<String, String> userDefinedInfo;
	
}
