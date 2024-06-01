import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class NotasClient {

	public static void main(String[] args) throws IOException {
		if	(args.length != 2) {
			System.out.println("Uso: java NotasClient <ip_servidor> <nome>");
			System.exit(1);
		}
	
		// cria um socket datagrama
		DatagramSocket socket = new DatagramSocket();

		// envia um pacote
		byte[] nome = new byte[256];
		nome = args[1].getBytes();
		int servPort = 9934;
		InetAddress servIP = InetAddress.getByName(args[0]);
		DatagramPacket pacEnv = new DatagramPacket(nome, nome.length, servIP, servPort);
		socket.send(pacEnv);

		// obtem a resposta
		byte[] bufNota = new byte[8];
		DatagramPacket pacRec = new DatagramPacket(bufNota, bufNota.length);
		socket.receive(pacRec);

		// mostra a resposta
		System.out.println("Nota do aluno '"+args[1]+"' = "+ByteBuffer.wrap(bufNota).getDouble());

		// fecha o socket
		socket.close();
	}

}
