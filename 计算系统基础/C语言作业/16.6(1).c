#include<stdio.h>
void StrCat(char*,char*);
int main(){
	char firstStr[20];
	char secondStr[20];
	
	printf("input string1:");
	scanf("%s",firstStr);
	printf("input string1:");
	scanf("%s",secondStr);
	
	StrCat(firstStr,secondStr);
	
	printf("%s",firstStr);
}

void StrCat(char firstStr[],char secondStr[]){
	int i=0;
	int k;
	while(firstStr[i]!='\0'){
		i++;
	}
	for(k=0;secondStr[k]!='\0';k++){
		firstStr[i]=secondStr[k];
		i++; 
	}
	firstStr[i+1]='\0';
}
