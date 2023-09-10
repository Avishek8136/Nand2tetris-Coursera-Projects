import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Ainstruction {
    public static void main(String[] args) throws IOException {
        try{
            File read=new File("nowhitespaces.asm");            
            Scanner in=new Scanner(read);
            List<String> variables= new ArrayList<String>();
            FileWriter writer=new FileWriter("Ainstruction.hack"); 
            while (in.hasNextLine()) {                                                   
                String data = in.nextLine();
                if(data.startsWith("@"))
                {
                    int flag=0;
                    File symbol=new File("symboltable.asm");
                    Scanner scanner=new Scanner(symbol);
                    while(scanner.hasNextLine())
                    {
                        String symbo = scanner.nextLine();
                        data=data.replaceAll(" ", "");
                        symbo=symbo.replaceAll(" ", "");
                        if(data.substring(1).equals(symbo.substring(0,symbo.indexOf(":"))))
                        {
                            int c=symbo.indexOf(":")+1;
                            String val=(symbo.substring(c));
                            variables.add(val);
                            flag++;
                        }
                    }
                    scanner.close();
                    if(flag==0)
                    {
                        variables.add(data.substring(1));
                    }
                }
            }
            for(String variable : variables)                                              // iliterating values in variable (for each loop)
            {
                try {
                    int number = Integer.parseInt(variable);
                    String binary = Integer.toBinaryString(number);
                    // Pad with leading zeros to ensure 16 bits
                    binary = String.format("%16s", binary).replace(' ', '0');
                    writer.write(binary+'\n');
                } catch (NumberFormatException e) {
                    System.out.println(variable);                
                }
            }
            writer.close();
            in.close();
        } catch(FileNotFoundException e)
        {                                            
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}