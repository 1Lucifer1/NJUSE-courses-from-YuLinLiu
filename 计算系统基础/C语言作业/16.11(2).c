#include<stdio.h>
void f(int a[3][4],int b[4][3]);

int main(){
	int a[3][4];
	int b[4][3];
	int i;
	int j;
	
	for(i=0;i<3;i++){
		for(j=0;j<4;j++){
			printf("input number[%d][%d]:",i+1,j+1);
			scanf("%d",&a[i][j]);
		}
	}
	
	f(a,b);
	
	for(i=0;i<4;i++){
		for(j=0;j<3;j++){
			printf("%d ",b[i][j]);
		}
		printf("\n");
	}
}

void f(int a[3][4],int b[4][3]){
	int i;
	int j;
	for(i=0;i<4;i++){
		for(j=0;j<3;j++){
			b[i][j]=a[j][i];
		}
	}
	
}
