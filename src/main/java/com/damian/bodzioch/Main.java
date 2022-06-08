package com.damian.bodzioch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        char[][] chessboardTab = new char[8][8];
        readChessboardFromFile(chessboardTab);
        if (isKingAlive(chessboardTab)){
            System.out.println("N");
        }else {
            System.out.println("Y");
        }

    }

    public static void readChessboardFromFile(char[][] tab){
        BufferedReader reader = null;

        try {
           reader = new BufferedReader(new FileReader("szachownica.txt"));
        }catch (FileNotFoundException err){
            System.out.println("Nie znaleziono pliku");
            System.exit(0);
        }

        for (int i = 0; i < tab.length; i++){
            try {
                tab[i] = reader.readLine().toCharArray();
            }catch (IOException err){
                System.out.println("Nie odczytano linii!");
            }
        }

        try {
            reader.close();
        }catch (IOException err){
            System.out.println("Nie udało się zamknąć pliku!");
        }

    }

    public static boolean isKingAlive(char[][] tab){
        String kingPosition = null;

        if((kingPosition = findKing(tab)) == null){
            System.out.println("Nie znaleziono króla!");
            System.exit(0);
        }

        ArrayList<String> queensPositions = findQueens(tab);
        if (queensPositions.size() == 0){
            System.out.println("Nie znaleziono królowych!");
            System.exit(0);
        }
        //listowanie pozycji w pionie
        ArrayList<Integer> dangerousQueensPositions = new ArrayList<>();
        int dangerousPosition = Character.getNumericValue(kingPosition.charAt(0)) * 10 + 1;
        while (dangerousPosition <= Character.getNumericValue(kingPosition.charAt(0)) * 10 + 8){
            dangerousQueensPositions.add(dangerousPosition);
            dangerousPosition++;
        }
        // listowanie pozycji w poziomie
        dangerousPosition = 10 + Character.getNumericValue(kingPosition.charAt(1));
        while (dangerousPosition <= 88){
            dangerousQueensPositions.add(dangerousPosition);
            dangerousPosition += 10;
        }

        //listowanie pierwszej przekątnej
        if (Character.getNumericValue(kingPosition.charAt(0)) >= Character.getNumericValue(kingPosition.charAt(1))){
            int secondDigit = Character.getNumericValue(kingPosition.charAt(1)) - 1;
            int firstDigit = Character.getNumericValue(kingPosition.charAt(1)) - secondDigit;
            dangerousPosition = firstDigit * 10 + 1;
            while (dangerousPosition < 88){
                dangerousQueensPositions.add(dangerousPosition);
                dangerousPosition += 11;
            }

            secondDigit = 8 - Character.getNumericValue(kingPosition.charAt(0)) +
                    Character.getNumericValue(kingPosition.charAt(1));
            firstDigit = 8;
            dangerousPosition = firstDigit * 10 + secondDigit;
            while (dangerousPosition > 11){
                dangerousQueensPositions.add(dangerousPosition);
                dangerousPosition -= 11;
            }
        }else if (Character.getNumericValue(kingPosition.charAt(0)) < Character.getNumericValue(kingPosition.charAt(1))){
            int firstDigit = Character.getNumericValue(kingPosition.charAt(0)) - 1;
            int secondDigit = Character.getNumericValue(kingPosition.charAt(1)) - firstDigit;
            dangerousPosition = 10 + secondDigit;
            while (dangerousPosition < 88){
                dangerousQueensPositions.add(dangerousPosition);
                dangerousPosition += 11;
            }

            firstDigit = 8 - Character.getNumericValue(kingPosition.charAt(1)) +
                    Character.getNumericValue(kingPosition.charAt(0));
            secondDigit = 8;
            dangerousPosition = firstDigit * 10 + secondDigit;
            while (dangerousPosition > 11){
                dangerousQueensPositions.add(dangerousPosition);
                dangerousPosition -= 11;
            }
        }

        for (String element : queensPositions){
            for (int element2 : dangerousQueensPositions){
                if (element.equals(Integer.toString(element2))){
                    return false;
                }
            }
        }
        return true;
    }

    public static String findKing(char[][] tab){
        for (int i = 1; i <= tab.length; i++){
            for (int j = 1; j <= tab[i - 1].length; j++){
                if(tab[i - 1][j - 1] == 'K'){
                    return Integer.toString(j * 10 + i);
                }
            }
        }
        return null;
    }

    public static ArrayList<String> findQueens(char[][] tab){
        ArrayList<String> queensPositions = new ArrayList<>();

        for (int i = 1; i <= tab.length; i++){
            for (int j = 1; j <= tab[i - 1].length; j++){
                if(tab[i - 1][j - 1] == 'Q'){
                    queensPositions.add(Integer.toString(j * 10 + i));
                }
            }
        }
        return queensPositions;
    }
}
