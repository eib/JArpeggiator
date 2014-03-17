package com.a31morgan.sound.player;

import com.a31morgan.sound.Melody;

public interface IPlayer {

	public void start();
	
	public void play(Melody melody);
	
	public void pause();

	public void stop();

}