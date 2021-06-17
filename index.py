# -*- coding: UTF-8 -*-

start = int(input("Inout The Start Number As Qr Code:"))
count = int(input("Inout Total Count:"))
end = start + count
fileName = '%s-%s.txt'%(start,end)

with open(fileName,"w") as file:
    for num in range(start,end):
        print(num)
        file.write('%s\n'%num)
   

