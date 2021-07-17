#include <stdio.h>

void count(FILE *fp, char * fileName){
    if(fp == NULL){
        printf("wc: Cannot open %s.\n", fileName);
        return;
    }
    int characters, lines, words, state;
    char c;
    state = characters = lines = words = 0;
    while ((c = fgetc(fp)) != EOF) {
        characters++;
        if (c == '\n') {
            lines++;
            state = 0;
            continue;
        } else if (c == ' ') {
            state = 0;
            continue;
        } else if (c == '\t') {
            state = 0;
            continue;
        } else {
            if (state == 0) {
                state = 1;
                words++;
            }
            continue;
        }
    }
    printf("%d characters %d words %d lines %s\n", characters, words, lines, fileName);
}

int main(int argc, char *argv[]) {
    int cnt = 1;
    while(cnt < argc){
        FILE *fp = fopen(argv[cnt],"r");
        count(fp, argv[cnt]);
        cnt++;
    }

}
