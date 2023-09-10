#include<stdio.h>
#include<string.h>
int main()
{
	char b[100],c[100];
	FILE *fp,*file; //filepointer
	fp=fopen("whitespace.asm","r");
	file=fopen("symbolsandvariables.asm","w");
	while( fgets ( b, 100, fp ) != NULL )//CAN ALSO USE EOF(END OF FILE)
     {
		 if(b[0]=='(')
		 {
			 fputs(b,file);
		 }
		 else if(b[0]=='@')
		 {
			int ascii=b[1];
			if(((ascii>64)&&(ascii<91))||((ascii>96)&&(ascii<123)))
			{
				for(int i=1;i<strlen(b);i++)
				{
					c[i-1]=b[i];
					c[i+1]='\0';
				}
				fputs(c,file);
			}
		 }
	 }
	 fclose(fp);
     fclose(file);
	 printf("Data successfully written in file symbolandvariables.asm\n");
     printf("The file is now closed.") ;
     return 0;
}
