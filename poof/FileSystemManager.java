import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileSystemManager{
	
	private Directory _actual;
	private User _activeUser;
	private FileSystem _filesystem;
	
	public void createFileSystem(){
		_filesystem = new FileSystem();
		_activeUser = _filesystem.getRoot();
		_actual = _activeUser.getPrincipalDirectory();
	}
	
	public void open(String name) throws FileNotFoundException{
		try{
			_filesystem.setFileName(name);
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(name)));
			_filesystem = (FileSystem)ois.readObject();
			_activeUser = (User)ois.readObject();
			_actual = _activeUser.getPrincipalDirectory();
			ois.close();
		}
		catch(FileNotFoundException e){ throw e; }
		catch(IOException e){ e.printStackTrace(); }
		catch(ClassNotFoundException e) { e.printStackTrace(); }
	}
	
	public void save(String name){
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(name)));
			oos.writeObject(_filesystem);
			oos.writeObject(_activeUser);
			oos.close();
		}
		catch(IOException e){ e.printStackTrace(); }
	}
	
	public void createUser(String username, String name) throws NotRootException, UsernameExistsException{
		_filesystem.createUser(username,name,_activeUser);
	}
	
	public void createDirectory(String name) throws PermissionDeniedException, DuplicateEntryException{
		_filesystem.createDirectory(name, _actual,_activeUser);
	}
	
	public void createFile(String name) throws PermissionDeniedException, DuplicateEntryException{
		_filesystem.createFile(name,_actual,_activeUser);
	}
	
	public void listEntry(String name) throws NoSuchEntryException{
		_filesystem.listEntry(name,_actual);
	}
	
	public void listAllEntry(){
		_filesystem.listAllEntry(_actual);
	}
	
	public void deleteEntry(String name) throws NoSuchEntryException, PermissionDeniedException, NotLegalRemovalException{
		_filesystem.deleteEntry(name, _actual, _activeUser);
	}
	
	public void selectWork(String name) throws NoSuchEntryException, NotDirectoryException{
		_actual = _filesystem.selectWork(name, _actual);
	}
	
	public void showPath(){
		_filesystem.showPath(_actual);
	}
	
	public void writeFile(String name, String line) throws NoSuchEntryException, NotFileException, PermissionDeniedException{
		_filesystem.writeFile(name,line,_actual,_activeUser);
	}
	
	public void showFile(String name) throws NoSuchEntryException, NotFileException{
		_filesystem.showFile(name, _actual);
	}
	
	public void changePermission(String name, boolean permission) throws NoSuchEntryException, PermissionDeniedException{
		_filesystem.changePermission(name,permission,_actual, _activeUser);
	}
	
	public void changeOwner(String name, String username) throws NoSuchEntryException, PermissionDeniedException, NoSuchUserException{
		_filesystem.changeOwner(name,username,_actual,_activeUser);
	}
	
	public void listAllUsers(){
		_filesystem.listAllUsers();
	}
	
	public void selectUser(String username) throws NoSuchUserException{
		_activeUser = _filesystem.getUser(username);
		_actual = _activeUser.getPrincipalDirectory();
	}
	
}
