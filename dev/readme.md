# Sistema Megasolution

## Instalar base de datos usando docker

### Pull mysql image
    
    docker pull mysql

### Run a new mysql container

    docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=<ROOT_PASSWORD> -e MYSQL_DATABASE=<megasolution_db> -e MYSQL_USER=admin -e MYSQL_PASSWORD=<MYSQL_USER_PASSWORD> -d mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

### Restore the database

1. Copy the backup sql file into the docker container:

       docker cp <BACKUP_SQL_FILE> mysql:/home/<BACKUP_SQL_FILE>

2. Get a bash shell in the container

       docker exec -it mysql /bin/bash

3. Execute the mysql restore command:

       mysql -u<USER> -p<USER_PASSWORD> megasolution_db < /home/<BACKUP_SQL_FILE>