#!/bin/bash
clear
echo compile jave files......
javac *.java
echo done compiling No error reported


echo processing and outputing Fail files.....
java Program  Test_Files/fail_01.minc 	> Test_Files/f1.txt
java Program  Test_Files/fail_01b.minc 	> Test_Files/f1b.txt
#java Program  Test_Files/fail_02.minc 	> Test_Files/f2.txt
#java Program  Test_Files/fail_03.minc 	> Test_Files/f3.txt
#java Program  Test_Files/fail_03b.minc > Test_Files/f3b.txt
#java Program  Test_Files/fail_04.minc 	> Test_Files/f4.txt
#java Program  Test_Files/fail_05.minc 	> Test_Files/f5.txt
#java Program  Test_Files/fail_06.minc 	> Test_Files/f6.txt
#java Program  Test_Files/fail_06b.minc > Test_Files/f6b.txt
#java Program  Test_Files/fail_07.minc 	> Test_Files/f7.txt
#java Program  Test_Files/fail_08.minc 	> Test_Files/f8.txt
#java Program  Test_Files/fail_08b.minc > Test_Files/f8b.txt
#java Program  Test_Files/fail_09.minc 	> Test_Files/f9.txt
#java Program  Test_Files/fail_09b.minc > Test_Files/f9b.txt
#java Program  Test_Files/fail_10.minc 	> Test_Files/f10.txt
#java Program  Test_Files/fail_11.minc 	> Test_Files/f11.txt
#java Program  Test_Files/fail_12.minc 	> Test_Files/f12.txt
#java Program  Test_Files/fail_13.minc 	> Test_Files/f13.txt
#java Program  Test_Files/fail_14.minc 	> Test_Files/f14.txt
#java Program  Test_Files/fail_14b.minc > Test_Files/f14b.txt
#java Program  Test_Files/fail_15.minc 	> Test_Files/f15.txt
#java Program  Test_Files/fail_15b.minc > Test_Files/f15b.txt

echo processing and outputing Success files
java Program  Test_Files/succ_01.minc 	> Test_Files/s1.txt
java Program  Test_Files/succ_02.minc 	> Test_Files/s2.txt
java Program  Test_Files/succ_03.minc 	> Test_Files/s3.txt
java Program  Test_Files/succ_04.minc 	> Test_Files/s4.txt
java Program  Test_Files/succ_05.minc 	> Test_Files/s5.txt
java Program  Test_Files/succ_06.minc 	> Test_Files/s6.txt
java Program  Test_Files/succ_07.minc 	> Test_Files/s7.txt
#java Program  Test_Files/succ_08.minc 	> Test_Files/s8.txt
#java Program  Test_Files/succ_09.minc 	> Test_Files/s9.txt
#java Program  Test_Files/succ_10.minc 	> Test_Files/s10.txt
#java Program  Test_Files/succ_11.minc 	> Test_Files/s11.txt
#java Program  Test_Files/succ_12.minc 	> Test_Files/s12.txt
#java Program  Test_Files/succ_13.minc 	> Test_Files/s13.txt
#java Program  Test_Files/succ_14.minc 	> Test_Files/s14.txt
#java Program  Test_Files/succ_15.minc 	> Test_Files/s15.txt
#java Program  Test_Files/succ_16.minc 	> Test_Files/s16.txt

echo Done

python Test_Files/filesTester.py







