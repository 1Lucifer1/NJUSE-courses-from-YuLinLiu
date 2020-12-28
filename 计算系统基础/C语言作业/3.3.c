#include<stdio.h>

int main(){
	int number1;
	int number2;
	char result;
	 
	printf("input two numbers\n");
	printf("number1:");
	scanf("%d",&number1);
	
	printf("number2:");
	scanf("%d",&number2);
	
	result = (number1>number2)?'1':'2';
	printf("%c",result);
	
}
