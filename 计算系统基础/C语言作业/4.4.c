#include<stdio.h>

int main(){
	int year;
	int month;
	
	printf("input year:\n");
	scanf("%d",&year);
	
	printf("input month:\n");
	scanf("%d",&month);
	
	if((month==1)||(month==3)||(month==5)||(month==7)||(month==8)||(month==10)||(month==12))
	printf("31 days");
	else if((month==4)||(month==6)||(month==9)||(month==11))
	printf("30 days");
	else if((month==2)){
		if((year%400)==0)
		printf("29 days");
		else if((year%100)==0)
		printf("28 days");
		else if((year%4)==0)
		printf("29 days");
		else
		printf("28 days");
	}
	else
	printf("error");
	
}
