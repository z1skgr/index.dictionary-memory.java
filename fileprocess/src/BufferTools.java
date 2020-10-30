import java.util.Random;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BufferTools {
	
	private final static int DataPageSize = 128;
	private int[] pinakas1;
	private createDomes cdWA;
	private RandomAccessFile MyFile;
	public BufferTools(createDomes cd)throws IOException{
		getArray(cd);
		writeEurethrio(0);
		writeLexiko(0);
	}
	
	public BufferTools(){}
	
	public void writeEurethrio(long pos) throws IOException{
		Random r;
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ;	 //Create an input stream of bytes that implements DatainputStream
		DataOutputStream out = new DataOutputStream(bos);
		MyFile= new RandomAccessFile ("Zoo.txt", "rw"); //Write and open Index File
		MyFile.seek(0);
	    byte dst[];
		byte[] src;
		int arithmosSelidas=0;//arithmosSelidas saves the number of pages so that we can use it to add it to dictionary file
		
		pinakas1=new int[cdWA.getLexiko().size()];
        for(int i=0;i<cdWA.getLexiko().size();i++){
        	pinakas1[i]=arithmosSelidas;//we use pinakas1 to save arithmosSelidas so that we have knowledge where we can find the word in the dictionary
        	String word=cdWA.getLexiko().get(i);
        	int totalWrites=0;r=new Random();
        	int n=r.nextInt();
        	int t=n;
        	n=n-(2*t);
        	for(int j=0;j<cdWA.getWordArray().size();j++){//Valuate a temporary buffer dst to accept strings size 21 bytes
        			if(word.equalsIgnoreCase(cdWA.getWordArray().get(j).getWord())){//that means our buffer can fit 4 words before creating new page;
            			if(totalWrites<4){
            				totalWrites++;
            				src = cdWA.getWordArray().get(j).getNameFile().getBytes();//Get name file for the specific word
                			dst = new byte[21];
                            System.arraycopy(src, 0, dst, 1, src.length);//Copy src to dst
                            out.write(dst, 0, 21);	//First write name file and the starting bytes
                            out.writeInt(cdWA.getWordArray().get(j).getStartByte());
            			}else{
            				out.writeInt(arithmosSelidas+1);//Create new page 
            				out.close();
            				byte[] buf=bos.toByteArray();//Construct buffer and DataPage
            				byte[] DataPage=new byte[DataPageSize];
            				System.arraycopy(buf, 0, DataPage, 0,buf.length);
            				bos.close();
            				totalWrites=0;//Reinitialize totalWrites and buffer after complete DataPage 
            				MyFile.write(DataPage);
            				arithmosSelidas++;
            				bos = new ByteArrayOutputStream() ;//Create a new input stream of bytes that implements DatainputStream
            				out = new DataOutputStream(bos);
            				totalWrites++;//Dont forget the remaining word. We have to save it to the file although
            				src = cdWA.getWordArray().get(j).getNameFile().getBytes();
                			dst = new byte[21];
                            System.arraycopy(src, 0, dst, 1, src.length);
                            out.write(dst, 0, 21);
                            out.writeInt(cdWA.getWordArray().get(j).getStartByte());
            			}
            			
            			
            		}
         	}//but we have to make a linkage between the previous and the next page that corresponds to the same word if it exists
        	
        	out.writeInt(n);
        	out.close();
			byte[] buf=bos.toByteArray();//Create buffer even buf can hold more elements
			byte[] DataPage=new byte[DataPageSize];//Create page
			System.arraycopy(buf, 0, DataPage, 0,buf.length);
			bos.close();
			totalWrites=0;
			MyFile.write(DataPage);
			arithmosSelidas++;//Increase arithmosSelidas
			bos.reset();
			out = new DataOutputStream(bos);
        }
        MyFile.close();
        
	}
	
	
	public void writeLexiko(long pos)throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ;//Create a new input stream of bytes that implements DatainputStream
		DataOutputStream out = new DataOutputStream(bos);
		MyFile = new RandomAccessFile ("Fly.txt", "rw");//Open dictionary
		MyFile.seek(pos);
		byte dst[];
		byte[] src;
		int arithmosSelidas=1;
		int totalWrites=0;
		for(int i=0;i<cdWA.getLexiko().size();i++){//We use dst storing a byte array that makes up a word
        	if(totalWrites<5){//We can store buffer with 5 words so that we can store 5 int without outdo 128 bytes(this integer is the number that represents the first page that we can find the registrations of this word in the index
        		totalWrites++;
        		src = cdWA.getLexiko().get(i).getBytes();
        		dst = new byte[20];
        		System.arraycopy(src, 0, dst, 1, src.length);
                out.write(dst, 0, 20); //Write every single word in dictionary file
                out.writeInt(pinakas1[i]);//Write this integer in front of every word
        	}else{
        		out.close();
				byte[] buf=bos.toByteArray();//Create a buffer and storing the Datapage with the buffer
				byte[] DataPage=new byte[DataPageSize];
				System.arraycopy(buf, 0, DataPage, 0,buf.length);
				bos.close();
				totalWrites=0;
				MyFile.write(DataPage);//Write Datapage
				arithmosSelidas++;//Increase pages
				bos = new ByteArrayOutputStream() ;//Create a new input stream of bytes that implements DatainputStream
				out = new DataOutputStream(bos);
				totalWrites++;
				src = cdWA.getLexiko().get(i).getBytes();//Write the left word in the new page
    			dst = new byte[20];
                System.arraycopy(src, 0, dst, 1, src.length);
                out.write(dst, 0, 20);
                out.writeInt(pinakas1[i]);
        	}
        }
		byte[] buf=bos.toByteArray();//Create a buffer for the last appearances of the same word that do not fulfill buffer.Create a page though
		byte[] DataPage=new byte[DataPageSize];
		System.arraycopy(buf, 0, DataPage, 0,buf.length);
		bos.close();
		totalWrites=0;
		MyFile.write(DataPage);
		arithmosSelidas++;
		bos = new ByteArrayOutputStream() ;//Create a new input stream of bytes that implements DatainputStream
		out = new DataOutputStream(bos);
		MyFile.close();
	}
              
	public void getArray(createDomes cd)
	{
		cdWA = cd;
	}
}
