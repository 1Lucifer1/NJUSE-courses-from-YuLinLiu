#include<stdio.h>

int main(){	
    int n=10;
	int a[n];
	int i;

	

	for(i=0;i<n;i++){
		printf("number%d:",i+1);
		scanf("%d",&a[i]);
	}
	
	int x;
	for(i=0;i<n;i++){
		if(a[i]==0){
			x=i;
			break;
		}
	}
	
	
	for(i=0;i<n;i++){
		int k;
		for(k=i+1;k<n;k++){
			if(a[i]==a[k]){
				a[k]=0;
			}
		}
		if(a[i]!=0){
			printf("%d",a[i]);
		}else{
			if(x==i){
				printf("%d",a[i]);
			}
		}
	}
	
	for(i=0;i<n;i++){
		if(a[i]==0){
			a[i]=a[i+1];
		}
	}
}
	
	


