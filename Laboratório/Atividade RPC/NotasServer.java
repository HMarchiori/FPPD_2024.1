import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

class Aluno {  
	public String nome;
	public double nota;

	public Aluno(String  nome,double nota) {
		this.nome = nome;
		this.nota = nota;
	}
}

public class NotasServer {

	private static Aluno[] alunos = new Aluno[]{
		new Aluno("Alexandre", 9.5),
		new Aluno("Barbara",   8.5),
		new Aluno("Joao",      6.5),
		new Aluno("Maria",     9.0),
		new Aluno("Paulo",    10.0),
		new Aluno("Pedro",     7.0)
	};

	private static double obtemNota(String nome) {
		for	(int i = 0 ; i < alunos.length; ++i) {
			if (alunos[i].nome.equals(nome)) {
				return alunos[i].nota;
			}
		}
		return -1.0;
	}

	public static void main(String[] args) throws IOException {
		int servPort = 9934;
		DatagramSocket socket = new DatagramSocket(servPort);
		byte[] resposta	= new byte[8];
		byte[] nomeAluno = new byte[1024];

		while (true) {
			try {
				DatagramPacket pacIn = new DatagramPacket(nomeAluno, nomeAluno.length);
				socket.receive(pacIn);
				double nota = obtemNota(new String(pacIn.getData(), 0, pacIn.getLength()));
				ByteBuffer.wrap(resposta).putDouble(nota);
				InetAddress endIP = pacIn.getAddress();
				int porta = pacIn.getPort();
				DatagramPacket pacOut = new DatagramPacket(resposta, resposta.length, endIP, porta);
				socket.send(pacOut);
			} catch	(IOException e) {
				e.printStackTrace();
				break;
			}
		}

		socket.close();
	}
}

