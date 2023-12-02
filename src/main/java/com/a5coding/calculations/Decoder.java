package com.a5coding.calculations;

import com.a5coding.io.Converter;

import java.util.ArrayList;
import java.util.List;

public class Decoder {
    private static final int[][] H = {{1,1},{1,-1}};

    //Input: y - received vector, m - RM(1, m) parameter
    //Decodes the received vector
    //Output: decoded vector
    public int[] decode(int[] y, int m){
        y = replaceOnes(y);
        int[] vector = getVectorForDecoding(y, m);
        int position = getLargestAbsoluteValuePosition(vector);
        return getDecodedMessage(position, m, vector);
    }

    //Input: received and sent vectors
    //Compares the elements of two vectors and if the elements do not match
    //adds the following element to the positions list
    //Output: list of positions where the errors occurred
    public List<Integer> getErrorPositions(int[] received, int[] sent){
        List<Integer> positions = new ArrayList<>();
        for(int i = 0; i < received.length; i++){
            if(received[i] != sent[i]){
                positions.add(i);
            }
        }

        return positions;
    }

    //Input: vector
    //In the vector, finds and returns the position of the element with the largest absolute value
    private int getLargestAbsoluteValuePosition(int[] vector){
        int position = -1;
        int largest = 0;

        for(int i = 0; i < vector.length; i++){
            int absoluteValue = Math.abs(vector[i]);
            if(absoluteValue > largest){
                largest = absoluteValue;
                position = i;
            }
        }

        return position;
    }

    //Input: vector
    //0s are replaced in the vector by -1s
    private int[] replaceOnes(int[] vector){
        for(int i = 0; i < vector.length; i++){
            if(vector[i] == 0){
                vector[i] = -1;
            }
        }
        return vector;
    }

    //Input: num - the position of the element in the input vector with the largest absolute value,
    //m - RM(1,m) parameter and a vector
    //The position is converted into a binary string and depending on whether the value in that
    //position is positive or negative a leading 1 or 0 is added.
    //Output: decoded message
    private int[] getDecodedMessage(int num, int m, int[] vector){

        String binary = Converter.intToBinaryString(num, m);

        if(vector[num] >= 0){
            binary = "1" + binary;
        }else{
            binary = "0" + binary;
        }

        int[] decodedMessage = Converter.stringToIntArray(binary);

        return  decodedMessage;
    }

    //Input: two matrix
    //Calculates and returns the Kronecher's product of the two matrix
    private int[][] calculateKronecherProduct(int[][] matrix1, int[][] matrix2){
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;

        int[][] result= new int[rows1 * rows2][cols1 * cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols1; j++) {
                for (int k = 0; k < rows2; k++) {
                    for (int l = 0; l < cols2; l++) {
                        result[i * rows2 + k][j * cols2 + l] = matrix1[i][j] * matrix2[k][l];
                    }
                }
            }
        }

        return result;

    }

    //Input: dimension for the identity matrix
    //Generates and return an identity matrix of size dimension x dimension
    private int[][] generateIdentityMatrix(int dimension){
        int[][] matrix = new int[dimension][dimension];

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(i == j){
                    matrix[i][j] = 1;
                }else{
                    matrix[i][j] = 0;
                }
            }
        }

        return matrix;
    }

    //Input: i - numeration of Hadamard matrix (i = 1, 2,...m) and m - RM(1,m) parameter
    //Computes and return Hadamard matrix H(i,m)
    private int[][] getHadamardMatrix(int i, int m){
        int[][] identityMatrix1 = generateIdentityMatrix((int)Math.pow(2, m-i));
        int[][] identityMatrix2 = generateIdentityMatrix((int)Math.pow(2, i-1));

        int[][] temp = calculateKronecherProduct(identityMatrix1, H);
        return calculateKronecherProduct(temp, identityMatrix2);
    }

    //Input: vector and m - RM(1,m) parameter
    //Generates and returns a new vector by multiplying the current vector with the
    //Hadamard matrix(i,m) until i = m
    private int[] getVectorForDecoding(int[] vector, int m){
        int i = 1;
        while(i <= m){
            vector = multiplyMatrixAndVector(getHadamardMatrix(i, m), vector);
            i++;
        }
        return vector;
    }

    //Input: matrix, vector and num - number for division
    //Multiplies vector and matrix
    //Output: resulting vector
    public int[] multiplyMatrixAndVector(int[][] matrix, int[] vector){
        int[] c = new int[matrix[0].length];

        for(int i = 0; i < matrix[0].length; i++){
            for(int j = 0; j < vector.length; j++){
                c[i] += (vector[j]*matrix[j][i]);
            }
        }

        return c;
    }
}
