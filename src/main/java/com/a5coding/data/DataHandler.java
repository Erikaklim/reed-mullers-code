package com.a5coding.data;

import com.a5coding.calculations.Channel;
import com.a5coding.calculations.Decoder;
import com.a5coding.calculations.Encoder;

public abstract class DataHandler<T> {
    public Channel channel;
    public Encoder encoder = new Encoder();
    public Decoder decoder = new Decoder();
    public int addedZeros;

    public abstract T handleWithoutEncoding(String input, double p);

    public abstract T handleWithEncoding(String input, double p, int m);

    //Input: binary - binary representation of text, m - RM(1,m) parameter
    //Divides the binary string into strings of length (m+1). If needed trailing zeros are added
    //Output: array containing divided strings
    public String[] divide(String binary, int m) {
        String[] dividedArr = binary.split("(?<=\\G.{" + (m + 1) + "})");
        int reminder = binary.length() % (m + 1);
        if (reminder != 0) {
            int toAdd = (m + 1) - reminder;
            addedZeros = toAdd;
            int lastIndex = dividedArr.length - 1;
            while (toAdd > 0) {
                dividedArr[lastIndex] += "0";
                toAdd--;
            }
        }

        return dividedArr;
    }

}
