import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
	public static void main(String[] args)throws IOException, ClassNotFoundException {
		int answer=-1;
		createDomes cd=null;
		BufferTools bT=null;
		System.out.println("Welcome!\n");
		try{
			cd = new createDomes();//Constructor call
			cd.createLexiko();//Call methods to create dictionary and index
			cd.createEurethrio();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		
		//cd.printLexiko();
		//cd.printEurethrio();
		//cd.printWord();
		bT=new BufferTools(cd);//Call constructor to create dictionary and index in files
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//Create a variable to read from console
		boolean flag=false;
		while(!flag){
			try{
				do{
					System.out.println("\nEpiloges\n1)Anazitisi lexis");//Answer 1 means user desires to find a word in dictionary and its registration in index
					System.out.println("2)Exodos programmatos");//Answer 2 means exit from the program
					System.out.print("Dialexte apantisi:");
					String input = br.readLine();
					answer= Integer.parseInt(input);
				}while(answer<0&&answer>2);
			}catch(NumberFormatException e){
				System.out.println("Your answer does not correspond to a number.Try again");
			}
			
			
	        if(answer==1){
	        	System.out.print("Grapste ti lexi pou thelete na anazitisete:");//Read word for search
	        	String input = br.readLine();
	        	BinarySearch bS=new BinarySearch(input);//Create variable BinarySearch
	        	flag=false;
	        }if(answer==2)
	        	flag=true;
		}
		System.out.println("\n\nTelos Programmatos\nGOODBYE\n");
	}

}
