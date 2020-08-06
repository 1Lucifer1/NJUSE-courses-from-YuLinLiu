#include<stdio.h>

int main(){
	int i = 1;
	int result = 1;
	while(i <= 9){
		result = result * i;
		i = i + 1;
	}
	printf("%d",result);
}
