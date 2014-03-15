package com.a31morgan.sound.gui;

import com.a31morgan.sound.Melody;


public class BackgroundPlayer implements Runnable, IPlayer {
	
	private final Player player;
	private final Object threadLock = new Object();
	private Melody nextMelody = null; //"null" means "REST"
	private boolean isClosed = false;
	
	public BackgroundPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		player.open();
		try {
			while (!isClosed()) {
				waitForNextMelody();
				playNextMelody();
			}
		} finally {
			player.close();
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
		synchronized (threadLock) {
			if (!isClosed() && hasNextMelody()) {
				this.player.play(this.nextMelody);
			}
		}
	}
	
	private boolean isClosed() {
		return this.isClosed;
	}
	
	private boolean hasNextMelody() {
		return this.nextMelody != null;
	}
	
	/* Can be called from a different thread. */
	
	@Override
	public void close() {
		synchronized (threadLock) {
			this.isClosed = true;
			threadLock.notify();
		}
	}

	@Override
	public void setMelody(Melody nextMelody) {
		synchronized (threadLock) {
			this.nextMelody = nextMelody;
			threadLock.notify();
		}
	}
}
