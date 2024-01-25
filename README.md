# # Resource Monitor

Este trabalho prático consiste em desenvolver um sistema em Java que
permita fazer a monitorização dos recursos do sistema operativo,
nomeadamente as percentagens da carga do CPU, da memória RAM livre e do
espaço em disco disponível. Este sistema segue uma lógica de programação
multitarefa, utilizando threads para executar várias ações em simultâneo e de
forma concorrente.

Na base do sistema está a aplicação do padrão produtor-consumidor. Devem
existir 3 produtores, cada um responsável por obter a cada 100 milissegundos
uma das percentages já referidas. Isto significa que existe um produtor
responsável só pela carga do CPU, outro só pela memória RAM livre, e outro
pelo espaço em disco disponível (é fornecida a classe ResourceMonitorUtils
que oferece um conjunto de métodos estáticos para obter os valores
referidos). Por outro lado, devem existir n consumidores (sendo n um
argumento configurável do sistema) que vão ler os dados produzidos pelos
produtores e identificar situações de alarme quando a carga do CPU
ultrapassa os 80%, quando a memória RAM livre fica abaixo dos 10%, e
quando o espaço em disco disponível fica abaixo dos 20%. Nestas situações
de alarme, é adicionada na GUI do sistema uma mensagem de alerta com a
informação relevante (é fornecida uma GUI para este propósito na classe
ResourceMonitorGUI). De referir que as threads dos produtores e dos
consumidores devem ser geridas através de thread pools, e que existe apenas
um ArrayBlockingQueue no sistema que é utilizado por todos os produtores e
consumidores.

Adicionalmente, existe uma thread daemon independente cujo propósito é
verificar, a cada segundo, se alguma das threads dos produtores ou dos
consumidores deixou de estar em funcionamento. Para tal, esta thread
assume que um produtor deixou de estar em execução se este não produzir
nenhum novo valor há 10 ou mais segundos, e, da mesma forma, assume que
um consumidor terminou a sua execução quando este não obtém um novo
valor há 30 ou mais segundos. Em ambos os casos, a thread é responsável
por lançar um novo produtor/consumidor que vai substituir o que terminou.
Por fim, o sistema deve permitir ser encerrado na totalidade sem que existam
threads pendentes ou sem que estas sejam terminadas abruptamente. Este
encerramento do sistema pode ser acionado, por exemplo, através da escrita
de um comando de texto na consola (são aceites outras soluções).

## Enunciado feito por: ESGTS/IPS 1 Ricardo Pereira, Ph.D.
