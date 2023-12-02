package com.a5coding.data;

import com.a5coding.calculations.Channel;
import com.a5coding.data.image.ImageData;
import com.a5coding.io.Converter;
import com.a5coding.io.Validator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler extends DataHandler {

    //Input: input - image filepath and p - error probability
    //Sends the image through the channel without encoding and decoding
    //Output: filepath of the received image
    @Override
    public String handleWithoutEncoding(String input, double p) {
        channel = new Channel(p);
        ImageData imageData = Converter.imageToBinary(input);
        int[] received = channel.send(Converter.stringToIntArray(imageData.binaryData));
        imageData.binaryData = Converter.intArrayToString(received);
        String outputPath = "C:/Users/Erika/IdeaProjects/A5coding/src/main/java/com/a5coding/output_image.bmp";
//        String workingDir = System.getProperty("user.dir");
//        String outputPath = workingDir + File.separator + "output_image.bmp";
        writeToFile(imageData, outputPath);
        return outputPath;

    }

    //Input: input - image filepath, p - error probability and m - RM(1,m) parameter
    //Encodes the image, sends it though the channel and decodes it
    //Output: filepath of the received image
    @Override
    public String handleWithEncoding(String input, double p, int m) {
        channel = new Channel(p);
        ImageData imageData = Converter.imageToBinary(input);;
        String[] dividedArr = divide(imageData.binaryData, m);
        String result = "";
        for (int i = 0; i < dividedArr.length; i++) {
            int[] arr = Converter.stringToIntArray(dividedArr[i]);
            int[] encoded = encoder.encode(arr, m);
            int[] received = channel.send(encoded);
            int[] decoded = decoder.decode(received, m);
            String decodedStr = Converter.intArrayToString(decoded);
            if (i == dividedArr.length - 1) {
                decodedStr = decodedStr.substring(0, decodedStr.length() - addedZeros);
            }
            result += decodedStr;
        }

        imageData.binaryData = result;
//        String workingDir = System.getProperty("user.dir");
//        String outputPath = workingDir + File.separator + "output_image_decoded.bmp";
        String outputPath = "C:/Users/Erika/IdeaProjects/A5coding/src/main/java/com/a5coding/output_image_decoded.bmp";
        writeToFile(imageData, outputPath);
        return outputPath;
    }

    //Input: object ImageData and outputPath
    //Gets the image from the ImageData binary string and writes to a new file
    private void writeToFile(ImageData imageData, String outputPath){
        BufferedImage image = Converter.binaryToImage(imageData);
        try{
            File outputFile = new File(outputPath);
            ImageIO.write(image, "bmp", outputFile);
        }catch (
        IOException e){
            Validator.errorAlert("Error while writing the file!");
        }
    }
}
