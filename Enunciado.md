# Enunciado dos exercícios da 5ª Sessão do PrePROMPT - IoC, DI e Guice

## Exercício 1

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

9. Compile os projectos e é expectável que apenas o projecto SocialBus-Tests-iter1 apresente 19 erros de compilação por referência a tipos desconhecidos.

10. Corrija o problema anterior adicionado o projecto SocialBus-Main-iter1 à "*Build Path*" do projecto SocialBus-Tests-iter1.