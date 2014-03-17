package com.a31morgan.sound.player;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.a31morgan.sound.Melody;

public class BackgroundPlayerTest {

	@Test(timeout=1000)
	public void testRun_ThatAClosedPlayerDoesNotWaitForNextMelody() {
		IPlayer mock = mock(IPlayer.class);
		BackgroundPlayer player = new BackgroundPlayer(mock);

		player.stop();
		player.run();

		verify(mock, times(1)).start();
		verify(mock, never()).play(any(Melody.class));
		verify(mock, times(1)).stop();
	}

	@Test(timeout=1000)
	public void testRun_ThatAClosedPlayerDoesNotPlayNextMelody() {
		IPlayer mock = mock(IPlayer.class);
		BackgroundPlayer player = new BackgroundPlayer(mock) {
			@Override
			protected void waitForNextMelody() {
				this.stop();
			}
		};
		
		player.run();

		verify(mock, times(1)).start();
		verify(mock, never()).play(any(Melody.class));
		verify(mock, times(1)).stop();
	}

	@Test(timeout=1000)
	public void testSetNextMelody_ThatItAwakensAWaitingPlayer() throws InterruptedException {
		final Melody melody = new Melody(100);
		IPlayer mock = mock(IPlayer.class);
		final BackgroundPlayer player = new BackgroundPlayer(mock);
		
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				player.setNextMelody(melody);
				return null;
			}
		}).when(mock).start();
		
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				player.stop();
				return null;
			}
		}).when(mock).play(any(Melody.class));
		
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				synchronized (melody) {
					melody.notify();
				}
				return null;
			}
		}).when(mock).stop();

		player.start();
		synchronized (melody) {
			melody.wait();
		}
		
		verify(mock, times(1)).start();
		verify(mock, times(1)).play(melody);
		verify(mock, times(1)).stop();
	}
}
