# segtaxi_old


Considerações essenciais:
O projeto em si utiliza a IDE Eclipse em sua versão para JavaEE
A IDE está disponível para download [aqui](https://www.eclipse.org/downloads/).
É importante fazer o download da versão para Enterprise, ou não será possível utilizar as opções de Server, que serão necessárias.

### Portas
Aplicação (Fronetend e Backend): 8080
Portas abertas pelo Wildfly através da reconfiguração: 8080, 9990
Portas recebidas pelo standalone: 8282, 9090

### O projeto utiliza o Java Developer Kit 8

É possível instalar o Java Development Kit 8 através do próprio site da Oracle, optei por utilizar o Chocolatey
Para instalar o Chocolatey, acesse a documentação [aqui](https://chocolatey.org/install)
Após instalar, execute o seguinte comando no terminal do Windows: 

```shell
 choco install jdk8
```

### O projeto utiliza MySQL

É possível instalar o MySQL através do próprio site, vide documentação [aqui](https://dev.mysql.com/downloads/).
Como foi necessário alguns testes para conseguir configurar o mesmo, realizei sua configuração através do docker, a dockerfile está disponível aqui mesmo.
A imagem utilizada foi a *mysql:5.6*
Após subir a imagem docker através da docker-compose disponível dentro da pasta utils, basta executar a migração do banco de dados através do MySQL Workbench
A base de dados a ser importada deve receber o nome "taxi" para que o sistema funcione.
Antes de rodar o servidor a JNDI deve estar configurada para que ele consiga ser iniciado.

### O projeto utiliza JavaEE e JBoss através do servidor Wildfly

A versão utilizada do Wildfly é a 9.0
Para instalar o Wildfly é possível seguir os seguintes tutoriais

[Preparação de Ambiente de Desenvolvimento: Java Web com JPA + Eclipse + WildFly + MySQL](https://www.youtube.com/watch?v=2AY8JrgfDgU)
[6.7.4. EXAMPLE MYSQL XA DATASOURCE](https://access.redhat.com/documentation/en-us/jboss_enterprise_application_platform/6/html/administration_and_configuration_guide/example_mysql_xa_datasource1)

Basicamente, ative o Wildfly indo na guia
Window > Preferences
Na seção Server > Runtime Environment, clique em Add, selecione *Wildfly 9.0*
O próprio Eclipse irá instalar a versão do servidor.
Certifique-se de instalar a versão correta e de copiar o caminho ao qual o Wildfly será instalado pois serão necessárias alterações nas pastas do dito servidor.

A pasta a ser editada, normalmente estará em:
> C:\Users\$USER\wildfly-9.0.0.Final

É necessário instalar o connector Java para que seja possível estabelecer a conexão com o banco de dados, para isso, copie a pasta mysql pada dentro da pasta 

> C:\Users\$USER\wildfly-9.0.0.Final\modules\system\layers\base\com
