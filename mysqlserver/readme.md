This is all of the code for creating the db and the helper methods for the rest of the code to interact with the db
COMMANDS MUST BE DONE USING LINUX (I used ubuntu)
The Dockerfile in here is for the db stuff. Works with the following commands (just for db):

//at this level
docker build -t <name_of_image> .
docker run -e MYSQL_ROOT_PASSWORD=cs506 -p 63306:3306 -it -d --name=<name_of_container> <name_of_image>

//to prove it works
docker exec -it <name_of_container> /bin/sh
mysql -u root -pcs506
//not, yes you need to run it twice for some reason.  Ignore the error unless it pops up on the second attempt
mysql -u root -pcs506
USE warhammerdb
show tables

For those who want to copy and paste:
docker build -t mysql_image .
docker run -e MYSQL_ROOT_PASSWORD=cs506 -p 63306:3306 -it -d --name=mysql_docker mysql_image

docker exec -it mysql_docker /bin/sh
mysql -u root -pcs506
mysql -u root -pcs506
USE warhammerdb
show tables;