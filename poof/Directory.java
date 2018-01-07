import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;

public class Directory extends Entry implements Serializable{
	
	private Map<String, Entry> _entries = new TreeMap<String,Entry> ();
	private Directory _father;
	
	public Directory(String path, boolean permission, int size, String name, Directory father, User owner){
		super(path,permission,size,name,owner);
		_father = father;
	}
	
	public Directory(String path, boolean permission, int size, String name){
		super(path,permission,size,name);
	}
	
	public Directory getFather(){
		return _father;
	}
	
	public void setFather(Directory father){
		_father = father;
	}
	
	public void createFile(String name){
		Entry e = new File(getPath(), getPermission(), 0, name, getOwner());
		setSize(getSize() + 8);
		e.addPath(name);
		_entries.put(name, e);
	}
	
	public void createSubDirectory(String name){
		Entry e = new Directory(getPath(), getPermission(), 0, name, this, getOwner());
		setSize(getSize() + 8);
		e.addPath(name);
		_entries.put(name, e);
	}
	
	public void createSubDirectoryHome(String name){
		Entry e = new Directory(getPath(), getPermission(), 0, name, this, getOwner());
		setSize(getSize() + 8);
		e.addPathHome(name);
		_entries.put(name, e);
	}
	
	public void listEntry(String name){
		Entry e = _entries.get(name);
		System.out.println(e);
	}
	
	public void listFather(){
		System.out.println(_father);
	}
	
	public void list(){
		for (Map.Entry<String,Entry> entry : _entries.entrySet()) {
			String key = entry.getKey();
			listEntry(key);
		}
		listFather();
		System.out.println(this);
	}
	
	public void deleteEntry(String name){
		_entries.remove(name);
	}
	
	public boolean containsEntry(String name){
		return _entries.containsKey(name);
	}
	
	public Entry getEntry(String name){
		return _entries.get(name);
	}
	
	@Override
	public String toString(){
		return "d" + " " + super.toString();
	}
	
}
 
