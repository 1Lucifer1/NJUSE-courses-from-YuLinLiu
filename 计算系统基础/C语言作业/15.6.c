#include<stdio.h>
char Func(int);
int main(){
	int number1;
	int number2;
	int count1;
	int count2;
	char output1;
	char output2;
	char j;
	int i;
	char output;
	printf("input number1:\n");
	scanf("%d",&number1);
	printf("input number2:\n");
	scanf("%d",&number2);
	
	output1 = Func(number1);
	output2 = Func(number2);
	
    if(number1==7||number1==9){
    	count1 = 4;
	}else if(number1==1||number1==0){
		printf("error");
		return 0;
	}else{
		count1 = 3;
	}
	if(number2==7||number2==9){
    	count2 = 4;
	}else if(number2==1||number2==0){
		printf("error");
		return 0;
	}else{
		count2 = 3;
	}
	
	while(count1!=0){
		i = count2;
		j = output2;
		while(i!=0){
			printf("%c%c,",output1,j);
			i = i-1;
			j = j + 1;
		}
		
		output1 = output1 + 1;
		count1 = count1 - 1;
	}

}

char Func(int x){
	switch(x){
		case 1:
			return 0;
		case 2:
			return 'a';
		case 3:
			return 'd';
		case 4:
			return 'g';
		case 5:
			return 'j';
		case 6:
			return 'm';
		case 7:
			return 'p';		
		case 8:
			return 't';
		case 9:
			return 'w';	
		case 0:
			return 0;
	}
}
