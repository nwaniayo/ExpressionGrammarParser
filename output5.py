previous = 0
n = 12
current = 1
index = 1
while index <= n:
    print(current)
    
    temp = current
    current = current + previous
    previous = temp
    index += 1