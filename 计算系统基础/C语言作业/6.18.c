#include<stdio.h>

int main(){
	char letter;
	
	printf("input a line of sentence:\n");
	
	
	
	while(letter!='\n'){
	scanf("%c",&letter);
		if(letter!=32){
			printf("%c",letter);
		}
	}
}
