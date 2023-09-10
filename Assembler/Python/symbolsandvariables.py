import re
variable=re.compile(r'@\D+')
text=""
file=open("_no_whitespaces.asm","r")
for line in file.readlines():
    ob=variable.search(line)
    if(ob==None):
        if(line.startswith("(")):
            text+=line
    else:
        text+=ob.group().strip("@")
    
file.close()
file=open("symbolsandvariables.asm","w")
for i in text:
    file.write(i)
file.close()
