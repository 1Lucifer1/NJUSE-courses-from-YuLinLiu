#include<stdio.h>

int main(){
	int fn1 = 1;
	int fn2 = 1;
	int f = 0;
	int n;
	int i;
	printf("input n to calaulate f(n)=f(n-1)+f(n-2),f(0)=1,f(1)=1:\n");
	scanf("%d",&n);
	
	if(n >= 2){
		for(i = 2;i <= n;i = i + 1){
			f = fn1 + fn2;
			fn2 = fn1;
			fn1 = f;
		    
	}
	printf("f(n)=%d",f);
	}
	
	else if(n == 1 )
	printf("1");
	
	else if(n == 0 )
	printf("1");
	else if(n < 0)
	printf("error");
	
	
} 
