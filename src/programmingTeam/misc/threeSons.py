# -*- coding: utf-8 -*-
"""
Created on Sat Apr  6 16:01:19 2019

@author: rubes

This is to solve Programming Problem written by myself. The description goes:
	
This problem is based on a classic riddle. Solving it will help you have a 
clear understanding of how to write your program. Here it is:
////////
Two Russian mathematicians meet on a plane. 
“If I remember correctly, you have three sons,” says Ivan. “What are their ages today?”
“The product of their ages is thirty-six,” says Igor, “and the sum of their 
ages is exactly today’s date.”
“I’m sorry, Igor,” Ivan says after a minute, “but that doesn’t tell me the ages
of your boys.”
“Oh, I forgot to tell you, my youngest son has red hair.”
“Ah, now it’s clear,” Ivan says. “I now know exactly how old your three sons 
are.”
How did Ivan figure out the ages?
////////////
You are to write a program that will determine whether it is always possible to
figure out the ages of someone’s sons assuming that their ages add up to 
today’s date and you are given two numbers (1) the number of offspring and 
(2) the product of their ages. Just to be clear, the children aren’t 
necessarily Igor’s, because not everyone has a youngest son with red hair.
 
Input
Each case will be a set of two integers, line by line, with the first integer 
representing the number of sons and the second representing the product of 
their ages. The last line of the input will be two zeroes, I guess. 
In every case, the sum of their ages will not exceed 31.

Output
For each case, output “yes” if it is possible to figure out the ages of the 
sons, and output “no” if it is not.

Thanks to Manuel Salvadores on StackOverflow for making a function to find all
combinations of a list that add up to a given sum. I've changed it to find all
combinations that multiply to a given product.

Thanks to Alex Martelli on Stack Overflow for pointing out how to use itertools
to filter out duplicates in a list of lists.

"""
import itertools

#Generates a huge list of all the *PERMUTATIONS* of the factors of a number 
#that are of the given size and have a sum of less than 31.
#I was too loathe to figure out how to do combinations as opposed to 
#permutations, so I cheesed it by passing in a list of the factors that has 
#duplicates of each number.
def subset_product_gen_combo_filtered(numbers, target, size, partial=[]):
	p = product(partial)
	
	if p == target and len(partial) == size and sum(partial) <= 31:
		yield sorted(partial)
	if p >= target:
		return

	for i in range(len(numbers)):
		n = numbers[i]
		remaining = numbers[i+1:]
		yield from subset_product_gen_combo_filtered(remaining, target, size, partial + [n]) 
		
#Returns the product of a list of numbers.
def product(nums):
	prod = 1
	for i in range(len(nums)):
		prod *= nums[i]
	return prod

#Returns the factors of a given number as a list.
def factors(num):
	factors = []
	for i in range(num):
		if i != 0:
			r = num % i
			if r == 0:
				factors += [i]
	factors += [num]
	return factors
	
if __name__ == "__main__":
	
	line = ""
	while line != "0 0":
		line = input()
		if line == "0 0":
			break
		numStrings = line.split(" ")
		numKids = int(numStrings[0])
		prod = int(numStrings[1])
		if numKids < 3:
			print("yes")
		else:
			n = 3
			while n <= numKids:
				factors1 = factors(prod)
				factors2 = factors(prod)
				for i in range(n - 1):
					factors1 += factors2
				print(factors2)
				lists = []
				lists += subset_product_gen_combo_filtered(factors1,prod,n)
#				print("UNFILTERED", lists)
				lists.sort()
				f = list(lists for lists,_ in itertools.groupby(lists))
				print("FILTERED", f)
				sums = list(map(sum, f))
				print("SUMS", sums)
				if len(sums) != len(set(sums)):
					print("no")
					break
				elif n < numKids:
					n += 1
				elif n == numKids:
					print("yes")
					break
		
#def subset_sum(numbers, target, partial=[]):
#    s = sum(partial)
#
#    # check if the partial sum is equals to target
#    if s == target: 
#        print("sum(%s)=%s" % (partial, target))
#    if s >= target:
#        return  # if we reach the number why bother to continue
#
#    for i in range(len(numbers)):
#        n = numbers[i]
#        remaining = numbers[i+1:]
#        subset_sum(remaining, target, partial + [n]) 
#		
#def subset_product(numbers, target, partial=[]):
#	p = product(partial)
#	# check if the partial sum is equals to target
#	if p == target: 
#		print("prod(%s)=%s" % (partial, target))
#	if p >= target:
#		return  # if we reach the number why bother to continue
#
#	for i in range(len(numbers)):
#		n = numbers[i]
#		remaining = numbers[i+1:]
#		subset_product(remaining, target, partial + [n]) 
#		
#def subset_product_gen(numbers, target, partial=[]):
#	p = product(partial)
#	# check if the partial sum is equals to target
#	if p == target: 
#		yield partial
#	if p >= target:
#		return  # if we reach the number why bother to continue
#
#	for i in range(len(numbers)):
#		n = numbers[i]
#		remaining = numbers[i+1:]
#		yield from subset_product_gen(remaining, target, partial + [n])