previous: INT = 0
n: INT = 12
current: INT = 1
index: INT = 1
while index <= n do
print(current)
temp = current
current = current + previous
previous = temp
index += 1
break


