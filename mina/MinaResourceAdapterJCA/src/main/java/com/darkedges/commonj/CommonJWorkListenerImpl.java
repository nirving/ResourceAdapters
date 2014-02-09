package com.darkedges.commonj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commonj.work.WorkEvent;
import commonj.work.WorkListener;

public class CommonJWorkListenerImpl implements WorkListener {

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(CommonJWorkListenerImpl.class);
	
	public CommonJWorkListenerImpl() {
		logger.info("CommonJWorkListenerImpl");
	}

	public void workAccepted(WorkEvent workevent) {
		logger.info("workAccepted");
	}

	public void workCompleted(WorkEvent workevent) {
		logger.info("workCompleted");
	}

	public void workRejected(WorkEvent workevent) {
		logger.info("workRejected");
	}

	public void workStarted(WorkEvent workevent) {
		logger.info("workStarted");
	}
}
