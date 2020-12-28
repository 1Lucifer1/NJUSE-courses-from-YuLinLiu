#include<stdio.h>
void f(int,int*,int);
int main(){
	int x;
	int a[32];
	int i;
	int cnt=3;
	
	printf("input a munber:\n");
	scanf("%d",&x);
	for(i=0;i<32;i++){
		a[i]=0;
	}
	
	i=x;
	while(i>1){
		i=i/2;
		cnt++;
	}
	if(x<0){
		cnt=32;
	}
    f(x,a,cnt);
	
	for(i=0;i<32;i++){
		printf("%d",a[i]);
	}
}

void f(int x,int a[32],int cnt){
	int i=1;
	int j;
	if(cnt==0){
			return 0;
		}else {
			for(j=1;j<cnt;j++){
				i=i*2;
			}
			if((x&i)>0){
			    a[32-cnt]=1;
			}
			cnt--;
			f(x,a,cnt);
		}
}
