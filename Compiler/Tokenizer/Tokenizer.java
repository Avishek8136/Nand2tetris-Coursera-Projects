import java.util.*;
import java.io.*;
public class Tokenizer {
    public static void main(String[] args) throws IOException {
        try{
            String filename;
            if (args.length == 0) {
                System.out.println("Now the code is running with a default file \n please enter the name correctly next time");
                filename="Main.jack";
            }
            else{
                filename = args[0];
            }
            File read = new File(filename);
            Scanner in = new Scanner(read);
            FileWriter writer = new FileWriter(filename.substring(0,filename.indexOf("."))+".xml");
            List<String> keywords = List.of("class", "constructor", "function","field","method","static","char","var","int","boolean","void","true","false","null","this","let","do","if","else","while","return");
            List<String> symbols = List.of("{","}","|","(",")","[","]",".",",",";","+","-","*","/","&","&lt;","&gt","=","~");
            writer.write("<tokens>\n");
            String s=null;
            while(in.hasNextLine()){
            String data=in.nextLine();
                data = data.replaceAll("\n", "");
                data = data.replaceAll("\t", "");
                if (data.length() > 1 && data.substring(0, 2).equals("//")) {
                    continue;
                } else if (data.length() == 0)
                    continue;
                if (data.isEmpty()) {
                    continue;
                }
                String a=data.replaceAll(" ","");
                if(a.startsWith("*"))
                {
                    continue;
                }
                else if(a.startsWith("/"))
                {
                    continue;
                }
                data=data.replaceAll("\\?","ques");
                if(data.contains("\"")){
                    int startIndex = data.indexOf("\""); 
                    int endIndex = data.indexOf("\"", startIndex + 1);
                    s=data.substring(startIndex + 1, endIndex);
                    data=data.replaceAll("\""+s+"\"", " \" ");
                }
                data=data.replaceAll("\\["," \\[ ");
                data=data.replaceAll("\\]"," \\] ");
                data=data.replaceAll("\\{"," \\{ ");
                data=data.replaceAll("\\}"," \\} ");
                data=data.replaceAll("\\("," \\( ");
                data=data.replaceAll("\\)"," \\) ");
                data=data.replaceAll("\\;"," \\; ");
                data=data.replaceAll("\\<"," \\&lt; ");
                data=data.replaceAll("\\>"," \\&gt; ");
                data=data.replaceAll("\\."," \\. ");
                data=data.replaceAll("\\,"," \\, ");
                data=data.replaceAll("\\-"," \\- ");
                String[] tokens = data.split("\\s+"); 
                for (String token: tokens){ 
                    if (token != null && !token.isEmpty()) {
                        if (token.contains("//"))
                        {
                            break;
                        }
                        char firstChar = token.charAt(0);
                        if (firstChar >= '0' && firstChar <= '9') {
                            writer.write("<integerConstant> ");
                            writer.write(token);
                            writer.write(" </integerConstant>\n");
                        }
                        else if(symbols.contains(token)){
                            writer.write("<symbol> ");
                            writer.write(token);
                            writer.write(" </symbol>\n");
                        }
                        else if(keywords.contains(token)){
                            writer.write("<keyword> ");
                            writer.write(token);
                            writer.write(" </keyword>\n");
                        }
                        else if (token.contains("\"")){
                            writer.write("<stringConstant> ");
                            s=s.replaceAll("ques","\\?");
                            writer.write(s);
                            writer.write(" </stringConstant>\n");
                        }
                        else{
                            writer.write("<identifier> ");
                            writer.write(token);
                            writer.write(" </identifier>\n");
                        }
                    }
                }
            }
            writer.write("</tokens>\n");
            in.close();
            writer.close();
            System.out.println("Operation Successful ");
        }catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
    }  
}
