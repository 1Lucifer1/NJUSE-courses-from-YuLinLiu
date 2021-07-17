import os
import time


if __name__ == '__main__':

    for i in range(10):
        for j in range(10):
            print("%d / 10" % i)
            print("%d / 10" % j)
            time.sleep(1)
            os.system('cls')
