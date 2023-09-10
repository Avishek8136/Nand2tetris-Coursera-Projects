import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class symbolsandvariables{
    public static void main(String[] args) throws IOException       
    {
        try{                                                                                   // The IOException is a checked exception that can be thrown by the main method if there is an error reading or writing to an input or output stream
            File read=new File("nowhitespaces.asm");                                  // creating a new instance read to read nowhitespaces.asm 
            Scanner in=new Scanner(read);  
            List<String> variables= new ArrayList<String>();                                                    // creating a new instance in to read from file nowhitespaces.asm
            FileWriter writer=new FileWriter("symbolandvariables.asm");               // creating a new instance writer to write in symbolandvariables.asm
            while (in.hasNextLine()) {                                                         // if there is EOF returns false otherwise
                String data = in.nextLine();                                                   // reading each line
                
                if(data.charAt(0)=='@')
                {
                    int f= (int)(data.charAt(1));                                        // as variable name starts with a char so checking whether first char is a char or not
                    if(((f>64)&&(f<91))||((f>96)&&(f<123)))
                    {
                        if(!variables.contains(data.substring(1))){
                            variables.add(data.substring(1));
                            writer.write(data.substring(1));                            // write to the file from index 1 removing @                                   
                            writer.write('\n');
                            System.out.println(data);
                        }
                    }
                }
              }
            writer.close();
            in.close();
        } catch(FileNotFoundException e){                                                      // if file nowhitespaces.asm is not found
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}
