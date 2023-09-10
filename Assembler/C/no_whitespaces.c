#include<stdio.h>
#include<string.h>
int main()
{
	char b[100],c[100];
	FILE *fp,*file; //filepointer
	fp=fopen("b.asm","r");
	file=fopen("whitespace.asm","w");
	while( fgets ( b, 100, fp ) != NULL )//CAN ALSO USE EOF(END OF FILE)
     {
		int d=0,f=0;
		if((strcmp(" ",b)==0)||(strcmp("\n",b)==0)||(strcmp(b,"\t")==0))
			continue;
        else if((b[0]=='/')&&(b[1]=='/'))
			continue;
		else
		{
			for(int i=0;i<strlen(b);i++)
			{
				if(b[i]=='/')
				{
					f=1;
					d++;
				}
				else
				{ 
					if(b[i]!=' ')
					{
						if(f!=1)
						{
							c[d]=b[i];
							d++;
						}
					}
				}
			}
			c[d]='\0';
			fputs(c,file);
		}
     }
     fclose(fp);
     fclose(file);
     printf("Data successfully written in file whitespaces.asm\n");
     printf("The file is now closed.") ;
     return 0;
}

