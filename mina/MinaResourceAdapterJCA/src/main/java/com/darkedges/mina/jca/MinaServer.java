package com.darkedges.mina.jca;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.endpoint.MessageEndpoint;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.darkedges.commonj.CommonJExecutor;
import com.darkedges.min.api.MinaMessageListener;

public class MinaServer  {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(MinaServer.class);

	private MinaActivationSpec activationSpec;
	private MinaMessageListener messageListener;
	private boolean released;
	private IoAcceptor acceptor;

	public MinaServer(ActivationSpec activationSpec,
			MessageEndpoint messageEndpoint) {
		logger.info("[start] MinaServer()");
		this.activationSpec = (MinaActivationSpec) activationSpec;
		this.messageListener = (MinaMessageListener) messageEndpoint;
		this.released = false;
	}

	public void bind() {
		logger.info("port"+activationSpec.getPort());
		Executor executor = new CommonJExecutor("java:comp/env/wm/minaWorkManager");
		acceptor = new NioSocketAcceptor(executor,new NioProcessor(executor));
		acceptor.getFilterChain().addLast("logger",new LoggingFilter());
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));
		acceptor.setHandler(new MinaServerHandler(messageListener));
		try {
			acceptor.bind(new InetSocketAddress(activationSpec.getPort()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unbind() {
		logger.info("port"+activationSpec.getPort());
		acceptor.unbind();
	}
}
