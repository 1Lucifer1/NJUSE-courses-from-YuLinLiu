#include<stdio.h>

int main(){
	int letter;
	
	printf("input a sentence:\n");
	
	
	while(letter!='\n'){
	    scanf("%c",&letter);
		if(letter>=97&&letter<=122){
		letter=letter-32;
		printf("%c",letter);
	}
        else{
		printf("%c",letter);
	}
	}
	
} 
