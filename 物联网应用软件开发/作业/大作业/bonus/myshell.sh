#!/bin/bash
echo `pwd` >> /home/HwHiAiUser/ydllalala ;
cd /home/HwHiAiUser/ascendcamera/out ;
echo `pwd` >> ./ydllueluelue ;
source /etc/profile;
n=0
while :
do
        ./main -i -c 1 -o ./output/peace.jpg --overwrite > log.txt ;
        # n=$((n+1))
	scp /home/HwHiAiUser/ascendcamera/out/output/peace.jpg yu-lin@192.168.1.166:~ ;
	sleep 5
done
