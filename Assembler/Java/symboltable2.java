import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class symboltable2 {
    public static void main(String[] args) throws IOException {
        try {
            Map<String, String> builtinSymbols = new HashMap<>(); // Hashmap creation(Dictionary in python)
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
            List<String> variables = new ArrayList<String>();
            List<String> labels = new ArrayList<String>();
            File read = new File("nowhitespaces.asm"); // creating a new instance read to read nowhitespaces.asm
            Scanner in = new Scanner(read); // creating a new instance in to read from file nowhitespaces.asm
            FileWriter writer = new FileWriter("symboltable2.asm"); // creating a new instance writer to write in
                                                                    // symboltable.asm
            int lineno = 0;
            while (in.hasNextLine()) { // if there is EOF returns false otherwise
                String data = in.nextLine();
                lineno++;
                if (data.startsWith("@") && (data.length() >= 3) && (data.charAt(1) == 'R')
                        && (Character.isDigit(data.charAt(2)))) {
                    System.out.println("data");
                    writer.write(data.substring(1) + " : " + data.substring(2) + "\n");
                } else if (data.startsWith("@") && (!(variables.contains(data.substring(1))))
                        && (!(Character.isDigit(data.charAt(1)))))
                // check whether line starts with '@' and isnot in list variable and is not a
                // digit
                {
                    variables.add(data.substring(1)); // add to list variable
                } else if (data.startsWith("(")) // check whether line starts with '('
                {
                    lineno--;
                    labels.add(data.substring(1, data.indexOf(")")));
                    writer.write(data.substring(1, data.indexOf(")")) + " : " + lineno + '\n'); // write in the file
                                                                                                // with line no in
                                                                                                // nowhitespaces.asm
                }
            }
            int value = 15;
            for (String variable : variables) // iliterating values in variable (for each loop)
            {
                if (!(labels.contains(variable))) {
                    if (builtinSymbols.containsKey(variable)) // check whether the builtinsymbols contains the variable
                                                              // or not
                    {
                        writer.write(variable + " : " + builtinSymbols.get(variable) + '\n'); // saving the variable
                                                                                              // with the values
                                                                                              // fetching from the
                                                                                              // builtinsymbols
                    } else {
                        writer.write(variable + " : " + value + '\n'); // saving the variable with the values values
                                                                       // starting from 15
                        value++;
                    }
                }
            }
            in.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}