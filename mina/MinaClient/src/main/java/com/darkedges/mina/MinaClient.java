package com.darkedges.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author giftsam
 */
public class MinaClient {
	private static final int PORT = 8801;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		IoConnector connector = new NioSocketConnector();
		connector.getSessionConfig().setReadBufferSize(2048);

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));

		connector.setHandler(new MinaClientHandler("Hello Server.."));
		ConnectFuture future = connector.connect(new InetSocketAddress(
				"127.0.0.1", PORT));
		future.awaitUninterruptibly();

		if (!future.isConnected()) {
			return;
		}
		IoSession session = future.getSession();
		session.getConfig().setUseReadOperation(true);
		session.getCloseFuture().awaitUninterruptibly();

		System.out.println("After Writing");
		connector.dispose();

	}

}
