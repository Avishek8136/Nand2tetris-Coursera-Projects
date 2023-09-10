import java.util.*;
import java.io.*;
public class VMEmulator{
    public static String extractNumericPart(String input) {
        StringBuilder numericPart = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                numericPart.append(c);
            }
        }
        return numericPart.toString();
    }
    public static void main(String[] args) throws IOException{
        try{
            if (args.length == 0) {
               System.out.println("No filename provided.");
                return ;
            }
            String filename = args[0];
            File read = new File(filename);
            Scanner in = new Scanner(read);
            int label=1;
            FileWriter writer = new FileWriter(filename.substring(0,filename.indexOf("."))+".asm");
            while(in.hasNextLine())
            {
                String data=in.nextLine();
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
                if(data.contains("push")||data.contains("pop"))
                {
                    String numericPart = extractNumericPart(data);
                    int value=Integer.parseInt(numericPart);
                    String seg;
                            if(data.contains("local"))
                            {
                                seg="LCL";
                            }
                            else if(data.contains("argument"))
                            {
                                seg="ARG";
                            }
                            else if(data.contains("this"))
                            {
                                seg="THIS";
                            }
                            else if(data.contains("that"))
                            {
                                seg="THAT";
                            }
                            else if(data.contains("pointer"))
                            {
                                if(value==0)
                                seg="THIS";
                                else
                                seg="THAT";
                            }
                            else {
                                seg="R11";
                            }
                    if(data.contains("push"))
                    {
                        if(data.contains("static"))
                        {
                            writer.write("@"+filename.substring(0,filename.indexOf("."))+"."+value+"\n");
                            writer.write("D=M"+"\n");
                        }
                        else{
                            writer.write("@"+value+"\n");
                            writer.write("D=A"+"\n");
                            if(data.contains("temp"))
                            {
                                writer.write("@5"+"\n");
                                writer.write("AD=D+A"+"\n");
                                writer.write("D=M"+"\n");
                            }
                            else if(!data.contains("constant"))
                            {
                                if(data.contains("pointer")){
                                    writer.write("@3"+"\n");
                                }
                                else{
                                    writer.write("@"+seg+"\n");
                                    writer.write("A=M"+"\n");
                                }
                                writer.write("AD=D+A"+"\n");
                                writer.write("D=M"+"\n");
                            }
                        }
                        writer.write("@SP"+"\n");
                        writer.write("A=M"+"\n");
                        writer.write("M=D"+"\n");
                        writer.write("@SP"+"\n");
                        writer.write("M=M+1"+"\n");
                    }
                    else{
                        writer.write("@SP"+"\n");
                        writer.write("M=M-1"+"\n");
                        if(data.contains("static"))
                        {
                            writer.write("@SP"+"\n");
                            writer.write("A=M"+"\n");
                            writer.write("D=M"+"\n");
                            writer.write("@StaticTest."+value+"\n");
                            writer.write("M=D"+"\n");
                        }
                        else{
                            writer.write("@"+value+"\n");
                            writer.write("D=A"+"\n");
                            if(data.contains("temp"))
                            {
                                writer.write("@5"+"\n");
                            }
                            else if(data.contains("pointer"))
                            {
                                writer.write("@3"+"\n");
                            }
                            else{
                                writer.write("@"+seg+"\n");
                                writer.write("A=M"+"\n");
                            }
                            writer.write("AD=D+A"+"\n");
                            writer.write("@R13"+"\n");
                            writer.write("M=D"+"\n");
                            writer.write("@SP"+"\n");
                            writer.write("A=M"+"\n");
                            writer.write("D=M"+"\n");
                            writer.write("@R13"+"\n");
                            writer.write("A=M"+"\n");
                            writer.write("M=D"+"\n");
                        }
                    }
                }
                else if(data.contains("add")||(data.contains("sub")))
                {
                    writer.write("@SP"+"\n");
                    writer.write("M=M-1"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("D=M"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("M=M-1"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("A=M"+"\n");
                    if(data.contains("add"))
                    {
                        writer.write("D=D+A"+"\n");
                    }
                    else{
                        writer.write("D=A-D"+"\n");
                    }
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("M=D"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("M=M+1"+"\n");
                }
                else if(data.contains("and")||(data.contains("or")))
                {
                    writer.write("@SP"+"\n");
                    writer.write("M=M-1"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("D=M"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("M=M-1"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("A=M"+"\n");
                    if(data.contains("and"))
                    {
                        writer.write("D=D&A"+"\n");
                    }
                    else{
                        writer.write("D=D|A"+"\n");
                    }
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("M=D"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("M=M+1"+"\n");
                }
                else if(data.contains("not"))
                {
                    writer.write("@SP"+"\n");
                    writer.write("A=M-1"+"\n");
                    writer.write("M=!M"+"\n");
                }
                else if(data.contains("neg"))
                {
                    writer.write("@SP"+"\n");
                    writer.write("M=M-1"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("D=M"+"\n");
                    writer.write("D=-D"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("M=D"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("M=M+1"+"\n");
                }
                else if(data.contains("lt")||data.contains("gt")||data.contains("eq"))
                {
                    writer.write("@SP"+"\n");
                    writer.write("M=M-1"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("D=M"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("M=M-1"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("D=A-D"+"\n");
                    writer.write("@LABEL"+label+"\n");
                    label++;
                    if(data.contains("lt"))
                        writer.write("D;JLT"+"\n");
                    else if(data.contains("gt"))
                        writer.write("D;JGT"+"\n");
                    else
                        writer.write("D;JEQ"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("M=0"+"\n");
                    writer.write("@LABEL"+label+"\n");
                    writer.write("0;JMP"+"\n");
                    writer.write("(LABEL"+(label-1)+")"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M"+"\n");
                    writer.write("M=-1"+"\n");
                    writer.write("(LABEL"+label+")"+"\n");
                    label++;
                    writer.write("@SP"+"\n");
                    writer.write("M=M+1"+"\n");
                }
                else if(data.contains("label")){
                    writer.write("("+data.substring(5)+")"+"\n");
                }
                else if(data.contains("if-goto"))
                {
                    writer.write("@"+data.substring(7)+"\n");
                    writer.write("D;JNE"+"\n");
                }
                else if(data.contains("goto"))
                {
                    writer.write("@"+data.substring(4)+"\n");
                    writer.write("0;JMP"+"\n");
                }
            }
            in.close();
            writer.close();
        }catch(FileNotFoundException e)
        {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
}