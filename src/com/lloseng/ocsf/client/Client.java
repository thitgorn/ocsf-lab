package com.lloseng.ocsf.client;

/**
 * Connecting to server class by prasing ip and port number.
 * 
 * @author Thitiwat Thongbor
 *
 */
public class Client extends AbstractClient {
	public String msg;

	/**
	 * construct the client.
	 * @param host ip address
	 * @param port number 1023+
	 */
	public Client(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println(msg.toString());
		System.out.println(calculate(msg.toString()));
		this.msg = msg.toString();
	}

	@Override
	protected void connectionClosed() {
		System.out.println("Disconnected");
	}

	@Override
	protected void connectionEstablished() {
		System.out.println("Connected");
	}

	/**
	 * cal calculate the answer
	 * 
	 * @param msg
	 * @return
	 */
	public int calculate(String msg) {
		String[] split = msg.split(" ");
		String result = "";
		if (split.length == 5) {
			int x = Integer.parseInt(split[2]);
			int y = Integer.parseInt(split[4].substring(0, split[4].length() - 1));

			String a = Integer.toBinaryString(x);
			String b = Integer.toBinaryString(y);
			int length = 0;
			if (a.length() > b.length()) {
				length = a.length();
				for (int i = b.length(); i < length; i++) {
					b = "0" + b;
				}
			} else {
				length = b.length();
				for (int i = a.length(); i < length; i++) {
					a = "0" + a;
				}
			}
			char delimit = split[3].charAt(0);
			if (delimit == '|') {
				for (int i = 0; i < a.length(); i++) {
					if ((a.charAt(i) == '1') || (b.charAt(i) == '1')) {
						result += 1;
					} else {
						result += 0;
					}
				}
			}
			if (delimit == '^') {
				for (int i = 0; i < a.length(); i++) {
					if (a.charAt(i) != b.charAt(i)) {
						result += 1;
					} else {
						result += 0;
					}
				}
			}
			if (delimit == '&') {
				for (int i = 0; i < a.length(); i++) {
					if (a.charAt(i) == '1' && b.charAt(i) == '1') {
						result += 1;
					} else {
						result += 0;
					}
				}
			}
		}
		return Integer.parseInt(result, 2);
	}
}
