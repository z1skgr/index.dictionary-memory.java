import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;


public class BinarySearch {
	private static final int DataPageSize = 128;
	private int diskAccesses;
	private int wantedIntexPointer;
	
	public BinarySearch(String input) throws IOException{
		System.out.println(BinarySearchInDic(input)+" accesses were made to the disk"); //Print the number of accesses in disk
	}
	
	public int BinarySearchInDic(String input) throws IOException{
		  diskAccesses=0;
		  ByteArrayInputStream bis;
          DataInputStream ois = null;
          String key = null;
          RandomAccessFile MyFile = new RandomAccessFile ("Fly.txt", "r");     
          RandomAccessFile TheFile = null;
          
		  boolean Found = false; 
          long DataPageNumInDict = MyFile.length() / DataPageSize ;
          byte[] ReadDataPage;
          int compare;
          int filePointer = 0;
          long mid;
          int start = 0; //We need to locate first and last page so that we can execute BinarySearch in the file
          long end = DataPageNumInDict-1; 
          
          
          while((start <= end) && (Found == false)){//Same page and WordFound == false we will //read every word of the page and contrast it with the given word.
              mid = (start + end)/2;  	
              int i=0;
              
              while((i<5) && (Found == false)){   
                  ReadDataPage = new byte[DataPageSize];   //Create an array of bytes size 128 that reflects a data page 
                  MyFile.seek(mid * DataPageSize+filePointer);                        //Setting file pointer in the middle page.filePointer is a pointer that seeks from one word to another.
                  MyFile.read(ReadDataPage);  
                  //Read DataPage                
                  bis= new ByteArrayInputStream(ReadDataPage); //Create an input stream of bytes that implements DatainputStream
                  ois= new DataInputStream(bis);             
                  byte bb[] = new byte[20];	
                  
                  ois.read(bb);	//Read word and place it on the bb array after we convert the word to byte form
                  key = new String(bb);  
                    key=key.trim();			//key is the word we want to compare it with the word given.
                  if(key.equalsIgnoreCase(input)){       
                      Found = true;                                 //Found = true means we found the word in the dictionary file
                  }
                  filePointer = filePointer + 24 ;                             //Increase filePointer to point to the next word(adding 24 bytes(20 String + 4 Int)
                  i++;                                                  //Increase i that shows the i-th word we consider .
              }
              diskAccesses++;
              filePointer=0;     
              //reinitialize filePointer to investigate the starting words in the next page we search
              if(Found == false){                    //Found=false means we compare the last word of the page with the searching  word            
                  compare = input.compareToIgnoreCase(key);
                  if(compare < 0){                       //compare<0(searching word is less than the word of the page) we take the right part of the middle page
                      end= mid-1;
                  }
                  else{
                      start = (int) (mid+1);             //else we take the left part 
                  }
              }
          }                                              //Same procedure
          if(Found == true){
        	  wantedIntexPointer=ois.readInt();	//After we find the word we use its evaluation to access to the right position to the index to write down its registration in files 
              System.out.println("\nIndexPointer is: "+wantedIntexPointer+"\n"); 
              
              TheFile = new RandomAccessFile ("Zoo.txt", "r");//Open index file
              ReadDataPage = new byte[DataPageSize];	//Read Page
              TheFile.seek(wantedIntexPointer*DataPageSize);	//Seek to the requested point
              TheFile.read(ReadDataPage);	
              bis= new ByteArrayInputStream(ReadDataPage);	//Create an array of bytes size 128 that reflects a data page 
              ois= new DataInputStream(bis);	//Create an input stream of bytes that implements DatainputStream
              
              diskAccesses++;
              int i=0;
              boolean status=false;
              int countRegistrations=0;
              
              System.out.println("The give word: "+ input+" \nFound below:");
              
              while(i<4 &&(!status)){
            	  byte bb[] = new byte[21];
                  ois.read(bb);	//We start to read registrations. 4 registration read means that we have to check if this word has more registrations. 4 registrations fit in a page.
                  String registration = new String(bb);
                  registration=registration.trim();
                  i++;
                  int bytekey=ois.readInt();
                  if(bytekey>0){
                	  countRegistrations++;
                	  System.out.println(countRegistrations+")File Name: " +registration+ " Start Byte: " +bytekey);//Print informations about the word
                      if(i==4){
                    	  i=0;
                    	  int check=ois.readInt();//check is the number to confirm if a page is linked with its next one
                    	  if(check<0){
                    		  status=true;	//check<0 means that our page do not chain with another page so this word do not have any other registrations
                    	  }else{
                    		  diskAccesses++;
                    		  ReadDataPage = new byte[DataPageSize];
                    		  TheFile.read(ReadDataPage);		//Create an array of bytes size 128 that reflects a new datapage 
                    		  bis= new ByteArrayInputStream(ReadDataPage);
                              ois= new DataInputStream(bis);	//Create a new input stream of bytes that implements DatainputStream
                    	  }
                    	  
                      }
                  }
                  
                	  
              }									//Return number of access in disk
              TheFile.close();
              MyFile.close();
              return diskAccesses; 
          }
          else{
        	  System.out.println("The word you are looking for was not found!");
        	  MyFile.close();
              return diskAccesses;
          }
          
        
	}

	public int getDiskAccesses() {
		return diskAccesses;
	}

	public void setDiskAccesses(int diskAccesses) {
		this.diskAccesses = diskAccesses;
	}
	  
	  
}

