#include<stdio.h>
double f(int);
int main(){
	int x;
	double result;
	printf("input x:\n"); 
	scanf("%d",&x);
	result = f(x);
	printf("%f",result);
} 

double f(int x) {
	double y1;
	double y0 = 1.0;
	int n = 100000000;
	
	while(n != 0){
		y1 = ( y0 + x / y0 ) / 2;
		y0 = y1;
		n = n - 1;
	}
	
	return y1;
}
