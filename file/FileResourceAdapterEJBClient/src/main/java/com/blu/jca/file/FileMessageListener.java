package com.blu.jca.file;

import javax.resource.cci.MessageListener;

public interface FileMessageListener extends MessageListener {
	void onMessage(FileMessage message);
}