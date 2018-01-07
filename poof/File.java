import java.io.Serializable;

public class File extends Entry implements Serializable{
	
	private String _text;
	
	public File(String path, boolean permission, int size, String name, User owner){
		super(path, permission, size, name, owner);
	}
	
	public String getText(){
		return _text;
	}
	
	public void setText(String text){
		_text = text;
	}
	
	public void addLine(String line){
		_text = _text + line + "\n";
		setSize(getSize() + line.length());
	}
	
	public String getFirstLine(String text){
		String[] lines = text.split("\n");
		return lines[0];
	}
	
	@Override
	public String toString(){
		return "-" + " " + super.toString();
	}
}
