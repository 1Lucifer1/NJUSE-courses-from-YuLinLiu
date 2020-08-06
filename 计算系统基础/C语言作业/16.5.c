#include<stdio.h>
void f(char a[],int);
int main(){
	int n;
	char a[20];

	printf("input n:");
	scanf("%d",&n);
	printf("input string:");
	scanf("%s",a);
	
	f(a,n);
	
	printf("%s",a);
	
}

void f(char a[],int n){
	int i;
	for(i=0;a[i]!='\0';i++){
		if(a[i]>(126-n)){
			a[i]=a[i]-(94-n);
		}else{
			a[i]=a[i]+n;
		}
	}
}
