package com.a31morgan.sound;

public enum Pitch {

    REST,
    C1, C1$, D1, D1$, E1, F1, F1$, G1, G1$, A1, A1$, B1,
    C2, C2$, D2, D2$, E2, F2, F2$, G2, G2$, A2, A2$, B2,
    C3, C3$, D3, D3$, E3, F3, F3$, G3, G3$, A3, A3$, B3,
    C4, C4$, D4, D4$, E4, F4, F4$, G4, G4$, A4, A4$, B4,
    C5, C5$, D5, D5$, E5, F5, F5$, G5, G5$, A5, A5$, B5,
    C6, C6$, D6, D6$, E6, F6, F6$, G6, G6$, A6, A6$, B6,
    C7, C7$, D7, D7$, E7, F7, F7$, G7, G7$, A7, A7$, B7,
    C8, C8$, D8, D8$, E8, F8, F8$, G8, G8$, A8, A8$, B8,
    C9;
    public static final int SAMPLE_RATE = 16 * 1022; // ~16KHz
    private final byte[] sine;
    private static final double MININUM_FREQUENCY = 32.7032; //C1
    public static final float MAX_VOLUME = 127.0f;

    Pitch() {
    	boolean isRest = this.ordinal() == 0;
    	if (isRest) {
    		this.sine = new byte[SAMPLE_RATE];
    	} else {
        	double sineLength = isRest ? SAMPLE_RATE : getSineLength();
            this.sine = new byte[(int)sineLength * 1000];
            for (int ii = 0; ii < sine.length; ii++) {
                double angle = 2.0 * Math.PI * ii / sineLength;
                sine[ii] = (byte)(Math.sin(angle) * MAX_VOLUME);
            }
    	}
    }
    
    public double getFrequency() {
    	double frequency = 1.0;
    	if (this.ordinal() > 0) { //"not REST"
            double exp = ((double)this.ordinal() - 1) / 12.0;
            return MININUM_FREQUENCY * Math.pow(2.0, exp);
    	}
    	return frequency;
    }
    
    public double getPeriod() {
    	return 1.0 / getFrequency();
    }
    
    /**
     * @return The length of the period.
     */
    public double getSineLength() {
        return (double)SAMPLE_RATE * getPeriod();
    }
    
    public byte[] getData(int millis) {
        int length = Pitch.SAMPLE_RATE * millis / 1000;
    	return ArrayUtils.repeatToLength(this.sine, length);
    }
}
