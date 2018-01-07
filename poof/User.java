import java.io.Serializable;

public class User implements Serializable{
	
	private String _username;
	private String _name;
	private Directory _principal;
	
	public User(String username, String name, Directory directory){
		_username = username;
		_name = name;
		directory.createSubDirectory(name);
		_principal = (Directory) directory.getEntry(name);
		_principal.setOwner(this);
	}
	
	public String getUsername(){
		return _username;
	}
	
	public String getName(){
		return _name;
	}
	
	public Directory getPrincipalDirectory(){
		return _principal;
	}
	
	public void setPrincipalDirectory(Directory d){
		_principal = d;
	}
	
	public void setUsername(String username){
		_username = username;
	}
	
	public void setName(String name){
		_name = name;
	}
	
	@Override
	public String toString(){
		return getUsername() + ":" + getName() + ":" + _principal.getPath();
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof User){
			User u = (User) o;
			return _username.equals(u.getUsername()) && _name.equals(u.getName());
		}
		return false;
	}
	
}
