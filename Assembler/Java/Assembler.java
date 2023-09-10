import java.util.*;
import java.io.*;
public class Assembler {
    public static void main(String[] args) throws IOException {
        try {
            int value=16;
            Map<String, String> builtinSymbols = new HashMap<>();
            builtinSymbols.put("R0", "0");
            builtinSymbols.put("R1", "1");
            builtinSymbols.put("R2", "2");
            builtinSymbols.put("R3", "3");
            builtinSymbols.put("R4", "4");
            builtinSymbols.put("R5", "5");
            builtinSymbols.put("R6", "6");
            builtinSymbols.put("R7", "7");
            builtinSymbols.put("R8", "8");
            builtinSymbols.put("R9", "9");
            builtinSymbols.put("R10", "10");
            builtinSymbols.put("R11", "11");
            builtinSymbols.put("R12", "12");
            builtinSymbols.put("R13", "13");
            builtinSymbols.put("R14", "14");
            builtinSymbols.put("R15", "15");
            builtinSymbols.put("SCREEN", "16384");
            builtinSymbols.put("KBD", "24576");
            builtinSymbols.put("SP", "0");
            builtinSymbols.put("ARG", "1");
            builtinSymbols.put("LCL", "2");
            builtinSymbols.put("THIS", "3");
            builtinSymbols.put("THAT", "4");
            Map<String, String> comp = new HashMap<>(); // Hashmap creation(Dictionary in python)
            comp.put("", "0000000");
            comp.put("0", "0101010");
            comp.put("1", "0111111");
            comp.put("-1", "0111010");
            comp.put("D", "0001100");
            comp.put("A", "0110000");
            comp.put("!D", "0001101");
            comp.put("!A", "0110001");
            comp.put("-D", "0001111");
            comp.put("-A", "0110011");
            comp.put("D+1", "0011111");
            comp.put("A+1", "0110111");
            comp.put("D-1", "0001110");
            comp.put("A-1", "0110010");
            comp.put("D+A", "0000010");
            comp.put("D-A", "0010011");
            comp.put("A-D", "0000111");
            comp.put("D&A", "0000000");
            comp.put("D|A", "0010101");
            comp.put("M", "1110000");
            comp.put("!M", "1110001");
            comp.put("-M", "1110011");
            comp.put("M+1", "1110111");
            comp.put("M-1", "1110010");
            comp.put("D+M", "1000010");
            comp.put("D-M", "1010011");
            comp.put("M-D", "1000111");
            comp.put("D&M", "1000000");
            comp.put("D|M", "1010101");
            Map<String, String> dest = new HashMap<>(); // Hashmap creation(Dictionary in python)
            dest.put("", "000");
            dest.put("null", "000");
            dest.put("M", "001");
            dest.put("D", "010");
            dest.put("MD", "011");
            dest.put("A", "100");
            dest.put("AM", "101");
            dest.put("AD", "110");
            dest.put("AMD", "111");
            Map<String, String> jump = new HashMap<>(); // Hashmap creation(Dictionary in python)
            jump.put("", "000");
            jump.put("null", "000");
            jump.put("JGT", "001");
            jump.put("JEQ", "010");
            jump.put("JGE", "011");
            jump.put("JLT", "100");
            jump.put("JNE", "101");
            jump.put("JLE", "110");
            jump.put("JMP", "111");
            List<String> variables= new ArrayList<String>();
            List<String> labels= new ArrayList<String>();
            File read = new File("Fill.asm");
            Scanner in = new Scanner(read);
            int lineno=0;
            while (in.hasNextLine()) {
                String data = in.nextLine();
                data = data.replaceAll(" ", "");
                data = data.replaceAll("\n", "");
                data = data.replaceAll("\t", "");
                
                if (data.length() > 0 && data.substring(0, 1).equals("/")) {
                    continue;
                } else if (data.length() == 0) {
                    continue;
                } else if (data.contains("/")) {
                    int index = data.indexOf("/");
                    data = data.substring(0, index);
                }
                
                if (data.isEmpty()) {
                    continue;
                }
                
                if (data.startsWith("@")) {
                    String variable = data.substring(1);
                    if (!Character.isDigit(variable.charAt(0))) {
                        if (!variables.contains(variable)) {
                            variables.add(variable);
                        }
                    }
                    lineno++;
                } else if (data.startsWith("(")) {
                    String label = data.substring(1, data.length() - 1);
                    labels.add(label);
                    builtinSymbols.put(label, String.valueOf(lineno));
                }
                else{
                    lineno++;
                }
            }
            for (String variable : variables) {
                if (!labels.contains(variable) && !builtinSymbols.containsKey(variable)) {
                    builtinSymbols.put(variable, String.valueOf(value));
                    value++;
                }
            }
            in.close();
            in = new Scanner(read);
            FileWriter writer = new FileWriter("fill.hack");
            while(in.hasNextLine()) {
                String data = in.nextLine();
                data = data.replaceAll(" ", "");
                data = data.replaceAll("\n", "");
                data = data.replaceAll("\t", "");
                if (data.length() > 0 && data.substring(0, 1).equals("/")) {
                    continue;
                } else if (data.length() == 0)
                    continue;
                else if (data.contains("/"))
                {
                    int index = data.indexOf("/");
                    data = data.substring(0, index);
                }
                if (data.isEmpty()) {
                    continue;
                }
                if (data.startsWith("@") || data.startsWith("(")) {
                    if (data.startsWith("@") && !Character.isDigit(data.charAt(1))) {
                        String variable = data.substring(1);
                        data = builtinSymbols.get(variable);
                    } else if (data.startsWith("(")) {
                        continue;
                    } else {
                        data = data.substring(1);
                    }        
                    int number = Integer.parseInt(data);
                    String binary = Integer.toBinaryString(number);
                    // Pad with leading zeros to ensure 16 bits
                    binary = String.format("%16s", binary).replace(' ', '0');
                    writer.write(binary + '\n');
                }
                else
                {
                    String Dest, Comp, Jump;
                    if (data.contains("=")) {
                        int ind = data.indexOf('=');
                        if (data.contains(";")) {
                            int ind1 = data.indexOf(";");
                            Dest = data.substring(0, ind);
                            Comp = data.substring(ind + 1, ind1);
                            Jump = data.substring(ind1 + 1);
                        } else {
                            Dest = data.substring(0, ind);
                            Comp = data.substring(ind + 1);
                            Jump = "";
                        }
                    } else {
                        if (data.contains(";")) {
                            int i1 = data.indexOf(";");
                            Comp = data.substring(0, i1);
                            Jump = data.substring(i1 + 1);
                            Dest = "";
                        } else {
                            Comp = data;
                            Dest = "";
                            Jump = "";
                        }
                    }
                    String binary = "111" + comp.get(Comp) + dest.get(Dest) + jump.get(Jump);
                    writer.write(binary + "\n");
                    lineno++;
                }
            }
            System.out.println("Succeeded");
            writer.close();
            in.close();
        } catch (FileNotFoundException e) {
        System.out.println("An error occured.");
        e.printStackTrace();
        }  
    }
}