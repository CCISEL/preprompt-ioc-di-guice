# Enunciado dos exercícios da 5ª Sessão do PrePROMPT - IoC, DI e Guice

## Exercício 1

### 1ª parte - Setup dos projectos

1. Crie uma pasta local como respositório dos projectos que irá desenvolver nesta sessão como o nome: **preprompt-ioc-di-guice**

2. Faça download do repositório disponível em https://github.com/prompt/preprompt-ioc-di-guice/, para a localização criada na alínea anterior. Se tiver o utilitário Git poderá fazer: `git clone https://github.com/prompt/preprompt-ioc-di-guice.git`.

3. Crie uma nova pasta para o workspace Eclipse exterior à localização da pasta da alínea 1. Por exemplo, dê a essa pasta o nome **preprompt-ioc-di-guice.workspace**

4. Abra o Eclipse e forneça a pasta da alínea anterior como localização do workspace.

5. Adicione um novo projecto Eclipse fazendo: **File > New > Project**

6. No wizard selecione: **Java Project from Existing Ant Buildfile**

7. Forneça os seguintes dados: 
   > * *Project name*: **SocialBus-Main-iter1**
   > * *Ant buildfile*: o ficheiro `Exercicio-1\build.xml`
   > * *Select javac declaration to use to define project* - selecionar o item: **"javac" task to found in target "compile-main"**.
   
8. Adicione um 2ª projecto ao Eclipse repetindo os passos anteriores, com as seguintes diferenças:
   > * *Project name*: **SocialBus-Tests-iter1**
   > * *Ant buildfile*: o ficheiro `Exercicio-1\build.xml`
   > * *Select javac declaration to use to define project* - selecionar o item: **"javac" task to found in target "compile-test"**.

9. Compile os projectos e é expectável que apenas o projecto **SocialBus-Tests-iter1** apresente 19 erros de compilação por referência a tipos desconhecidos. O projecto SocialBus-Main-iter1 deve compilar com sucesso.

10. Corrija o problema anterior adicionado o projecto **SocialBus-Main-iter1** à "*Build Path*" do projecto **SocialBus-Tests-iter1**.

11. Corra os testes unitários clicando sobre o package `socialbus.tests` e fazendo *Run As > JUnit Test*.

### 2ª parte - Dados do Facebook

Nesta fase deverá obter alguns dados para configuração do canal de input - Facebook.

1. Entre na página do Facebook com a sua conta.

2. Passe para a página http://developers.facebook.com/

3. Clique em "Documentation"

4. Clique em "Graph API"

5. No menu do lado esquerdo "Objects" clique em "User"

6. No exemplo clique em https://graph.facebook.com/me

7. Da página obtida pode extrair os seguintes dados:
   > * Na segunda linha em "id" obtém o seu **Uid**.
   > * Do URL "https://graph.facebook.com/me?access_token=....." pode extrair o *access token* que aparece à frente de `me?access_token=`.


### 3ª parte - SocialBus

Agora irá usar os dados obtidos na 2ª parte, para configuração do canal de input - Facebook. Também terá de configurar o canal de output - EMail (preferencialmente Gmail).

1. Abra o código fonte da classe `socialbus.app.MainProgram`.

2. Configure o *Uid* e *Access Token* obtidos na 2ª parte.

3. Configure o endereço da sua conta de EMail e password. Este será o remetente das mensagens enviadas com posts recolhidos do Facebook. Se a sua conta não for GMail, actualize também o SMTP host.

4. Execute o programa. Neste momento ele está configurado para filtrar mensagens que contenham a palavra "Portugal". 

5. Consulte a sua página do Facebook e identifique palavras chave comuns a vários posts. Altere a configuração do filtro usado por `socialbus.core.SocialParserNotifier` de modo a verificar o envio de emails referentes a outros posts.

6. Pretende-se alterar a aplicação `socialbus.core.SocialParserNotifier` de modo a que no lugar de Emails passe a enviar *tweets* na conta *greatwacky*. Para tal: 
  > * baseando-se na aplicação `javatwitter.app.TestTwitter` implemente um novo canal de output que coloque *tweets*  na conta *greatwacky*
  > * Implemente uma projecção - `IProjection` - que transforme Posts do Facebook numa String que não exceda os 140 caracteres permitidos nas mensagens do Twitter.

7. Desenhe um teste unitário para a classe `socialbus.channel.fb.FbPostReader` que verifique a leitura correcta de novos posts, sem repetir posts que já tenham sido retornados anteriormente.

## Exercício 2

1. Seguindo os passos da 1ª parte do Exercício 1, adicione mais dois projectos ao Eclipse: **SocialBus-Main-iter2** e **SocialBus-Tests-iter2**, baseado no *Ant buildfile*: `Exercicio-2\build.xml`

2. Adicione ao package `socialbus.app` uma classe com um `main` que implemente a mesma funcionalidade do exercício 1, 3ªparte, 4ª alínea.

3. Modifique a classe anterior para que envie *tweets* no lugar de emails.

## Exercício 3

1.Seguindo os passos da 1ª parte do Exercício 1, adicione mais dois projectos ao Eclipse: **NaiveContainer-Main** e **NaiveContainer-Tests**, baseado no *Ant buildfile*: `naivecontainer\build.xml`

2. Adicione o projecto **NaiveContainer-Main** ao *Build Path* do projecto **SocialBus-Main-iter2**.

3. Defina um ficheiro de configuração em XML que concretize todas as dependências necessárias ao funcionamento de `SocialParserNotifier` com envio de Emails. Adicione a anotação `@Named` aos parâmetros de construção que precisem de ser injectados. Baseie-se no exemplo fornecido no teste unitário: `XmlConfigurationTests`.

4. Altere a função main implementada no Exercício 2, para que passe a instanciar `SocialParserNotifier` através do injector de dependências **NaiveContainer** e baseado no ficheiro de configuração que definiu na alínea anterior.

5. Defina um outro ficheiro de configuração em XML para o `SocialParserNotifier` que especifique o envio de "tweets".

6. Teste a nova configuração da alínea 5, substituíndo na função `main` da alínea 4, o fichero de configuração usado.

## Exercício 4

1. Defina um ficheiro de configuração em Java que concretize todas as dependências necessárias ao funcionamento de `SocialParserNotifier` com envio de Emails. Baseie-se no exemplo fornecido no teste unitário: `NamedDependenciesTests`.

2. Altere a função main implementada no Exercício 3, para que use o ficheiro de configuração que definiu na alínea anterior.

3. Defina um ficheiro de configuração em Java que concretize todas as dependências necessárias ao funcionamento de `SocialParserNotifier` com envio de "tweets".

2. Altere a função main, para que use o ficheiro de configuração que definiu na alínea anterior.


## Exercício 5

1. Copie a pasta `Exercicio-2` para uma nova pasta `Exercicio-5`.

2. Seguindo os passos da 1ª parte do Exercício 1, adicione mais dois projectos ao Eclipse: **SocialBus-Main-iter5** e **SocialBus-Tests-iter5**, baseado no *Ant buildfile*: `Exercicio-5\build.xml`

3. Defina um modulo Guice que concretize todas as dependências necessárias ao funcionamento de `SocialParserNotifier` com envio de Emails. Atenção que a anotação `@Named` refere-se agora ao package **Guice** e não **NaiveContainer**.

4. Altere a função `main`, para que utilize o ficheiro de configuração que definiu na alínea anterior e o injector de dependências Guice.

5. Defina um módulo Guice que concretize **apenas as dependências necessárias ao IOutputChannel** para envio de "tweets".

6. Utilize o *override* de módulos para que o Guice use a combinação das configurações das alíneas 3 e 5.

