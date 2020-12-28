#include<stdio.h>

int main(){
	int number1;
	int number2;
	 
	printf("input two numbers\n");
	printf("number1:");
	scanf("%d",&number1);
	
	printf("number2:");
	scanf("%d",&number2);
	
	if(number1>number2)
	printf("number1");
	else if(number1==number2)
	printf("equal");
	else
	printf("number2");
	return 0;
	
}
