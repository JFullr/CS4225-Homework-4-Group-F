package network;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import matrix.Matrix;
import utils.FileUtils;

/**
 * The Class MatrixClient handles the client side of the matrix operations.
 * 
 * @author Joseph Fuller, James Irwin, Timothy Brooks
 * @version Spring 2020
 */
public class MatrixClient {

	private static final String FILE_IP_KEY = "server-ip";
	private static final String FILE_PORT_KEY = "port";
	private static final int SOCKET_TIMEOUT_SECONDS = 3;

	private String address;
	private int port;
	private Socket client;

	/**
	 * Instantiates a new matrix client.
	 *
	 * @param initFile the init file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public MatrixClient(File initFile) throws IOException {
		String[] data = FileUtils.readLines(initFile.getPath());
		for (String line : data) {
			if (line.toLowerCase().startsWith(FILE_IP_KEY)) {
				line = line.substring(line.indexOf(":") + 1);
				this.address = line.trim();
			} else if (line.toLowerCase().startsWith(FILE_PORT_KEY)) {
				line = line.substring(line.indexOf(":") + 1);
				this.port = Integer.parseInt(line.trim());
			}
		}
	}

	/**
	 * Instantiates a new matrix client.
	 *
	 * @param address the address
	 * @param port    the port
	 */
	public MatrixClient(String address, int port) {
		this.address = address;
		this.port = port;
	}

	/**
	 * Requests the matrix server to evaluate and respond with a product of the given
	 * matrices and returns the response as a Matrix object.
	 *
	 * @param matrices the matrixes
	 * @return the matrix
	 */
	public Matrix multiplyMatrices(Matrix... matrices) {

		if (this.client == null) {
			this.client = this.createClient();
			if (this.client == null) {
				return null;
			}
		}

		Matrix evaluated = this.requestServer(matrices);

		return evaluated;

	}

	private Matrix requestServer(Matrix... matrixes) {
		return this.waitResponse();
	}

	private Matrix waitResponse() {
		return null;
	}

	private Socket createClient() {

		Socket client = null;
		try {
			client = new Socket(this.address, this.port);
			client.setSoTimeout(SOCKET_TIMEOUT_SECONDS);
		} catch (Exception e) {
			client = null;
		}

		return client;
	}

}
