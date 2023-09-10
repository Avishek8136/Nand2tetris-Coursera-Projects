#include<stdio.h>

int main()
{
	char b[50];
	FILE *fp; //filepointer
	fp=fopen("a.asm","r");
	while( fgets ( b, 50, fp ) != NULL )
        {
         
            // Print the dataToBeRead
            printf( "%s" , b ) ;
        }
}
