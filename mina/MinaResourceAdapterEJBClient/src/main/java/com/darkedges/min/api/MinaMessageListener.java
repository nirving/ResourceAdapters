package com.darkedges.min.api;

import javax.resource.cci.MessageListener;

public interface MinaMessageListener extends MessageListener {
	void onMessage(String message);
}