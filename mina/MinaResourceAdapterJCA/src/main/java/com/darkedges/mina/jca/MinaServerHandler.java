package com.darkedges.mina.jca;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.darkedges.min.api.MinaMessageListener;

public class MinaServerHandler extends IoHandlerAdapter {
	private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());

	private final MinaMessageListener messageListener;
	
	public MinaServerHandler(MinaMessageListener messageListener) {
		this.messageListener = messageListener;
	}

	@Override
	public void sessionOpened(IoSession session) {
	}

	@Override
	public void messageReceived(IoSession session, Object message) {
		messageListener.onMessage(message.toString());
		session.write("hello back");
	}

}
