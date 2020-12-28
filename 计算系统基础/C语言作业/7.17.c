#include<stdio.h>

int main() {
	char nextletter;
	int i = 0;
	int in = 0;
	int count = 0;

	printf("enter a sentence:\n");

	do {
		scanf("%c", &nextletter);

		if (nextletter == 'i') {
			if (i == 0) {
				i = 1;
				in = 0;
			
			}
		
		}
		else if (nextletter == 'n') {
			if (i == 1) {
			    i =0;
				in = 1;
			
			}
			else if(in == 1){
				i = 0;
				in = 0;
			}
		}
		else if (nextletter == 't') {
			if (in == 1) {
				count = count + 1;
				i = 0;
				in = 0;
			
			}
		}
		else {
			i = 0;
			in = 0;
		}
	} while (nextletter != '\n');

	printf("there are %d 'int' in this sentence.", count);
}
