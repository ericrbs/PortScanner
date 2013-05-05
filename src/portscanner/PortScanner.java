package portscanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Eric Silva
 */
public class PortScanner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Caso nenhum host for informado o aplicativo irá escanear as portas
        // no host localhost (maquina local).
        if (args == null || args.length != 1 || args[0] == null) {
            // Define o host como localhost
            System.out.println("Hostname não fornecido. Escaneando 'localhost'.");
            args = new String[]{"localhost"};
        }

        // Itera por todas as portas possiveis iniciando da porta 1.
        // TODO Receber uma faixa de portas para ser escanedas.
        // Considerando um atraso de 100 milisegundos, para iterar por todas as portas
        // possiveis pode demorar um pouco mais the 1 hora e meia. 
        for (int i = 1; i < 65535 ; i++) {
            // Tenta abrir um recurso para o porta atual conforme a iteração. 
            // Como usamos try catch resource, não será necessário fechar o socket
            // ele será fechado automaticamente no final da execução do bloco try catch, 
            // incluindo casos de erro.
            try (Socket s = new Socket()){
                // Exibe o hostname e o numero da porta que está testando na
                // iteração atual.
                System.out.print("Host[" + args[0] + "] Port[" + i + "] ");
                
                // Tenta conectar na porta atual, caso a porta estiver aberta, 
                // nenhum Exception será lançada. 
                // Caso a porta estiver fechada receberemos uma IOExcepton. 
                // Caso o host for invalido receberemos UnknowHostException.
                // Um timeout de 100 milisegundos foi define para que a conexão 
                // não fique aguardando por muito tempo. Porém se o host escaneado 
                // estiver em outro pais, por exemplo, o tempo de conexão pode ser
                // maior que 100 milisegundos. Logo você deve definir um tempo 
                // de acordo com a latencia do host desejado. Para um rede local  
                // sem fio a latencia é de aproximadamente 10 milisegundos, podendo
                // chegar a valores mais altos dependendo da qualidade do sinal.
                s.connect(new InetSocketAddress(args[0], i), 100);
                
                // Exibe a mensagem porta aberta quando a conexão for bem sucedida.
                System.out.println("Porta aberta.");
            } catch (UnknownHostException ex) {
                // Exibe a mensagem 'Host desconhecido' quando o hostname não 
                // pode ser resolvido.
                System.out.println("Host desconhecido.");
            } catch (IOException ex) {
                // Exibe a mensagem 'Porta fechada' quando não pode estabelecer uma
                // conexão com a porta atual.
                System.out.println("Porta fechada.");
            }

        }
        
        // Exibe uma mensagem após verificar todas as portas.
        System.out.println("Todas as portas foram verificadas.");
                
    }
}
