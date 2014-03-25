package com.a31morgan.sound.player;

public interface IFilter {

	public void applyNoteFilter(double[] input);

	public void applyStreamFilter(double[] input);

}