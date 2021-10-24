package ex01;

import java.io.*;
import java.util.*;
import java.lang.Math;

class Program {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Error. Wrong number of arguments");
            System.exit(1);
        }

        try {
            FileReader fileReader1 = new FileReader(args[0]);
            FileReader fileReader2 = new FileReader(args[1]);

            BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
            BufferedReader bufferedReader2 = new BufferedReader(fileReader2);

            Map<String, Integer> dictionary = getDictionary(bufferedReader1, bufferedReader2);

            Integer[] vector1 = getEmptyVector(dictionary.keySet().size());
            Integer[] vector2 = getEmptyVector(dictionary.keySet().size());

            countWordsFromFile(vector1, bufferedReader1, dictionary);
            countWordsFromFile(vector2, bufferedReader2, dictionary);

            calculateSimilarity(vector1, vector2);
            createDictFile(dictionary);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void fillDictionary(Map<String, Integer> dictionary, Map<String, Integer> wordsOfFile) {
       Object[] keys = wordsOfFile.keySet().toArray();

        for (Object key : keys) {
            if (dictionary.get(key) != null) {
                dictionary.put((String)key, dictionary.get(key) + 1);
            } else {
                dictionary.put((String)key, new Integer(1));
            }
        }
    }

    private static Map<String, Integer> getDictionary(BufferedReader bufferedReader1, BufferedReader bufferedReader2) {
        Map<String, Integer> dictionary = new HashMap<>();
        Map<String, Integer> wordsOfFile1 = new HashMap<>();
        Map<String, Integer> wordsOfFile2 = new HashMap<>();

        try {
            bufferedReader1.mark(2000000);
            bufferedReader2.mark(2000000);
            getWorfsOfFile(wordsOfFile1, bufferedReader1);
            getWorfsOfFile(wordsOfFile2, bufferedReader2);
            fillDictionary(dictionary, wordsOfFile1);
            fillDictionary(dictionary, wordsOfFile2);

            if (wordsOfFile1.size() == wordsOfFile2.size() && wordsOfFile1.size() == 0) {
                System.out.println("Similarity = 1");
                createDictFile(dictionary);
                System.exit(0);
            } else if (wordsOfFile1.size() == 0 || wordsOfFile2.size() == 0) {
                System.out.println("Similarity = 0");
                createDictFile(dictionary);
                System.exit(0);
            }
            bufferedReader1.reset();
            bufferedReader2.reset();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return dictionary;
    }

    private static void getWorfsOfFile(Map<String, Integer> dictionary, BufferedReader bufferedReader) {
        Object[] allLines = bufferedReader.lines().toArray();

        for (Object object : allLines) {
            String line = (String)object;
            String[] splittedLine = line.split(" ");
            
            for (String word : splittedLine) {
                if (word.length() == 0) {
                    continue;
                } else if (dictionary.get(word) != null) {
                    dictionary.put(word, dictionary.get(word) + 1);
                } else {
                    dictionary.put(word, new Integer(1));
                }
            }
        }
    }

    private static void countWordsFromFile(Integer[] vector, BufferedReader bufferedReader, 
                                           Map<String, Integer> dictionary) {
        Object[] allLines = bufferedReader.lines().toArray();
        Object[] keys = dictionary.keySet().toArray();
        
        for (Object object : allLines) {
            String line = (String)object;
            String[] splittedLine = line.split(" ");

            for (String word : splittedLine) {

                for (int i = 0; i < keys.length; i++) {
                    if (word.equals(keys[i])) {
                        vector[i] += 1;
                        break;
                    }
                }
            }
        }
    }

    private static void calculateSimilarity (Integer[] vector1, Integer[] vector2) {
        int     numerator = 0;
        int     tmp1 = 0;
        int     tmp2 = 0;
        double  denominator;

        for (int i = 0; i < vector1.length; i++) {
            numerator += vector1[i] * vector2[i];
            tmp1 += vector1[i] * vector1[i];
            tmp2 += vector2[i] * vector2[i];
        }
        denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
        System.out.printf("%.3f\n", numerator / denominator);
    }

    private static Integer[] getEmptyVector(int size) {
        Integer[] vector = new Integer[size];

        for (int i = 0; i < size; i++) {
            vector[i] = 0;
        }
        return vector;
    }

    private static void createDictFile(Map<String, Integer> dictionary) {
        FileOutputStream    dictionaryTxt;
        Object[]            array = dictionary.keySet().toArray();

        Arrays.sort(array);

        try {
            dictionaryTxt = new FileOutputStream("dictionary.txt");

            for (int i = 0; i < array.length; i++) {
                dictionaryTxt.write(array[i].toString().getBytes());
                dictionaryTxt.write(' ');
            }
            dictionaryTxt.write('\n');
            dictionaryTxt.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}