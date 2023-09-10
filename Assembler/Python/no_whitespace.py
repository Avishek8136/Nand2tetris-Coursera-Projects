import textwrap
text=""
file=open("fill.asm","r")
for line in file.readlines():
    if "//" in line:
        continue
    elif (line==" "or line=="\n" or line=="\t"):
        continue
    else:    
        text+=textwrap.dedent(line)
file.close()
file=open("_no_whitespaces.asm","w")
for i in text:
    file.write(i)
file.close()