#include<stdio.h>
int f(int,int);
int main(){
	int x;
	int y;
	int result;
	
	printf("input x:\n");
	scanf("%d",&x);
	printf("input y:\n");
	scanf("%d",&y);
	result = f(x,y);
	
	printf("%d",result);
} 

int f(int x,int y){
	int result = 1;
	while(y!=0){
		result=result*x;
		y = y - 1;
	}
	return result;
}
