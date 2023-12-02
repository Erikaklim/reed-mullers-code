package com.a5coding.data;

import com.a5coding.calculations.Channel;
import com.a5coding.calculations.Decoder;
import com.a5coding.calculations.Encoder;
import com.a5coding.io.Converter;

public class TextHandler extends DataHandler<String> {

    //Input: input - text and p - error probability
    //Sends the text through the channel without encoding and decoding
    //Output: received text
    @Override
    public String handleWithoutEncoding(String input, double p) {
        channel = new Channel(p);
        String binary = Converter.textToBinaryString(input);
        int[] arr = Converter.stringToIntArray(binary);
        int[] received = channel.send(arr);
        String binaryString = Converter.intArrayToString(received);
        return Converter.binaryStringToText(binaryString);
    }

    //Input: input - text, p - error probability and m - RM(1,m) parameter
    //Encodes the text, sends it though the channel and decodes it
    //Output: received text
    @Override
    public String handleWithEncoding(String input, double p, int m){
        channel = new Channel(p);
        String binary = Converter.textToBinaryString(input);
        String[] dividedArr = divide(binary, m);
        String result = "";

        for(int i = 0; i < dividedArr.length; i++){
            int[] arr = Converter.stringToIntArray(dividedArr[i]);
            int[] encoded = encoder.encode(arr, m);
            int[] received = channel.send(encoded);
            int[] decoded = decoder.decode(received, m);
            String decodedStr = Converter.intArrayToString(decoded);
            if(i == dividedArr.length - 1){
                decodedStr = decodedStr.substring(0, decodedStr.length() - addedZeros);
            }
            result += decodedStr;

        }

        String finalText = Converter.binaryStringToText(result);

        return finalText;
    }
}

