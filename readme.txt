rabbitmq running in docker.  Sender & Receiver send/receive message from host machine (outside docker)
https://levelup.gitconnected.com/rabbitmq-with-docker-on-windows-in-30-minutes-172e88bb0808
https://www.youtube.com/watch?v=6lPK_LgTZ9Y

C:\Temp\docker-practice\docker-rabbitmq>docker pull rabbitmq:3-management
with ui plugin
C:\Temp\docker-practice\docker-rabbitmq>docker run -d -p 5672:5672 -p 15672:15672  --name my-rabbitMQ rabbitmq:3-management

C:\Temp\docker-practice\docker-rabbitmq>docker ps
CONTAINER ID        IMAGE                   COMMAND                  CREATED
         STATUS              PORTS
                                          NAMES
9b95dfee0f43        rabbitmq:3-management   "docker-entrypoint.s"   2 hours ago
        Up 2 hours          4369/tcp, 5671/tcp, 0.0.0.0:5672->5672/tcp, 15671/tc
p, 25672/tcp, 0.0.0.0:15672->15672/tcp   my-rabbitMQ

dockerdev host name in etc/host modified to add entry for docker-engine ip (from docker quickstart console)
localhost does not work in windows 7
ui available: http://dockerdev:15672/


C:\Users\sdass>docker logs my-rabbitMQ


C:\Temp\docker-practice\docker-rabbitmq> mvn archetype:generate -DgroupId=com.subra -DartifactId=docker-rabbitmq -DarchetypeArtifactId=maven-archetype-quickstar
t -DarchetypeVersion=1.4 -DinteractiveMode=false

add rabbimq client maven dependency and sl4j impl dependency

