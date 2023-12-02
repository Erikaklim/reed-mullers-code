package com.a5coding.io;

import com.a5coding.data.image.Header;
import com.a5coding.data.image.ImageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class Converter {

    public final static int RADIX = 2;
    public final static int NUMBER_OF_BITS = 8;

    //Converts input string into an integer array
    public static int[] stringToIntArray(String string){
        String[] strArr = string.split("");
        int[] intArr = new int[strArr.length];

        for(int i = 0; i < strArr.length; i++){
            intArr[i] = Integer.parseInt(strArr[i]);
        }

        return intArr;

    }

    //Converts input integer array to a string
    public static String intArrayToString(int[] ints){
        String string = "";
        for (int value : ints) {
            string += Integer.toString(value);
        }

        return string;
    }

    //Input: num - integer, m - RM(1, m) parameter
    //Converts an integer to a binary string of length m
    //If needed, ads trailing zeros
    public static String intToBinaryString(int num, int m){
        StringBuilder binaryBuilder = new StringBuilder();

        while(num > 0 || binaryBuilder.length() < m){
            //extract the least significant bit
            int bit = num & 1;
            binaryBuilder.append(bit);
            //right shift to process the next bit
            num >>= 1;
        }

        while(binaryBuilder.length() < m){
            binaryBuilder.insert(0, '0');
        }

        return binaryBuilder.toString();
    }

    //Converts input text into a binary string. If needed, adds leading zeros.
    public static String textToBinaryString(String text){
        StringBuilder stringBuilder = new StringBuilder();
        for(char c : text.toCharArray()){
            String binary = Integer.toBinaryString(c);
            while(binary.length() < 8){
                binary = "0" + binary;
            }
            stringBuilder.append(binary);
        }

        return stringBuilder.toString();
    }

    //Converts input binary string to text
    public static String binaryStringToText(String binary){
        StringBuilder stringBuilder = new StringBuilder();
        String[] binaryArr = binary.split("(?<=\\G.{"+ NUMBER_OF_BITS+ "})");

        for(String str : binaryArr){
            int decimal = Integer.parseInt(str,RADIX);
            char c = (char) decimal;
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    //Input: image path
    //Converts image into a binary representation
    //Output: object ImageData, which contains header information and the binary string
    public static ImageData imageToBinary(String imagePath){
        File inputFile = new File(imagePath);
        try {
            BufferedImage image = ImageIO.read(inputFile);

            ImageData imageData = new ImageData();
            imageData.header = new Header();
            imageData.header.width = image.getWidth();
            imageData.header.height = image.getHeight();

            StringBuilder binaryStringBuilder = new StringBuilder();
            for (int y = 0; y < imageData.header.height; y++) {
                for (int x = 0; x < imageData.header.width; x++) {
                    int rgb = image.getRGB(x, y);
                    String binaryString = Integer.toBinaryString(rgb);
                    binaryStringBuilder.append(binaryString.substring(binaryString.length() - 24)); // Assuming 24-bit RGB
                }
            }
            imageData.binaryData = binaryStringBuilder.toString();
            return imageData;
        }catch (IOException e){
            Validator.errorAlert("Error while reading the file!");
        }
        return  null;
    }

    //Input: object ImageData, which contains header information and the binary string
    //Reconstructs the image from the binary string and header information
    //Output: buffered image
    public static BufferedImage binaryToImage(ImageData imageData){
        BufferedImage image = new BufferedImage(imageData.header.width, imageData.header.height, BufferedImage.TYPE_INT_RGB);

        int index = 0;
        for (int y = 0; y < imageData.header.height; y++) {
            for (int x = 0; x < imageData.header.width; x++) {
                String binaryString = imageData.binaryData.substring(index, index + 24); // Assuming 24-bit RGB
                int rgb = Integer.parseInt(binaryString, 2);
                image.setRGB(x, y, rgb);
                index += 24;
            }
        }

        return image;
    }
}
