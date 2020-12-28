#include<stdio.h>

int main(){
	int time;
	int second;
	int minute;
	int hour;
	
	printf("input time with seconds: \n");
	scanf("%d",&time);
	hour = time / 3600;
	time = time - hour * 3600;
	minute = time / 60;
	time = time - minute * 60;
	second = time;
	
	printf("%d hours %d minutes %d seconds",hour,minute,second);
	return 0;
	
}
