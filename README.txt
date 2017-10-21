README

Arquivos submetidos:

- 4 projetos, sendo que qualquer um deles podem ser executados como slave ou master. Recomendação:
---- A com IP localhost:9998 - Master com d 250000000
---- B com IP localhost:9997 - Slave
---- C com IP localhost:9996 - Slave
---- D com IP localhost:9994 - Slave


Dentro do projeto:

- Master: Responsável por gerenciar quando for master
- Slave: Responsável por gerenciar quando for slave
- Util: Contém métodos utilizados tanto pelo Master como pelo Slave
- Main: Gerencia entradas do usuário


Obs: quando pedir log file e slave file, adicionar caminho completo, como:

"C:\Users\Lucas\Documents\NetBeansProjects\BerkeleyClockSynchronization\BerkeleyA\slave.txt"