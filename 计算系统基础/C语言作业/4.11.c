#include<stdio.h>

int main(){
	int n;
	int i;
	int j;
	
	printf("input a number:\n");
	scanf("%d",&n);
	
	for(i = 1;i <= n;i = i + 1){
		for(j = 1;j <= i;j = j + 1){
			printf("%d  ",i);
		}
		printf("\n");
	}
}
