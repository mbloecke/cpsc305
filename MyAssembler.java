import java.util.Scanner;
import java.util.Hashtable;
import java.io.RandomAccessFile;

public class MyAssembler {

	public static void main(String[] args) {

        	if(args.length != 1 || !args[0].endsWith(".asm")){
            		System.out.println("Invalid usage: Assembler must take 1 argument. The name of the .asm file");
            		System.exit(0);
        	}
        	
        	int fileLine = 0;
        	int assemblyLine = 0;
        	Hashtable<String, Integer> symbolTable = new Hashtable<String, Integer>();
		RandomAccessFile file;        


        	String filename = args[0];
        	System.out.println("pensifsfdlfse");
		try{
			file = new RandomAccessFile(args[0], "r");
		
		// check for valid asm file
		try{
		
		} catch(Exception f){
		}		


		// go through file first time
		try{
			file.seek(0); // go back to beginning
		} catch(Exception f){
		}


                try{
                        String line = "";
                        String hackLine = "";
                        while((line = file.readLine()) != null){

				if(line.contains("/")){
                                	line = line.substring(0,line.indexOf("/"));
                                	line = line.replaceAll("\\s","");
				}
                                 // first take a line and determine if it is an A or C instruction
				if(line.charAt(0) == '@'){
                                	line = line.substring(1);
					System.out.println(runA(line));
				} else {
					System.out.println(runC(line));
				}
                        }

                } catch(Exception f){
                }





		file.close();
		} catch(Exception e){} // end of filenotfound catch block

	}

	static String runA(String line){
		String bnum = Integer.toBinaryString(Integer.parseInt(line));
		for(int i = bnum.length(); i<15; i++){
			bnum = "0" + bnum;
		}
		return "0" + bnum;
	}

	static String runC(String line){
		String c = "";
		
		return "111" + c;
	}
	
	
	
	
}
