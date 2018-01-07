import java.util.Map;
import java.util.TreeMap;
import java.io.Serializable;

public class FileSystem implements Serializable{
	
	private Map<String, User> _users = new TreeMap<String,User> ();
	private Directory _mother;
	private boolean _alteredState;
	private String _fileName;
	
	public FileSystem(){
		_mother =  new Directory("/",true,0,"/");
		_mother.createSubDirectoryHome("home");
		UserRoot root = new UserRoot((Directory)_mother.getEntry("home"));
		_users.put(root.getName(), root);
		_alteredState = false;
	}
	
	public User getUser(String username) throws NoSuchUserException{
		if(!(_users.containsKey(username))){
			throw new NoSuchUserException();
		}
		return _users.get(username);
	}
	
	public boolean getAlteredState(){
		return _alteredState;
	}
	
	public String getFileName(){
		return _fileName;
	}
	
	public void setAlteredState(boolean state){
		_alteredState = state;
	}
	
	public void setFileName(String name){
		_fileName = name;
	}
		
	public void createUser(String username, String name, User user) throws NotRootException, UsernameExistsException{
		if(!(user instanceof UserRoot)){
			throw new NotRootException();
		}
		if(_users.containsKey(username)){
			throw new UsernameExistsException();
		}
		_users.put(username, new User(username, name,(Directory)_mother.getEntry("home")));
		_alteredState = true;
	}
	
	public void createDirectory(String name, Directory work, User user) throws PermissionDeniedException, DuplicateEntryException{
		if(work.containsEntry(name)){
			throw new DuplicateEntryException();
		}
		if(!(user instanceof UserRoot)){
			if(!(user.equals(work.getOwner()))){
				if(!(work.getPermission())){
					throw new PermissionDeniedException();
				}
			}
		}
		work.createSubDirectory(name);
		_alteredState = true;
	}
	
	public void createFile(String name, Directory work, User user) throws PermissionDeniedException, DuplicateEntryException{
		if(work.containsEntry(name)){
				throw new DuplicateEntryException();
			}
		if(!(user instanceof UserRoot)){
			if(!(user.equals(work.getOwner()))){
				if(!(work.getPermission())){
					throw new PermissionDeniedException();
				}
			}
		}
		work.createFile(name);
		_alteredState = true;
	}
	
	public void listEntry(String name, Directory work) throws NoSuchEntryException{
		if(name.equals("..")){
			if(work.getName().equals("/")){
				System.out.println(work);
			}
			else{
				work.listFather();
			}
		}
		else if(name.equals(".")){
			System.out.println(work);
		}
		if(!(work.containsEntry(name))){
			throw new NoSuchEntryException();
		}
		work.listEntry(name);
	}
	
	public void deleteEntry(String name, Directory work, User user) throws NoSuchEntryException, PermissionDeniedException, NotLegalRemovalException{
		if(name.equals(".") || name.equals("..")){
			throw new NotLegalRemovalException();
		}
		if(!(work.containsEntry(name))){
			throw new NoSuchEntryException();
		}
		if(!(user instanceof UserRoot)){
			if(!(user.equals(work.getOwner()))){
				if(!(work.getPermission())){
					throw new PermissionDeniedException();
				}
			}
		}
		work.deleteEntry(name);
		_alteredState = true;
	}
	
	public Directory selectWork(String name, Directory work) throws NoSuchEntryException, NotDirectoryException{
		if(name.equals("..")){
			if(work.getName().equals("/")){
				return work;
			}
			else{
				return work.getFather();
			}
		}
		else if(name.equals(".")){
			return work;
		}
		if(!(work.containsEntry(name))){
			throw new NoSuchEntryException();
		}
		Entry e = work.getEntry(name);
		if(!(e instanceof Directory)){
			throw new NotDirectoryException();
		}
		return (Directory)e;
	}
	
	public void showPath(Directory work){
		System.out.println(work.getPath());
	}
	
	public void listAllEntry(Directory work){
		work.list();
	}
	
	public void writeFile(String name, String line, Directory work, User user) throws NoSuchEntryException, NotFileException, PermissionDeniedException{
		if(!(work.containsEntry(name))){
			throw new NoSuchEntryException();
		}
		Entry e = work.getEntry(name);
		if(!(e instanceof File)){
			throw new NotFileException();
		}
		if(!(user instanceof UserRoot)){
			if(!(user.equals(e.getOwner()))){
				if(!(e.getPermission())){
					throw new PermissionDeniedException();
				}
			}
		}
		((File)e).addLine(line);
		_alteredState = true;
	}
	
	public void showFile(String name, Directory work) throws NoSuchEntryException, NotFileException{
		if(!(work.containsEntry(name))){
			throw new NoSuchEntryException();
		}
		Entry e = work.getEntry(name);
		if(!(e instanceof File)){
			throw new NotFileException();
		}
		System.out.println(((File)e).getText());
	}
	
	public void changePermission(String name, boolean permission, Directory work, User user) throws NoSuchEntryException, PermissionDeniedException{
		if(!(work.containsEntry(name))){
			throw new NoSuchEntryException();
		}
		Entry e = work.getEntry(name);
		if(!(user instanceof UserRoot)){
			if(!(user.equals(e.getOwner()))){
				if(!(e.getPermission())){
					throw new PermissionDeniedException();
				}
			}
		}
		e.setPermission(permission);
		_alteredState = true;
	}
	
	public void changeOwner(String name, String username, Directory work, User user) throws NoSuchEntryException, PermissionDeniedException, NoSuchUserException{
		if(!(work.containsEntry(name))){
			throw new NoSuchEntryException();
		}
		Entry e = work.getEntry(name);
		if(!(user instanceof UserRoot)){
			if(!(user.equals(e.getOwner()))){
				if(!(e.getPermission())){
					throw new PermissionDeniedException();
				}
			}
		}
		if(!(_users.containsKey(username))){
			throw new NoSuchUserException();
		}
		e.setOwner(_users.get(username));
		_alteredState = true;
	}
	
	public void listUser(String username){
		User u = _users.get(username);
		System.out.print(u);
	}
	
	public void listAllUsers(){
		for (Map.Entry<String,User> user : _users.entrySet()) {
			String key = user.getKey();
			listUser(key);
		}
	}
	
	public User getRoot(){
		return _users.get("root");
	}
	
}
