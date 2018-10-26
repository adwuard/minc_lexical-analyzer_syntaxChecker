yourOutputFiles=["f1.txt","f2.txt","f3.txt"]
TargetFiles=["output_fail_01.minc", "output_fail_01b.minc", "output_fail_02.minc"]

for i in range(0,0):
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
	print("===================================End=================================")
	print("\n\n")
	



yourSucessOutputFiles=["s1.txt","s2.txt","s3.txt","s4.txt","s5.txt","s6.txt","s7.txt","s8.txt","s9.txt","s10.txt","s11.txt","s12.txt",
						"s13.txt","s14.txt","s15.txt","s16.txt"]
SucessTargetFiles=["output_succ_01.minc", "output_succ_02.minc", "output_succ_03.minc","output_succ_04.minc", "output_succ_05.minc", "output_succ_06.minc",
"output_succ_07.minc", "output_succ_08.minc", "output_succ_09.minc","output_succ_10.minc", "output_succ_11.minc", "output_succ_12.minc",
"output_succ_13.minc", "output_succ_14.minc", "output_succ_15.minc","output_succ_16.minc"]
	
for i in range(0,7):
	f1=open("Test_Files/"+ yourSucessOutputFiles[i])
	f2=open("Test_Files/"+ SucessTargetFiles[i])
	
	print("======================================================================")
	print("File: "+ yourSucessOutputFiles[i]+ " Comparing To: " + SucessTargetFiles[i])
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
	print("===================================End=================================")
	print("\n\n")