import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Cinstruction {
    public static void main(String[] args) throws IOException {
        try {
            File read = new File("nowhitespaces.asm");
            Scanner in = new Scanner(read);
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
            dest.put("M", "001");
            dest.put("D", "010");
            dest.put("MD", "011");
            dest.put("A", "100");
            dest.put("AM", "101");
            dest.put("AD", "110");
            dest.put("AMD", "111");
            Map<String, String> jump = new HashMap<>(); // Hashmap creation(Dictionary in python)
            jump.put("", "000");
            jump.put("JGT", "001");
            jump.put("JEQ", "010");
            jump.put("JGE", "011");
            jump.put("JLT", "100");
            jump.put("JNE", "101");
            jump.put("JLE", "110");
            jump.put("JMP", "111");
            FileWriter writer = new FileWriter("Cinstruction.hack");
            while (in.hasNextLine()) {
                String Dest, Comp, Jump;
                String data = in.nextLine();
                data = data.replaceAll("\n", "");
                data = data.replaceAll(" ", "");
                if (((data.startsWith("@")) || ((data.startsWith("("))))) {
                    continue;
                }
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
                System.out.println(Comp + Dest + Jump);
                String binary = "111" + comp.get(Comp) + dest.get(Dest) + jump.get(Jump);
                writer.write(binary + "\n");
            }
            System.out.println("Written the program successfully");
            writer.close();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}