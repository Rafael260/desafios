# String Formatter

Após ler o coding style do kernel Linux, você descobre a mágica que é 
ter linhas de código com no máximo 80 caracteres cada uma.

Assim, você decide que de hoje em diante seus e-mails enviados também 
seguirão um padrão parecido e resolve desenvolver um plugin para te ajudar
com isso. Contudo, seu plugin aceitará no máximo 40 caracteres por linha.

## Exemplo input

`In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.`

`And God said, "Let there be light," and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light "day," and the darkness he called "night." And there was evening, and there was morning - the first day.`

## Como construir o projeto

`Com o terminal/cmd aberto na pasta do projeto(a pasta com o arquivo pom.xml), utilize o comando: mvn clean package`

## Executar o projeto

`java -jar target/StringFormatter-1.0-SNAPSHOT-jar-with-dependencies.jar
Opcionalmente você pode colocar os parâmetros <texto> <limite_de_caracteres> <booleano_para_justificar_texto>
Exemplo: java -jar target/StringFormatter-1.0-SNAPSHOT-jar-with-dependencies.jar "texto tal" 6 false`
