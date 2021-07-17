#include <stdio.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>
#include <pwd.h>
#include <grp.h>
#include <unistd.h>
#include <string.h>
#include <stdlib.h>

int i_flag = 0; // -i – inode 印出每个文件的 inode 号
int l_flag = 0; // -l – 长（long）。列举目录内容的细节，包括权限（模式）、所有者、组群、大小、创建日期、文件是否是到系统其它地方的链接，以及链接的指向。
int r_flag = 0; // -R – 递归（recursive）。该选项递归地列举所有目录（在当前目录之下）的内容。
int a_flag = 0; // -a – 全部（all）。列举目录中的全部文件，包括隐藏文件（.filename）。位于这个列表的起首处的 … 和 . 依次是指父目录和你的当前目录。
int d_flag = 0; // -d - directory 将目录像文件一样显示，而不是显示其下的文件。

/**
 * 输出访问级别
 * @param mode
 * @param str
 */
void mode_to_letters(int mode, char str[]) {
    strcpy(str, "----------");

    if (S_ISDIR(mode)) {
        str[0] = 'd';
    }

    if (S_ISCHR(mode)) {
        str[0] = 'c';
    }

    if (S_ISBLK(mode)) {
        str[0] = 'b';
    }

    if ((mode & S_IRUSR)) {
        str[1] = 'r';
    }

    if ((mode & S_IWUSR)) {
        str[2] = 'w';
    }

    if ((mode & S_IXUSR)) {
        str[3] = 'x';
    }

    if ((mode & S_IRGRP)) {
        str[4] = 'r';
    }

    if ((mode & S_IWGRP)) {
        str[5] = 'w';
    }

    if ((mode & S_IXGRP)) {
        str[6] = 'x';
    }

    if ((mode & S_IROTH)) {
        str[7] = 'r';
    }

    if ((mode & S_IWOTH)) {
        str[8] = 'w';
    }

    if ((mode & S_IXOTH)) {
        str[9] = 'x';
    }
}

/**
 * 转换User Identification格式
 * @param uid
 * @return
 */
char *uid_to_name(uid_t uid) {
    struct passwd *pw_ptr;
    static char num_str[10];

    if ((pw_ptr = getpwuid(uid)) == NULL) {
        sprintf(num_str, "%d", uid);

        return num_str;
    } else {
        return pw_ptr->pw_name;
    }
}

/**
 * 转换Group Identification格式
 * @param uid
 * @return
 */
char *gid_to_name(gid_t gid) {
    struct group *grp_ptr;
    static char num_str[10];

    if ((grp_ptr = getgrgid(gid)) == NULL) {
        sprintf(num_str, "%d", gid);
        return num_str;
    } else {
        return grp_ptr->gr_name;
    }
}

/**
 * 打印
 * @param dirent_pos
 * @param path 文件路径
 */
void output(struct dirent *dirent_pos, char *path) {
    if (i_flag) {
        printf("%20ld ", dirent_pos->d_ino);
    }
    if (l_flag) {
        struct stat info;
        char mode_str[11];
        char *ctime();
        if (stat(path, &info) == -1) {
            perror(path);
        } else {
            mode_to_letters(info.st_mode, mode_str);
            printf("%s ", mode_str);
            printf("%4d ", (int) info.st_nlink);
            printf("%-8s ", uid_to_name(info.st_uid));
            printf("%-8s ", gid_to_name(info.st_gid));
            printf("%8ld ", (long) info.st_size);
            printf("%.14s ", 4 + ctime(&info.st_mtime));
        }
    }
    printf("%s\n", dirent_pos->d_name);
}

/**
 * 路径回退
 * @param path
 * @param len
 */
void rmPath(char *path, int len) {
    memset(path + len, '\0', strlen(path) - len);
}

/**
 * 路径添加
 * @param path
 * @param add
 */
void addPath(char *path, char *add) {
    path[strlen(path)] = '/';
    long len = strlen(path);
    memmove(path + len, add, strlen(add));
}

/**
 * 读取directory中的所有file
 * @param dir_ptr
 * @param path
 */
void readDir(DIR *dir_ptr, char *path) {
    struct dirent *dirent_pos;
    int len = strlen(path);
    DIR * tmp_pos[100];
    char tmp_path[100][100];
    int cnt = 0;
    if (r_flag) printf("Source directory: %s\n", path);
    while ((dirent_pos = readdir(dir_ptr)) != NULL) {

        addPath(path, dirent_pos->d_name);
        if (d_flag) {
            output(dirent_pos, path);
            return;
        }
        if (dirent_pos->d_name[0] == '.' && !a_flag) {
            rmPath(path, len);
            continue;
        }

        output(dirent_pos, path);
        if (r_flag) {
            if(strlen(dirent_pos->d_name) == 1 && dirent_pos->d_name[0] == '.') {
                rmPath(path, len);
                continue;
            }
            if(strlen(dirent_pos->d_name) == 2 && dirent_pos->d_name[0] == '.' && dirent_pos->d_name[1] == '.') {
                rmPath(path, len);
                continue;
            }
            // d_type：8-文件，4-目录
            if (dirent_pos->d_type == 4) {
                DIR *dir_ptr_next;
                if ((dir_ptr_next = opendir(path)) != NULL) {
                    memcpy(tmp_path[cnt], path, strlen(path));
                    tmp_pos[cnt++] = dir_ptr_next;
                }
            }
        }
        rmPath(path, len);
    }
    if(r_flag){
        for(int i = 0; i < cnt;++i){
            readDir(tmp_pos[i], tmp_path[i]);
        }
    }
    closedir(dir_ptr);
}

/**
 * ls操作处理
 * @param dirname
 */
void do_ls(char dirname[]) {
    DIR *dir_ptr;
    char *path = getenv("PWD");

    if ((dir_ptr = opendir(dirname)) == NULL) {
        fprintf(stderr, "ls: Cannot open %s.\n", dirname);
    } else {
        char root[1000];
        memset(root, '\0', sizeof(root));
        strcpy(root, path);
        readDir(dir_ptr, root);
    }
}

int main(int argc, char *argv[]) {
    if (argc != 1){
        char arg;
        // 参数解析
        while ((arg = getopt(argc, argv, "alidR")) != -1) {
            switch (arg) {
                case 'l':
                    l_flag = 1;
                    break;
                case 'a':
                    a_flag = 1;
                    break;
                case 'i':
                    i_flag = 1;
                    break;
                case 'd':
                    d_flag = 1;
                    break;
                case 'R':
                    r_flag = 1;
                    break;
            }
        }
    }
    do_ls(".");
}
