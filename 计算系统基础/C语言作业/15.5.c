#include<stdio.h>
int f(int);

int main(){
	int n = 1 ;
	int result;
	while(n < 100){
		n=n + 1;
		result = f(n);
		if(result == 1){
			printf("%d\n",n);
		}
	}
}

int f(int n){
	int i=2;
	
	while(i<n){
		if((n % i) == 0){
			return 0;
		}
		else{
			i = i + 1; 
		}
	}
	
	return 1;
}


