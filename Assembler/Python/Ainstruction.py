f = open("symboltable.asm","r")
f2 = open("A_instruction.hack","w")
f3 = open("whitespace.asm","r")
lines = f.readlines()
A=[]
B=[]
for i in lines:
    i=i.replace("\n","")
    k=i.split(",")
    A.append(k[0])
    B.append(k[1])
lines2 = f3.readlines()
for i in lines2:
    i=i.replace("\n","")
    i = i.replace(")","")
    p = i[1:]
    if i[0]=='@' and i[1].isnumeric():
        f2.writelines(str(bin(int(i[1:]))[2:]).zfill(16)+'\n')
    for j in range(len(A)):
        if p==A[j]:
            f2.writelines((bin(int(B[j]))[2:]).zfill(16)+'\n')