import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;


public class createStructures {
	File[] file;
	Vector<String> Dict=new Vector<String>();
	ArrayList<String> Index=new ArrayList<String>();
	ArrayList<wordItem> wordArray = new ArrayList<wordItem>();
	
	public createStructures(){
	}
	
	public void readFiles()throws IOException,FileNotFoundException{	//Function that read files a number of files given from the user	
		String userName=null;
		int number=0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//Read the number of files that user wants
		System.out.print("Number of files: ");
		String input = br.readLine();
		try{
			number = Integer.parseInt(input);	//Convert every character that user types to integer
		}catch(NumberFormatException e){
			System.out.println("Wrong format.Program Ends");	//Wrong format of the number means end program
			System.exit(1);
		}
        
        String fileName[]=new String[number];	//Creation of an array that with size=number that every cell of this array holds a file
        file=new File[number];


		for(int j=0;j<number;j++){		//User types every single name of every file
		    try {
		    	do{
		    		System.out.print("Enter your file name: ");  //Create variables type file and put them on the array
		    		userName = br.readLine();
		    		fileName[j]=userName;	
			    	file[j]=new File(fileName[j]);
		    	}while(!(file[j].exists()));
		    }catch (IOException ioe) {	//try catch for io
		    	System.out.println("IO error trying to read your name!");
		        System.exit(1);
		    }
		}
		
	}
	
	public void createDict() throws IOException,ClassNotFoundException{//Function to create Dictionary in memory
		readFiles();	//Call function to read every file that user wants
		for(int i=0;i<file.length;i++){
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file[i])));//Variable to read words from the file
			String line = null;	
            //int line_count=0;
            int byte_count;
            int total_byte_count=0;
            int fromIndex;
            while( (line = br.readLine())!= null ){	//This loop means the selected file has remaining elements
            	//line_count++;
                fromIndex=0;
                String [] tokens = line.split(",\\s+|\\s*\\\"\\s*|\\s+|\\.\\s*|\\s*\\:\\s*|\\;|\\?|\\!|\\'|\\(|\\)|\\[|\\]");
                for(int q=0;q<tokens.length;q++){
                	tokens[q]=toLowerCase(tokens[q]);	//Transform every capital to lowers
                }
                String line_rest=line;
                    for (int j=0;j<tokens.length;j++){
                	byte_count = line_rest.indexOf(tokens[j]);
                	if (tokens[j].length()!=0){
                		if(Dict.isEmpty()){	//Empty dictionary means that we have no reason to check for duplicate words
                		 	Dict.add(tokens[j]);	//Insert word
                		 	
                		}
                		int k=0;
                		boolean flag=false;
                		while(!flag && k<Dict.size()){//Check if dictionary has duplicate elements
                			if(Dict.get(k).equalsIgnoreCase(tokens[j])){//flag=true means that dictionary contains the specific word
                				flag=true;
                			}
                			k++;         			
                		}
                		if(!flag)
                 		   Dict.add(tokens[j]);//Insert the word if it has not been inserted again
                		Collections.sort(Dict);//Sort dictionary
                		fromIndex = fromIndex+byte_count+1+tokens[j].length();
                		wordArray.add(new wordItem(tokens[j], file[i].getName(), total_byte_count + fromIndex));//Creation of an array type wordArray that contains every word , the file that we can find it, and the starting bytes
                	}
                	if (fromIndex < line.length())
	                	   line_rest = line.substring(fromIndex);
                }
                total_byte_count += fromIndex;
            }
            br.close();
		}
		System.out.println("\nDictionary created successfully");
	}
	
	public void createIndex(){	//Method that creates index using the array that stores items type wordArray(word,filename,startbytes) 
		for(int i=0;i < Dict.size(); i++)	//For every word in dictionary we try to find them in the wordArray array so that we can recall their start bytes and their filename
    	{
    		for (int k = 0; k<wordArray.size(); k++)
    		{
    			wordItem key = wordArray.get(k);
    			if(key.getWord().equals(Dict.get(i)))//if we find them we add them in the index
    			{
    				String info=(key.getNameFile()+","+key.getStartByte());
    				//System.out.println(key.getWord()+", "+key.getNameFile()+","+key.getStartByte());
    				Index.add(info);
    			}
    				
    		}
    	}
		System.out.println("Index created successfully");
	}
	
	
	public void printWord(){//Method to print wordArray array
		for(int i=0;i<wordArray.size();i++){
			System.out.println(wordArray.get(i).getWord()+" "+ wordArray.get(i).getNameFile()+" "+ wordArray.get(i).getStartByte());
		}
		
	}
	
	public void printDict(){//Method to print dictionary
		for(int i=0;i<Dict.size();i++){
			System.out.println(Dict.get(i));
		}
	}
	
	public void printIndex(){//Method to print index
		for(int i=0;i<Index.size();i++){
			System.out.println(Index.get(i));
		}
	}

	public Vector<String> getDict() {//Setters & getters
		return Dict;
	}

	public void setDict(Vector<String> Dict) {
		this.Dict = Dict;
	}

	public ArrayList<String> getIndex() {
		return Index;
	}

	public void setIndex(ArrayList<String> Index) {
		this.Index = Index;
	}

	public ArrayList<wordItem> getWordArray() {
		return wordArray;
	}

	public void setWordArray(ArrayList<wordItem> wordArray) {
		this.wordArray = wordArray;
	}
	
	public String toLowerCase(String word) {//Method to convert capital letters to lower case
	       String result = "";
	       for (int i = 0; i < word.length(); i++) {
	           char currentChar = word.charAt(i);
	           char currentCharToLowerCase = Character.toLowerCase(currentChar);
	           result = result + currentCharToLowerCase;
	       }
	       return result;
	   }
}
