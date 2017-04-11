import java.util.Scanner;
import java.util.Hashtable;
import java.io.RandomAccessFile;
import java.io.PrintWriter;

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
        	try{
			file = new RandomAccessFile(args[0], "r");
			PrintWriter p = new PrintWriter(filename.substring(0,filename.indexOf(".asm")) + ".hack", "UTF-8");

		// check for valid asm file		


		// go through file first time
		


                        String line = "";
                        String hackLine = "";
		
                        while(true){
				line = file.readLine();
				if(line == null){break;}
				if(line != null && !line.equals("") && !line.trim().substring(0,1).equals("/")){
					if(line.contains("/")){
                                		line = line.substring(0,line.indexOf("/"));
                                		line = line.replaceAll("\\s","");
					}
                                	// first take a line and determine if it is an A or C instruction
					if(line.charAt(0) == '@'){
                                		line = line.substring(1);
						p.println(runA(line));
					} else {
						p.println(runC(line));
					}
				}
			
                        }
			




		p.close();
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
		if(!line.contains("J")){
			if(line.contains("=")){
				c = c + comp(line.substring(line.indexOf("=")+1), c)
				+ dest(line.substring(0,line.indexOf("="))) + "000";
			} else {
				c = c + comp(line, c) + "000000";
			}
		} else {
			if(line.contains("=")){
				c = c + comp(line.substring(line.indexOf("=") + 1, line.indexOf(";")), c)
				+ dest(line.substring(0,line.indexOf("="))) + jump(line.substring(line.indexOf(";")+1));
			} else {
				c = c + comp(line.substring(0,line.indexOf(";")), c) + "000" 
				+ jump(line.substring(line.indexOf(";")+1));
			}
		}

		return "111" + c;
	}

	static String dest(String line){
		if(line.equals("M")){
			return "001";
		} else if(line.equals("D")){
			return "010";
		} else if(line.equals("MD")){
			return "011";
		} else if(line.equals("A")){
			return "100";
		} else if(line.equals("AM")){
			return "101";
		} else if(line.equals("AD")){
			return "110";
		} else if(line.equals("AMD")){
			return "111";
		}
		return "DESTERROR"; // throw error and line number if bad
	}

	static String jump(String line){
		if(line.equals("JGT")){
			return "001";
		} else if(line.equals("JEQ")){
			return "010";
		} else if(line.equals("JGE")){
			return "011";
		} else if(line.equals("JLT")){
			return "100";
		} else if(line.equals("JNE")){
			return "101";
		} else if(line.equals("JLE")){
			return "110";
		} else if(line.equals("JMP")){
			return "111";
		}
		return "JUMPERROR"; // throw error and line number if bad
	}


	
	static String comp(String line, String c){
		if(line.contains("M")){
			c = c + "1";
		} else {
			c = c + "0";
		}
		if(c.equals("1")){
			if(line.equals("M")){
				return "1110000";
			} else if(line.equals("!M")){
				return "1110001";
			} else if(line.equals("-M")){
				return "1110011";
			} else if(line.equals("M+1")){
				return "1110111";
			} else if(line.equals("M-1")){
				return "1110010";
			} else if(line.equals("D+M")){
				return "1000010";
			} else if(line.equals("D-M")){
				return "1010011";
			} else if(line.equals("M-D")){
				return "1000111";
			} else if(line.equals("D&M")){
				return "1000000";
			} else if(line.equals("D|M")){
				return "1010101";
			}
		} else {
			if(line.equals("0")){
				return "0101010";
			} else if(line.equals("1")){
				return "0111111";
			} else if(line.equals("-1")){
				return "0111010";
			} else if(line.equals("D")){
				return "0001100";
			} else if(line.equals("A")){
				return "0110000";
			} else if(line.equals("!D")){
				return "0001101";
			} else if(line.equals("!A")){
				return "0110001";
			} else if(line.equals("-D")){
				return "0001111";
			} else if(line.equals("-A")){
				return "0110011";
			} else if(line.equals("D+1")){
				return "0011111";
			} else if(line.equals("A+1")){
				return "0110111";
			} else if(line.equals("D-1")){
				return "0001110";
			} else if(line.equals("A-1")){
				return "0110010";
			} else if(line.equals("D+A")){
				return "0000010";
			} else if(line.equals("D-A")){
				return "0010011";
			} else if(line.equals("A-D")){
				return "0000111";
			} else if(line.equals("D&A")){
				return "0000000";
			} else if(line.equals("D|A")){
				return "0010101";
			}
		}
		return "COMPERROR"; // throw error if bad with line number
	} 
	




















































	
}
