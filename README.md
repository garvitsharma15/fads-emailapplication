## Process to follow

1. run "mvn clean install -DskipTests" in fads-emailapplication folder 

2. steps to create docker image

    2.1 Place dockerfile in fads-emailapplication/ folder
    
    2.2 "docker build . -t {docker_username_name}/{docker_repo_name} ." eg (docker build -t garvitsharma/fads-emailapplication .)
    
    2.3 "docker push {docker_username_name}/{docker_repo_name}" eg (docker push garvitsharma/fads-emailapplication:latest)
    
    2.4 "docker run -p {outside-port}:{container-port} -d {image name}" (sudo docker run -p 8088:8088 -d garvitsharma/fads-emailapplication) inside environment (azure/aws)
    

3. additional commands 

    3.1 list docker containers : docker container ls
    
    3.2 remove docker container : docker rm {container-name/ conatiner-id}
    
    3.3 list docker image : docker image ls
    
    3.4 remove docker image : docker image remove {imagename/image-id}