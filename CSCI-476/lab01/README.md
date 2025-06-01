<p align="center">
    Lab 01 - Environment Variables & Set-UID Programs <br/>
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
The learning objective of this lab is for students to understand how environment variables affect program and system behaviors.

Started: February 5, 2021
</b>
Last Updated: February 6, 2021
</b>
Due Date: February 9. 2021

<a name="struct"></a>
# File Structure
- lab01
	- README.md

<a name="tasks"></a>
# Task 1: Manipulating Environment Variables
In this task, we study the commands that can be used to set and unset environment variables.

## Task 1(a)
Using ```printenv``` or ```env``` it is possible to see/output the environment variables.
</b>

##  Task 1(b)
Using ```export``` it is possible to change some of the  environmental variables such as ```export HOME=/home/dees``` then ```printenv``` will return the new home directory as ```HOME=/home/dees```. Although export does not create new users it will set the env variable to the input.

# Task 2: Passing Environment Variables (Parent -> Child)
In this task, we study how a child process gets its environment variables from its parent. In this task, we would like to know whether the parent’s environment variables are inherited by the child process or not.

## Task 2(a)
This task asked us to run the myprintenv.c and examine the output. The output appears to be the exact same in the myenv1 file is the exact same as when the bash command ```printenv``` is entered. The output is the env variables from the forked child process.
## Task 2(b)
This task is similar to task 2(a) except we are asked to output the parent process env variables. It appears that they will match myenv1 that we examined in the above task 2(a).
## Task 2(c)
After compiling and running the program ```myprintenv.c``` as both the child process and the parent process there appears to be no distinct differences. To confirm my initial oberservation, running ```diff myenv1 myenv2``` returned no difference between the two files. 
</br>
This is logical as when calling ```fork()``` the child process gets seperate but exact copies of all data(variables, values, and stack) from the parent process. Although this child process is an exact duplicate, it recieves a unique process ID and a parent proccess ID that is the same as parent's proccess ID.   



# Task 3:  Environment Variables and ```execve()```
Study how environment variables are affected when a new program is executed via ```execve()```. We are interested in what happens to the environment variables; are they automatically inherited by the new program?
```c
#include <unistd.h>
 
extern char **environ;
 
int main()
{
    char *argv[2];
    argv[0] = "/usr/bin/env";
    argv[1] = NULL;
 
    execve(argv[0], argv, NULL);
 
    return 0;
}
```
The output from compiling and running the ```myenv_execve.c``` (code block above) is return of task 3(a)
## Task 3(a)
Using the supplied code run below program
</b>
```c
// Compile:
//  $ gcc myenv_execve.c -o myenv_execve

#include <unistd.h>

extern char **environ;

int main()
{
    char *argv[2];
    argv[0] = "/usr/bin/env";
    argv[1] = NULL;

    execve(argv[0], argv, NULL);

    return 0;
}
```
Nothing is outputted or printed as there are currently no specified/active processes.
## Task 3(b)
Change the invocation of ```execve()``` in to the following and describe observation.
```execve(argv[0], argv, environ);```
Replacing NULL with environ is letting ```execve()``` know that it should be printing out the environment variables.

## Task 3(c) 
The new program gets it environment variable from the seed environment which is the user that is running the ```myenv_execve```. I am thinking that this is related to the ```fork()``` operation that is completed when calling the program and when ```fork()``` is called the parent and child have the similar environment variables.  


# Task 4:  ```Environment Variables``` and ```system()```
Study how environment variables are affected by ```system()```.This function is used to execute a command, but unlike ```execve()```, which directly executes a command, ```system()``` actually executes ```/bin/sh -c``` command, i.e., it executes ```/bin/sh```, and asks the shell to execute command.
</b>
It is confirmed that using ```system()```, the environment variables of the calling process are passed to the new program ```/bin/sh```. Please compile and run the following program to verify this.


Compile and Run:
```c
// Compile:
//  $ gcc myenv_system.c -o myenv_system
 
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
 
int main()
{
    system("/usr/bin/env");
    return 0;
}
```

# Task 5: Environment Variables and Set-UID Programs
When a Set-UID program runs, it assumes the owner’s privileges. For example, if the program’s owner is root, when anyone runs this program, the program gains root’s privileges during its execution.  Although the behavior of Set-UID programs is decided by their program logic, not by users, users can indeed affect the behavior via environment variables. To understand how Set-UID programs can be affected, let us first figure out whether environment variables are inherited by a Set-UID program’s process from the user’s process.

##  Task 5(a)
the following program that can print out all the environment variables in the current process. 
```c
// Print environment variables using environ.
//
// Compile:
//  $ gcc myenv_environ.c -o myenv_environ
 
#include <stdio.h>
 
extern char **environ;
 
int main(int argc, char *argv[], char* envp[]) {
    int i = 0;
    while (environ[i] != NULL) {
        printf("%s\n", environ[i]);
        i++;
    }
    return 0;
}
```

Verify that your implementation correctly prints the environment variables.
Using the ```diff myenv5_1 myenv5_2``` used above with ```./myenv_environ > myenv5_1``` and ```printenv > myenv5_2``` it can be found that the only difference is the the process directory and as we are only printing environment variables that havent changed the above code is correct.

##  Task 5(b)
Compile the above program, change its ownership to root, and make it a Set-UID program.
```
$ sudo chown root myenv_environ   # chown = (ch)ange (own)er
$ sudo chmod 4755 myenv_environ   # chmod = (ch)ange file (mod)e bits
```

##  Task 5(c)
Using the ```export PATH=./LD_LIBRARY_PATH/TASK5:$PATH``` command to prepend the current directory ```./```, ```LD_LIBRARY_PATH``` and lastly ```TASK5``` it is possible to change the environmental variable path  to ```./LD_LIBRARY_PATH/TASK5:./LD_LIBRARY_PATH/TASK5:./LD_LIBRARY_PATH/TASK5:./:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:.``` compared to ```PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:.```

 
# Task 6: ```PATH``` and Set-UID Programs

In this task, we study how Set-UID programs deal with environment variables. Specifically, we examine the ```PATH``` environment variable and its potential impact on Set-UID programs. One concern is that the actual behavior of the shell program can be affected by environment variables, such as ```PATH```; these environment variables are provided by the user, who may be malicious. By changing these variables, malicious users can potentially control the behavior of the Set-UID program.
</b>

```c
#include <stdlib.h>
 
int main()
{
    system("ls");
}
```

After changing owner to root and making above program a Set-UID program. I was capable of executing a Set-UID with root privledges as you can see below in the image. This is extremely interesting, by changing ```export PATH=/home/seed:$PATH``` to ```export PATH=.:$PATH``` it is possible to run the program written and compiled as ls in the cwd compared to listing the files.  

# Task 7: ```LD_PRELOAD``` and Set-UID Programs
In this task, we study how Set-UID programs deal with environment variables.Specifically, we examine the LD_PRELOAD environment variable and its potential impact on Set-UID programs. Several environment variables, including LD_PRELOAD, LD_LIBRARY_PATH, and others with the LD_ prefix influence the behavior of the dynamic linker/loader. A dynamic linker/loader is the part of an operating system (OS) that loads an executable from persistent storage to RAM, and links the shared libraries needed by the executable at run time.


## Task 7(a)
First, we will see how these environment variables influence the behavior of the dynamic linker/loader when running a normal program. Please follow these steps:
</b>

Nothing to comment on

## Task 7(b)
After you have done Task 7.1, run myprog under the following conditions, and observe what happens.

- ```sleep_prog``` a regular program, and run it as a normal user.
	- ```sleep()``` is bypassed and ```I am not sleeping!``` is printed
- ```sleep_prog``` a Set-UID root program, and run it as a normal user. 
	- Sleeps for one second 
- ```sleep_prog``` a Set-UID root program, export the LD_PRELOAD environment variable again in the root account and run it.
	- ```sleep()``` is bypassed and ```I am not sleeping!``` is printed 
- ```sleep_prog``` a Set-UID user1 program (i.e., the owner is user1, which is another user account), export the ```LD_PRELOAD``` environment variable again in a different user’s account (not-root user) and run it. (NOTE: You may create a new, non-root user for this part)
	- ```./sleep_prog``` permission denied

## Task 7(c)


# Task 8:  ```system()``` versus ```execve()```
Consider the following scenario. Bob works for an auditing agency, and he needs to investigate a company for a suspected fraud. For the purpose of the investigation, Bob needs to be able to read all the files in the company’s Unix system. To protect the integrity of the system, however, Bob should not be able to modify any files. To achieve this goal, Vince, the superuser of the system, wrote a special root-owned Set-UID program (see below), and then gave “execute” permissions to Bob. This program requires Bob to type a file name at the command line, and then it will run ```/bin/cat``` to display the specified file. Since the program is running as a root, it can display any file Bob specifies. However, since the ```cat``` program does not support write operations, Vince is confident that Bob cannot use this special audit program to modify any files.

```c
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
 
int main(int argc, char *argv[])
{
    char *v[3];
 
    if (argc < 2) {
        printf("Audit! Please type a file name.\n");
        return 1;
    }
 
    v[0] = "/bin/cat"; v[1] = argv[1]; v[2] = 0;
    char *command = malloc(strlen(v[0]) + strlen(v[1]) + 2);
    sprintf(command, "%s %s", v[0], v[1]);
 
    /*
     * Use only one of the following (comment out the other):
     */
 
    system(command);
    // execve(v[0], v, 0);
 
    return 0;
}
```

## Task 8(a)
Compile the above program and make it a root-owned Set-UID program. This program uses ```system()``` to invoke the ```cat``` command. Assuming the role of Bob, can you compromise the integrity of the system? For example, can you remove a file that is not writable to you?
</b>
Using the ```system()``` part of the above c audit program it is possible to pass through more than one argument to the shell created by ```system()```.In order to do this it is as simple as using the terminal command ```./audit "myenv2; sudo vim /etc/shadow"``` . This is effective as you are passing the entirety of ```"myenv2; sudo vim /etc/shadow" to the ```./audit``` program which then calls concats the input and runs it in a shell.   

## Task 8(b)
Using the ```execve(v[0], v, 0)``` this attack no longer works. As using ```execve()``` does not invoke a shell.

# Task 9: Capability Leaking
Compile the following program, change its owner to root, and make it a Set-UID program. Run the program as a normal user, and describe what you observe. Will the file /etc/zzz be modified? Please explain.

```c
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
 
void main()
{
    int fd;
 
    /*
     * Assume that /etc/zzz is an important system file,
     * and it is owned by root with permission 0644.
     * Before running this program, you should create
     * the file /etc/zzz first.
     */
    fd = open("/etc/zzz", O_RDWR | O_APPEND);
    if (fd == -1) {
        printf("Cannot open /etc/zzz\n");
        exit(0);
    }
 
    // Simulate the tasks conducted by the program
    sleep(1);
 
    /*
     * After the task, elevated privileges are no longer needed;
     * it is time to relinquish these privileges!
     * NOTE: getuid() returns the real UID (RUID)
     */
    setuid(getuid());
 
    if (fork()) {  /* parent process */
        close (fd);
        exit(0);
    } else {  /* child process */
 
        /*
         * Now, assume that the child process is compromised, and that
         * malicious attackers have injected the following statements into this process
         */
        write (fd, "Malicious Data\n", 15);
        close (fd);
    }
}
```
Using the above program it is possible as a normal user to modify the /etc/zzz and inject the malicous data that is included in the program above near the end. This is possible as we are still using zsh and we are giving the program above root privledges and never adjusting when leaving root and returning to a normal user. After doing all of these task it is clear that Set-UID is an extremely powerful tool atleast within our practice vm's. Thankfully there are measures in place such as bash recognizing that the effective user ID and the real user ID are different which result in a drop of privledge


<a name="contact"></a>
<a href= "mailto: p.oconnormsu@gmail.com?subject= Lab 01 OConnor"> Click here to send email</a>

