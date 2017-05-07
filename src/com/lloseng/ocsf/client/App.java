package com.lloseng.ocsf.client;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 
 * app for running client class.
 * 
 * @author Thitiwat Thongbor
 *
 */
public class App {
	Client cl;
	Scanner scan = new Scanner(System.in);

	/**
	 * construct the application.
	 */
	App() {
		System.out.print("Host name : ");
		String host = scan.nextLine();
		System.out.print("Port : ");
		int port = scan.nextInt();
		System.out.println();
		System.out.println("Connecting to : " + host + ":" + port);
		cl = new Client(host, port);
		run();
	}

	/**
	 * 
	 */
	private void run() {
		try {
			cl.openConnection();
			String com = scan.nextLine();
			cl.sendToServer(com);
			while (cl.isConnected()) {
				String ans = scan.nextLine();
				if (ans.equals("shoot!")) {
					shoot(10); // time in millisecond.
				}
				cl.sendToServer(ans);
				if (ans.equals("exit")) {
					cl.closeConnection();
					System.exit(0);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * run the script answer the question
	 * 
	 * @param time
	 *            in millisecond for each time answer.
	 */
	private void shoot(long time) {
		try {
			while (cl.isConnected()) {
				try {
					TimeUnit.MILLISECONDS.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cl.sendToServer(String.valueOf(cl.calculate(cl.msg)));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * main class
	 * 
	 * @param args
	 *            doesnt' use.
	 */
	public static void main(String[] args) {
		new App();
	}
}
