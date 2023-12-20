FROM tomcat:8.5.87
ADD ./tomcatserver ./frontend/html/* ./frontend/html/partials/* ./frontend/html/fonts/* /usr/local/tomcat/webapps/ROOT/
ADD ./db /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/DB
# ADD ./frontend/helper ./usr/local/tomcat/webapps/js
ADD ./lib /usr/local/tomcat/webapps/ROOT/WEB-INF/lib
ADD ./backend /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/backend

EXPOSE 8080
CMD ["catalina.sh", "run"]