package com.a5coding.calculations;

import java.util.Random;

public class Channel {
    private Random random = new Random();;
    private double p;

    //p - error probability
    public Channel(double p) {
        this.p = p;
    }

    //Returns a double value from the interval [0, 1]
    private double getRandomValue(){
        return random.nextDouble() *  (1.0 - 0.0) + 0.0;
    }

    //Returns whether an element in the encoded message should be distorted or not
    private boolean shouldDistort(){
        double val = getRandomValue();
        return val < p ? true : false;
    }

    //Changes 1 to 0 and 0 to 1, returns the obtained number
    private int distortElement(int elem){
        return elem == 1 ? 0 : 1;
    }

    //Input: encoded message that is being sent
    //Sends the message through the channel while possibly changing some elements
    //Output: the message that has been sent
    public int[] send(int[] c){
        int[] sent = new int[c.length];
        System.arraycopy(c, 0, sent, 0, c.length);
        for(int i = 0; i < c.length; i++){
            if(shouldDistort()){
                sent[i] = distortElement(c[i]);
            }
        }

        return sent;
    }

}
