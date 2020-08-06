#include<stdio.h>

int main(){
	int input,i,j=1;
	
	printf("input a number:\n");
	scanf("%d",&input);
	
	for(i=1;i<31;i++){
		j=j*2;
	}
	
	if(input>=0){
		printf("0");
	}
	else{
		input=input*(-1);
		printf("1");
	}
	
	for(i=1;i<32;i++){
		if((input&j)>0){
			printf("1");
		}
		else{
			printf("0");
		}
		j=j>>1;
	}
}
