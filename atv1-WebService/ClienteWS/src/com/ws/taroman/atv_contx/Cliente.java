package com.ws.taroman.atv_contx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.ws.taroman.atv_contx.modelo.Aluno;
import com.ws.taroman.atv_contx.modelo.Turma;

public class Cliente implements AutoCloseable{
	private Socket socket;
	
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	public static final String SERVER_IP = "127.0.0.1";
	public static final int SERVER_PORT = 19919;

	public Cliente(String ip, int porta) throws IOException {
		socket = new Socket(ip, porta);
		
		outputStream = new ObjectOutputStream(socket.getOutputStream());
		//inputStream = new ObjectInputStream(socket.getInputStream());
	}

	
	@Override
	public void close() throws Exception {
		//inputStream.close();
		outputStream.close();
		socket.close();
	}
	
	/**Envia uma String pelo socket em UTF-8
	 * @throws IOException */
	public void sendUTF(String mensagem) throws IOException {
		outputStream.writeUTF(mensagem);
		outputStream.flush();
	}
	
	/**Recebe uma String pelo socket em UTF-8
	 * @throws IOException */
	public String receiveUTF() throws IOException {
		return inputStream.readUTF();
	}
	
	
	public static void main(String[] args) {
		Aluno alunos[] = {new Aluno(1,"qwe",true), new Aluno(2, "asd", false), new Aluno(3,"zxc",true)};
		Turma turma = new Turma(0, 2018, "bnm", alunos);
		
		try(Cliente cliente = new Cliente(SERVER_IP, SERVER_PORT)){
			
			System.out.println("Iniciou   "+turma.toJson());
			cliente.sendUTF(turma.toJson());
			System.out.println("Enviou");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
