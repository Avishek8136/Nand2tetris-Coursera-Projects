import java.util.*;
import java.io.*;
public class OwnVMEmulator{
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
            File read = new File("StaticTest.vm");
            Scanner in = new Scanner(read);
            FileWriter writer = new FileWriter("StaticTest.asm");
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
                        if(data.contains("constant"))
                        {
                            writer.write("@"+value+"\n");
                            writer.write("D=A"+"\n");
                        }
                        else if(data.contains("static"))
                        {
                            if(value>0)
                            {
                                writer.write("@staticLabel."+value+"\n");
                            }
                            else{
                                writer.write("@staticLabel"+"\n");
                            }
                            writer.write("D=M");
                        }else if(data.contains("temp"))
                        {
                            writer.write("@"+seg+"\n");
                            writer.write("D=A+"+value+"\n");
                            writer.write("A=D"+"\n");
                            writer.write("D=M"+"\n");
                        }
                        else{
                            writer.write("@"+seg+"\n");
                            writer.write("D=M"+"\n");
                            if((value>0)&&(!data.contains("pointer")))
                            {
                                writer.write("@"+value+"\n");
                                writer.write("A=A+D"+"\n");
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
                            if(value>0)
                            {
                                writer.write("@staticLabel."+value+"\n");
                            }
                            else{
                                writer.write("@staticLabel"+"\n");
                            }
                        }
                        else if(data.contains("temp"))
                        {
                            writer.write("@R11"+"\n");
                            if(value==0)
                            writer.write("D=A"+"\n");
                            else
                            writer.write("D=A+"+value+"\n");
                        }
                        else if(value>0)
                        {
                            writer.write("@"+seg+"\n");
                            writer.write("A=A+"+value+"\n");
                        }else{
                            writer.write("@"+seg+"\n");}
                        writer.write("M=D"+"\n");
                    }
                }
                else if(data.contains("add")||(data.contains("sub")))
                {
                    writer.write("@SP"+"\n");
                    writer.write("AM=M-1"+"\n");
                    writer.write("D=M"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("AM=M-1"+"\n");
                    if(data.contains("add"))
                    {
                        writer.write("M=D+M"+"\n");
                    }
                    else{
                        writer.write("D=M-D"+"\n");
                        writer.write("@SP"+"\n");
                        writer.write("A=M"+"\n");
                        writer.write("M=D"+"\n");
                    }
                    writer.write("@SP"+"\n");
                    writer.write("M=M+1"+"\n");
                }
                else if(data.contains("and")||(data.contains("or")))
                {
                    writer.write("@SP"+"\n");
                    writer.write("AM=M-1"+"\n");
                    writer.write("D=M"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("AM=M-1"+"\n");
                    if(data.contains("and"))
                    {
                        writer.write("D&M"+"\n");
                    }
                    else{
                        writer.write("D|M"+"\n");
                    }
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
                    writer.write("A=M-1"+"\n");
                    writer.write("M=-M"+"\n");
                }
                else if(data.contains("lt")||data.contains("gt")||data.contains("eq"))
                {
                    writer.write("@SP"+"\n");
                    writer.write("AM=M-1"+"\n");
                    writer.write("D=M"+"\n");
                    writer.write("A=A-1"+"\n");
                    writer.write("D=M-D"+"\n");
                    writer.write("@TRUE"+"\n");
                    if(data.contains("lt"))
                        writer.write("D;JLT"+"\n");
                    else if(data.contains("gt"))
                        writer.write("D;JGT"+"\n");
                    else
                        writer.write("D;JEQ"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M-1"+"\n");
                    writer.write("M=0"+"\n");
                    writer.write("@END"+"\n");
                    writer.write("0;JMP"+"\n");
                    writer.write("(TRUE)"+"\n");
                    writer.write("@SP"+"\n");
                    writer.write("A=M-1"+"\n");
                    writer.write("M=-1"+"\n");
                    writer.write("(END)"+"\n");
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