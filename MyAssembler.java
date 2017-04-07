import java.util.Scanner;
import java.util.Hashtable;
import java.util.File;

public class MyAssembler {

	public static void main(String[] args) {

        	if(args.length != 1 || !args[0].endsWith(".asm")){
            		System.out.println("Invalid usage: Assembler must take 1 argument. The name of the .asm file");
            		System.exit(0);
        	}
        	
        	int fileLine = 0;
        	int assemblyLine = 0;
        	Hashtable<String, Integer> symbolTable = new Hashtable<String, Integer>();
        
        	String filename = args[0];
        	System.out.println("pensifsfdlfse");




	}



}
