
public class wordItem {
	private String word;
	private String nameFile;
	private int startByte;
	public wordItem(String word,String nameFile, int startByte)
	{
		this.word=word;
		this.nameFile =nameFile;
		this.startByte =startByte;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	public int getStartByte() {
		return startByte;
	}
	public void setStartByte(int startByte) {
		this.startByte = startByte;
	}
}
