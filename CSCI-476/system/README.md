**Systems & Software - Towards Security**  <br />
**Patrick O'Connor (v75j556)**  <br />
**p.oconnormsu@gmail.com**  <br />
**CSCI 476** <br />
**30/01/2021** <br />

**Task 1** <br />
Describe how the fork() and exec() sys-calls work.
<br />
The sys-call fork() creates a copy/clone of the calling process. With this two identical yet separate programs are running in memory usually called a parent and child. The sys-call exec() is a shell command that specifies the program it would like along with any parameters. From there the exec("Program(child)") is passed through the OS to have logic applied and is then read into memory.

**Task 2**<br />
How is a program laid out in memory? (Diagram)
<br />
![Diagram of running program laid out in memory ](./runningProgramMemory.png)


**Task 3**<br />

Why do I think the distinction between code and data exists when running an
application?
<br />
Distinguishing between code and data when running an application appears to be a
more secure approach as there is separation from user input to the programmers
code. This separation may allow for a programmer to stop the malicious code in
its track before it can enter the system. Along with this organizing the stack then popping off
items as needed for function calls from text segment allows for a clean analysis of
what process is happening and what is going on in memory.

**Task 4**<br />
With our understanding of how an application is laid out in memory, what do you
think are some of the threats we may need to be concerned about?
<br />
Knowing how an application is laid out in memory, the fact that both stacks and
heaps can be overrun leads me to believe that we can utilize this to edit/create
an unintended return address and reach unintended information through malicious code.
Along with this I wonder if there are some programs out there that have holes in
their trust boundary design. If so escalating your privilege could lead to a serious threat.


**Task 5**<br />
What is a “trust boundary”?
<br />
A trust boundary is the intersection that data travels through to cross Systems,
networks, or to change its level of trust. This can be a weak-point for data
manipulation or data insertion attacks. Depending on your threat model, these
boundaries should be checked for vulnerabilities and utilizing a encryption or authentication may be useful at ensuring secure passages.

**Task 6**<br />
What are the major elements of a stack frame that we discussed in class?
<br />
The major elements of a stack frame that were discussed in class are function
Arguments values, Return Address, Previous Frame Pointer, and lastly Local variables.



**Task 7** <br />
After running probe program in seed VM identify the location of the following in
memory along with any supporting evidence:
  -  main: The location of main is near the bottom of the ./probe output within
  the probe section. To get more specific looking at the permissions only one of
  the memory files has permissions of "r-xp" as main should be readable and executable.
  -  printf: The location of printf can be found using a similar approach in that
  printf is from a shared library so libc-2.31.so and once again like main it is
  the only one that has permissions of "r-xp" as printf should be readable and executable.
  -  argv: Within the ./probe program, argv is found on the stack as it is a
  function call and function calls are pushed onto the stack by definition.
  Looking at the address provides extra support to solidify this answer
  -  environ: The environ is located on the stack as it is either the return address
  or previous frame pointer and will be popped off the stack in the end when the processes are done running.


**Task 8** <br />
Describe the stages of compilation that we discussed in class.
<br />
The stages of compilation that was discussed throughout class can be broken into
4 steps not including writing the code and running the outputted executable file.


1.  Preprocessor is invoked to resolve any of the required directives(#includes, #if or #etc.). The preprocessor essentially copies and paste the entirety of the included directives.
2.  After the file has been resolved of all dependencies it is ran through the actual compiler step which is when source code is transformed to assembly.
3.  With assembly code created it is passed to the assembler that must assemble and create machine code/object file.
4.  While machine code is very close to being runnable, there are links that must be made between object file and libraries. An .exe file is outputted.
