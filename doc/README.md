# Configurações de execução:

O projeto foi executado utilizando
    JAVA: jdk-8u281-linux-x64
Disponibilizada oficialmente no site da Oracle
    Wildfly v9.0.0
    MySQL Java Connector v5.1.48 

### Como executar o projeto:

Execute o container docker do MySQL com a seguinte configuração:

```shell
$ docker container run -d \
--name mysqldb \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_ALLOW_EMPTY_PASSWORD=true mysql:latest
```

Instale a versão 9.0.0 do container Wildfly.

Copie a pasta `com` para dentro da pasta `modules/system/layers/base` do container.

Ao configurar dentro do eclipe o container do Wildfly, selecione o `standalone-full.xml` como ponto de start do projeto.