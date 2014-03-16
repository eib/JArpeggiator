package com.a31morgan.sound.gui;

import com.a31morgan.sound.Melody;


public class BackgroundPlayer implements Runnable, IPlayer {
	
	private final IPlayer player;
	private final Object threadLock = new Object();
	private Melody nextMelody = null; //"null" means "REST"
	private boolean isClosed = false;
	
	public BackgroundPlayer(IPlayer player) {
		this.player = player;
	}

	@Override
	public void run() {
		player.start();
		try {
			while (!isClosed()) {
				waitForNextMelody();
				playNextMelody();
			}
		} finally {
			player.stop();
		}
	}
	
	private void waitForNextMelody() {
		synchronized (threadLock) {
			while (!isClosed() && !hasNextMelody()) {
				try {
					threadLock.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	private void playNextMelody() {
		Melody melody = null;
		synchronized (threadLock) {
			if (!isClosed()) {
				melody = this.nextMelody;
			}
		}
		if (melody != null) {
			this.player.play(this.nextMelody);
		}
	}
	
	private boolean isClosed() {
		return this.isClosed;
	}
	
	private boolean hasNextMelody() {
		return this.nextMelody != null;
	}
	
	/* Can be called from a different thread. */

	private void setNextMelody(Melody melody) {
		synchronized (threadLock) {
			this.nextMelody = melody;
			threadLock.notify();
		}
	}
	
	@Override
	public void start() {
		new Thread(this, "Background Player").start();
	}
	
	@Override
	public void stop() {
		synchronized (threadLock) {
			this.isClosed = true;
			threadLock.notify();
		}
	}

	@Override
	public void play(Melody melody) {
		setNextMelody(melody);
	}

	@Override
	public void pause() {
		setNextMelody(null);
		//TODO: stop player here???????
	}
}
