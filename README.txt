README

Arquivos submetidos:

- 4 projetos, sendo que qualquer um deles podem ser executados como slave ou master. Recomenda��o:
---- A com IP localhost:9998 - Master com d 250000000
---- B com IP localhost:9997 - Slave
---- C com IP localhost:9996 - Slave
---- D com IP localhost:9994 - Slave


Dentro do projeto:

- Master: Respons�vel por gerenciar quando for master
- Slave: Respons�vel por gerenciar quando for slave
- Util: Cont�m m�todos utilizados tanto pelo Master como pelo Slave
- Main: Gerencia entradas do usu�rio


Obs: quando pedir log file e slave file, adicionar caminho completo, como:

"C:\Users\Lucas\Documents\NetBeansProjects\BerkeleyClockSynchronization\BerkeleyA\slave.txt"