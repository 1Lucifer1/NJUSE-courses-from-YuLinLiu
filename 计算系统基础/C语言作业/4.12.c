#include<stdio.h>

int main(){
	int n;
	int x = 1;
	double i;
	double result;

	printf("input a n to calculate £k:\n");
	scanf("%d",&n);
	
	while(x <= n){
		if((x%2) == 0){
			i = 4.0/(x*2-1);
			result = result - i;
			x = x+1;
		}
		else if((x%2) == 1){
			i = 4.0/(x*2-1);
			result = result + i;
			x = x+1;
		}
	}
	printf("%f",result);
}
