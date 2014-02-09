package com.darkedges.commonj;

import commonj.work.Work;

public class CommonJWorkImpl implements Work {
	Runnable r;
	private boolean isDaemon;

	public CommonJWorkImpl(Runnable r) {
		isDaemon = false;
		this.r = r;
	}

	public void run() {
		r.run();
	}

	public void release() {
	}

	public boolean isDaemon() {
		return isDaemon;
	}

	public void setIsDaemon(boolean isDaemon) {
		this.isDaemon = isDaemon;
	}
}
