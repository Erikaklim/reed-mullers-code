package com.a5coding.calculations;

public class Encoder {

    private final static int DIVISOR = 2;

    //Input: r - order of Reed Muller's code and m - RM(1, m) parameter
    //Generates the generator matrix using recursion
    //Output: generator matrix for RM(r, m)
    private int[][] generateMatrix(int r, int m){
        if(r == 0){
            return generateHalfLastRow(m, 1);
        }else if (r==1 && m==1) {
            int[][] matrix = {{1,1}, {0,1}};
            return matrix;
        }else{
            int[][] zerosMatrix = generateHalfLastRow(m, 0);
            int[][] onesMatrix = generateMatrix(r-1, m);
            int[][] bottomMatrix = combineTwoMatrixH(zerosMatrix, onesMatrix);
            int[][] halfTopMatrix = generateMatrix(1, m-1);
            int[][] fullTopMatrix = combineTwoMatrixH(halfTopMatrix, halfTopMatrix);
            int[][] result = combineTwoMatrixV(fullTopMatrix, bottomMatrix);

            return result;
        }
    }

    //Input: two integer matrix
    //Two matrix are combined horizontally (matrix1 is on the left and matrix2 is on the right
    //of the combined matrix)
    //Output: combined matrix
    private int[][] combineTwoMatrixH(int[][] matrix1, int[][] matrix2){
        int rows = matrix1.length;
        int columns = matrix1[0].length + matrix2[0].length;
        int[][] result = new int[rows][columns];

        for(int i = 0; i < rows; i++){
            System.arraycopy(matrix1[i], 0, result[i], 0, matrix1[i].length);
            System.arraycopy(matrix2[i], 0, result[i], matrix1[i].length, matrix2[i].length);
        }

        return result;
    }

    //Input: two integer matrix
    //Two matrix are combined vertically (matrix1 is at the top and matrix2 at the bottom part
    //of the combined matrix)
    //Output: combined matrix
    private int[][] combineTwoMatrixV(int[][] matrix1, int[][] matrix2){
        int rows = matrix1.length + matrix2.length;
        int columns = matrix1[0].length;
        int[][] result = new int[rows][columns];

        System.arraycopy(matrix1, 0, result, 0, matrix1.length);
        System.arraycopy(matrix2, 0, result, matrix1.length, matrix2.length);

        return result;
    }

    //Input: m - RM(1, m) parameter and num - 1 or 0
    // 1 or 0 is repeated a specific amount of times depending on the m parameter to
    //form a new matrix
    //Output: matrix 1x2^(m-1) containing either all 1s or all 0s
    private int[][] generateHalfLastRow(int m, int num){
        int length = (int)Math.pow(2, m-1);
        int[][] matrix = new int[1][length];
        for(int i = 0; i < length; i++){
            matrix[0][i] = num;
        }

        return matrix;
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
            c[i] = c[i] % 2;
        }

        return c;
    }


    //Input: message to encode, m - R(1,m) parameter
    //Gets the generator matrix and multiplies it by the input message - encodes the message
    //Output: encoded message
    public int[] encode(int[] message, int m) {
        int[][] matrix = generateMatrix(1, m);
        return multiplyMatrixAndVector(matrix, message);
    }


}
