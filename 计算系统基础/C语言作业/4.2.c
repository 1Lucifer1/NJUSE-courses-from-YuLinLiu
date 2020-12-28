#include<stdio.h>

int main(){
	int number;
	int result;
	
	printf("input a number:\n");
	scanf("%d",&number);
	result = number % 7;
	
	if(result == 0)
	printf("可被整除");
	else
	printf("不可被整除");
	
	return 0;
} 
