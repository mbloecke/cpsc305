import java.util.Scanner;
import java.util.HashMap;
import java.io.RandomAccessFile;
import java.io.PrintWriter;

public class MyAssembler {

	static int snum;

	public static void main(String[] args) {

        	if(args.length != 1 || !args[0].endsWith(".asm")){
            		System.out.println("Invalid usage: Assembler must take 1 argument. The name of the .asm file");
            		System.exit(0);
        	}
		int aLineNum = -1;        	
        	int fileLine = 0;
        	int assemblyLine = 0;
		String line = "";
		snum = 16;
        	HashMap<String, Integer> stable;
		stable = loadStable();
		RandomAccessFile file;        


        	String filename = args[0];
        	try{
			file = new RandomAccessFile(args[0], "r");
			PrintWriter p = new PrintWriter(filename.substring(0,filename.indexOf(".asm")) + ".hack", "UTF-8");

		// go through file first time
			while(true){			
				line = file.readLine();
                                if(line == null){break;}
				if(line != null && !line.equals("") && !line.trim().substring(0,1).equals("/")){
					
					if(line.trim().substring(0,1).equals("(") && line.contains(")")){
						stable.put(line.substring(line.indexOf("(")+1, line.indexOf(")")), aLineNum+1);
					} else {
						aLineNum++;
					}
				}
			}
		// start actual assembly
			file.seek(0);
			aLineNum = -1;
                        while(true){
				line = file.readLine();
				if(line == null){break;}
				if(line != null && !line.equals("") && !line.trim().substring(0,1).equals("/") && !line.trim().substring(0,1).equals("(")){
					aLineNum++;
					if(line.contains("/")){
                                		line = line.substring(0,line.indexOf("/"));
                                		line = line.replaceAll("\\s","");
					}
					line = line.trim();
                                	// first take a line and determine if it is an A or C instruction
					if(line.charAt(0) == '@'){
                                		line = line.substring(1);
						p.println(runA(line.trim(), stable));
					} else {
						p.println(runC(line.trim()));
					}
				}
			
                        }
			
			p.close();
			file.close();
		} catch(Exception e){
			System.out.println("There was an error in the .asm file on asm line " + aLineNum + ".");
			System.out.println(line);
		} // end of filenotfound catch block

	}

	static HashMap loadStable(){
		HashMap<String, Integer> temp = new HashMap<String, Integer>();
		for(int i=0; i<16; i++){
			temp.put("R"+i, i);
		}
		temp.put("SCREEN", 16384);
		temp.put("KBD", 24576);
		temp.put("SP", 0);
		temp.put("LCL", 1);
		temp.put("ARG", 2);
		temp.put("THIS", 3);
		temp.put("THAT", 4);

		return temp;			
	}


	static String runA(String line, HashMap stable){

		try{
			Integer.parseInt(line);
                	String bnum = Integer.toBinaryString(Integer.parseInt(line));
                	for(int i = bnum.length(); i<15; i++){
                        	bnum = "0" + bnum;
			}
	                return "0" + bnum;

		} catch(NumberFormatException e){  
  			if(stable.get(line) != null){
				String bnum = Integer.toBinaryString((Integer)stable.get(line));
				for(int i = bnum.length(); i<15; i++){
					bnum = "0" + bnum;
				}
				return "0" + bnum;
			} else {
				stable.put(line, snum);
				snum = snum+1;				
				String bnum = Integer.toBinaryString((Integer)stable.get(line));
                                for(int i = bnum.length(); i<15; i++){
                                        bnum = "0" + bnum;
                                }
                                return "0" + bnum;
			}
		}
	}

	static String runC(String line) throws Exception{
		try{

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

		} catch(Exception e){
			throw e;
		}
	}

	static String dest(String line) throws Exception{

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
		throw new Exception(); // throw error and line number if bad
	}

	static String jump(String line) throws Exception{
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
		throw new Exception(); // throw error and line number if bad
	}


	
	static String comp(String line, String c) throws Exception{
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
		throw new Exception(); // throw error if bad with line number
	} 
	




















































	
}
