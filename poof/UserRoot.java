import java.io.Serializable;

public class UserRoot extends User implements Serializable{
	
	public UserRoot(Directory directory){
		super("root", "Super User",directory);
	}
	
}

