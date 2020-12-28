#include<stdio.h>
void f(char a[10][8]);
int main(){
	char a[10][8];
	int i;
	int j=0;

	int k=0;
	int temp;
	int x=1;

	
	for(i=0;i<10;i++){
		printf("input string%d:",i+1);
		scanf("%s",a[i]);
	}	
	

	
	for(i=0;i<10;i++){
		
		for(k=i+1;k<10;k++){
			j=0;
			do{

				if(a[i][j] != a[k][j]){
					x=0;
				}
				j++;
			}while(a[i][j] != '\0'||a[k][j] != '\0');
			
			if(x){
				
				a[k][0]='\0';
				}else{
			    x=1;
				}	
		}
	}

	for(i=0;i<10;i++){
		if(a[i][0]=='\0'){
			for(temp=i;temp<10;temp++){
				if(a[temp][0]!='\0'){
					for(j=0;j<8;j++){
					    a[i][j]=a[temp][j];
					    a[temp][j]='\0';
					}
					break;
				}
					
				}
		}
			
	}
	for(i=0;i<10;i++){
		for(j=0;a[i][j]!='\0';j++){
			printf("%c",a[i][j]);
		}
		printf("\n");
	}

}

