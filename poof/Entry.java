import java.io.Serializable;

public abstract class Entry implements Serializable{
	
	private String _path;
	private boolean _permission;
	private int _size;
	private String _name;
	private User _owner;
	
	public Entry(String path, boolean permission, int size, String name, User owner){
		_path = path;
		_permission = permission;
		_size = size;
		_name = name;
		_owner = owner;
	}
	
	public Entry(String path, boolean permission, int size, String name){
		_path = path;
		_permission = permission;
		_size = size;
		_name = name;
	}
	
	public String getPath(){
		return _path;
	}
	
	public boolean getPermission(){
		return _permission;
	}
	
	public int getSize(){
		return _size;
	}
	
	public String getName(){
		return _name;
	}
	
	public User getOwner(){
		return _owner;
	}
	
	public void setPath(String path){
		_path = path;
	}
	
	public void setPermission(boolean permission){
		_permission = permission;
	}
	
	public void setSize(int size){
		_size = size;
	}
	
	public void setName(String name){
		_name = name;
	}
	
	public void setOwner(User owner){
		_owner = owner;
	}
	
	public String permToString(){
		if(_permission){ 
			return "w"; 
		}
		return "-";
	}
	
	public void addPath(String path){
		_path = _path + "/" +  path;
	}
	
	public void addPathHome(String path){
		_path = _path + path;
	}
	
	@Override
	public String toString(){
		return permToString() + " " + getOwner().getName() + " " + getSize() + " " + getName();
	}
	
}
