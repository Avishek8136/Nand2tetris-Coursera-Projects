import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Nowhitespaces {
    public static void main(String[] args) throws IOException {
        try { // The IOException is a checked exception that can be thrown by the main method
              // if there is an error reading or writing to an input or output stream
            File read = new File("Fill.asm"); // creating a new instance read to read fill.asm
            Scanner in = new Scanner(read); // creating a new instance in to read from file read.asm
            FileWriter writer = new FileWriter("nowhitespaces.asm"); // creating a new instance writer to write in
                                                                     // nowhitespaces.asm
            while (in.hasNextLine()) { // if there is EOF returns false otherwise
                String data = in.nextLine(); // reading each line
                data = data.replaceAll(" ", "");
                data = data.replaceAll("\n", "");
                data = data.replaceAll("\t", ""); // removing all spaces
                if (data.length() > 0 && data.substring(0, 1).equals("/")) { // if comments are encountered in beginning
                    continue;
                } else if (data.length() == 0) // if the line has no length
                    continue;
                else if (data.contains("/")) // if the line has comment at any position
                {
                    int index = data.indexOf("/"); // read index at which comment starts
                    data = data.substring(0, index); // take the beginning substring before comment starts
                }
                writer.write(data); // write the string saved in data variable to the file nowhitespaces.asm
                writer.write('\n'); // writing newline in the file nowhitespaces.asm
                System.out.println(data);
            }
            writer.close();
            in.close();
        } catch (FileNotFoundException e) { // if file fill.asm is not found
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }
}
