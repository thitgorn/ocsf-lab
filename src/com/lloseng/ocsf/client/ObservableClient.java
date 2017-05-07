// This file contains material supporting section 6.13 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com

package com.lloseng.ocsf.client;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * This class acts as a subclass of <code>AbstractClient</code> and is also an
 * <code>Observable</code> class. Each time a new message is received, observers
 * are notified.
 *
 * @author Dr Robert Lagani&egrave;re
 * @author Dr Timothy C. Lethbridge
 * @author Francois Belange
 * @version Febuary 2001
 */
public class ObservableClient extends Observable {
	/**
	 * Indicates a close of the connection to server.
	 */
	public static final String CONNECTION_CLOSED = "#OC:Connection closed.";

	/**
	 * Indicates establishment of a connection to server.
	 */
	public static final String CONNECTION_ESTABLISHED = "#OC:Connection established.";

	/**
	 * The service instance used to simulate multiple class inheritance.
	 */
	private AdaptableClient service;

	public ObservableClient(String host, int port) {
		service = new AdaptableClient(host, port, this);
	}

	/**
	 * Opens the connections with the server.
	 * @throws IOException if error trying to connect to server.
	 */
	final public void openConnection() throws IOException {
		service.openConnection();
	}

	/**
	 * Closes the connection to the server.
	 * @throws IOException if network error.
	 */
	final public void closeConnection() throws IOException {
		service.closeConnection();
	}

	/**
	 * Sends an object to the server. This is the only way that methods should
	 * communicate with the server.
	 *
	 * @param msg  The message to be sent.
	 * @throws IOException if cannot send message to server
	 */
	final public void sendToServer(Object msg) throws IOException {
		service.sendToServer(msg);
	}

	/**
	 * Used to find out if the client is connected.
	 * 
	 * @return true if the client is connected to a server.
	 */
	final public boolean isConnected() {
		return service.isConnected();
	}

	/**
	 * Get the port number that will be used for connection.
	 * 
	 * @return the port number.
	 */
	final public int getPort() {
		return service.getPort();
	}

	/**
	 * Sets the server port number for the next connection. Only has effect if
	 * the client is not currently connected.
	 *
	 * @param port
	 *            the port number.
	 */
	final public void setPort(int port) {
		service.setPort(port);
	}

	/**
	 * Get the name of the server that will be used for connection.
	 * 
	 * @return the host name.
	 */
	final public String getHost() {
		return service.getHost();
	}

	/**
	 * Sets the server host for the next connection. Only has effect if the
	 * client is not currently connected.
	 *
	 * @param host
	 *            the host name.
	 */
	final public void setHost(String host) {
		service.setHost(host);
	}

	/**
	 * @return the client's Inet address.
	 */
	final public InetAddress getInetAddress() {
		return service.getInetAddress();
	}

	/**
	 * This method is used to handle messages from the server. This method can
	 * be overridden but should always call notifyObservers().
	 *
	 * @param message The message received from the client.
	 */
	protected void handleMessageFromServer(Object message) {
		setChanged();
		notifyObservers(message);
	}

	/**
	 * Hook method called after the connection has been closed.
	 */
	protected void connectionClosed() {
		setChanged();
		notifyObservers(CONNECTION_CLOSED);
	}

	/**
	 * Hook method called each time an exception is raised by the client
	 * listening thread.
	 *
	 * @param exception the exception raised.
	 */
	protected void connectionException(Exception exception) {
		setChanged();
		notifyObservers(exception);
	}

	/**
	 * Hook method called after a connection has been established.
	 */
	protected void connectionEstablished() {
		setChanged();
		notifyObservers(CONNECTION_ESTABLISHED);
	}
}
