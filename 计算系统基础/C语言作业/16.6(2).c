#include<stdio.h>
void StrCpy(char*,char*);
int main(){
		char firstStr[20];
	char secondStr[20];
	
	printf("input string1:");
	scanf("%s",firstStr);
	printf("input string1:");
	scanf("%s",secondStr);
	
	StrCpy(firstStr,secondStr);
	
	printf("%s",firstStr);
}

void StrCpy(char*firstStr,char*secondStr){
	int i;
	for(i=0;firstStr[i]!='\0';i++){
		firstStr[i]=secondStr[i];
	}
}
