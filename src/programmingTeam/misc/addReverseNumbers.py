for x in range(int(input())):

    input_nums = input().split()

    print(str(int(str(input_nums[0])[::-1]) + int(str(input_nums[1])[::-1]))[::-1].lstrip("0"))
    
