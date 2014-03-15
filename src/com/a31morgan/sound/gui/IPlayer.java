package com.a31morgan.sound.gui;

import com.a31morgan.sound.Melody;

public interface IPlayer {

	public void close();

	public void setMelody(Melody melody);

}