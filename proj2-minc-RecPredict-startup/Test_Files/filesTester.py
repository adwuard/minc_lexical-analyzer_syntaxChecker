# Fail Files Testing
yourOutputFiles=["f1.txt","f1b.txt","f2.txt","f3.txt","f3b.txt","f4.txt","f5.txt","f6.txt","f6b.txt","f7.txt","f8.txt","f8b.txt",
"f9.txt","f9b.txt","f10.txt","f11.txt","f12.txt","f13.txt","f14.txt","f15.txt","f15b.txt"]
TargetFiles=["output_fail_01.minc", "output_fail_01b.minc", "output_fail_02.minc",
"output_fail_03.minc", "output_fail_03b.minc", "output_fail_04.minc",
"output_fail_05.minc", "output_fail_06.minc", "output_fail_06b.minc",
"output_fail_07.minc", "output_fail_08.minc", "output_fail_08b.minc",
"output_fail_09.minc", "output_fail_09b.minc", "output_fail_10.minc",
"output_fail_11.minc", "output_fail_12.minc", "output_fail_13.minc",
"output_fail_14.minc", "output_fail_15.minc", "output_fail_15b.minc"]

for i in range(0,len(TargetFiles)):
	f1=open("Test_Files/"+ yourOutputFiles[i])
	f2=open("Test_Files/"+ TargetFiles[i])
	
	print("======================================================================")
	print("File: "+ yourOutputFiles[i]+ " Comparing To: " + TargetFiles[i])
	print("======================================================================")

	for line1 in f1:
		for line2 in f2:
			if line1!=line2:
				print("Error! Line is different")
				print("Your Output: \t\t"+ line1 +"Target: \t\t"+ line2)
			else:
				print("Same file")	
			break
	f1.close()
	f2.close()
	print("===================================End================================")
	print("\n\n")
	

#Successs Files Testing

yourSucessOutputFiles=["s1.txt","s2.txt","s3.txt","s4.txt","s5.txt","s6.txt","s7.txt","s8.txt","s9.txt","s10.txt","s11.txt","s12.txt",
						"s13.txt","s14.txt","s15.txt","s16.txt"]
SucessTargetFiles=["output_succ_01.minc", "output_succ_02.minc", "output_succ_03.minc","output_succ_04.minc", "output_succ_05.minc", "output_succ_06.minc",
"output_succ_07.minc", "output_succ_08.minc", "output_succ_09.minc","output_succ_10.minc", "output_succ_11.minc", "output_succ_12.minc",
"output_succ_13.minc", "output_succ_14.minc", "output_succ_15.minc","output_succ_16.minc"]
	
for i in range(0,16):
	f1=open("Test_Files/"+ yourSucessOutputFiles[i])
	f2=open("Test_Files/"+ SucessTargetFiles[i])
	
#	print("======================================================================")
	s=("File: "+ yourSucessOutputFiles[i]+ " Comparing To: " + SucessTargetFiles[i])
	for line1 in f1:
		for line2 in f2:
			if line1!=line2:
				print("Error! Line is different")
				print("Your Output: \t\t"+ line1 +"Target: \t\t"+ line2)
			else:
				print(s+" ---> Same file")	
			break
	f1.close()
	f2.close()
#	print("===================================End================================")