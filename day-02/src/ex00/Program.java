package ex00;

import java.util.*;
import java.io.*;
import java.util.Scanner;

class Program {
    private static String makeStringFromArray(Object array[]) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
        }
        return sb.toString();
    }

    private static String parseFile(String fileName) {
        LinkedList<String>  list = new LinkedList<String>();
        FileInputStream     f;
        int                 size;
        
        try {
            f = new FileInputStream(fileName);
            size = f.available();
            
            for (int i = 0; i < size; i++) {
                list.add(Integer.toHexString(f.read()));
            }
            f.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return makeStringFromArray(list.toArray());
    }

    private static String[][] getArraySignatures(Object[] s) {
        String splittedSignatures[][] = new String[s.length + 1][2];
        String tmp[];

        for (int i = 0; i < s.length; i++) {
            tmp = s[i].toString().split(",");
            splittedSignatures[i][0] = tmp[0];
            splittedSignatures[i][1] = tmp[1];
            splittedSignatures[i][1] = splittedSignatures[i][1].replaceAll(" 0", "");
            splittedSignatures[i][1] = splittedSignatures[i][1].replaceAll(" ", "").toLowerCase();
        }
        splittedSignatures[s.length] = null;
        return splittedSignatures;
    }

    private static String[][] getSignatures() {
        LinkedList<String>  signatures = new LinkedList<String>();
        File                file = new File("signatures.txt");
        Scanner             scanner;
        String              tmp;

        try {
            scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                tmp = scanner.nextLine();
                
                if (tmp.length() != 0) {
                    signatures.add(tmp);
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        getArraySignatures(signatures.toArray());
        return getArraySignatures(signatures.toArray());
    }

    private static int getPositionOfFoundString(String parsedFile, String signature) {
        int j = 0;

        for (int i = 0; i < parsedFile.length(); i++) {

            for (int k = i; k < parsedFile.length(); k++) {
                if (parsedFile.charAt(k) != signature.charAt(j)) {
                    j = 0;
                    break ;
                } else if (j == signature.length() - 1) {
                    return i;
                }
                j++;
            }
        }
        return 0;
    }

    private static String getSignatureType(String[][] signatures, String parsedFile) {
        if (parsedFile == null) {
            return null;
        }

        for (int i = 0; signatures[i] != null; i++) {
            if (parsedFile.contains(signatures[i][1]) &&
                    getPositionOfFoundString(parsedFile, signatures[i][1]) == 0) {
                    System.out.println("PROCESSED");
                    return signatures[i][0];
            }
        }
        System.out.println("UNDEFINED");
        return null;
    }

    private static void writeResult(LinkedList<String> result) {
        FileOutputStream    resultTxt;
        Object              array[];

        try {
            resultTxt = new FileOutputStream("result.txt");
            array = result.toArray();
            
            for (int i = 0; i < result.size(); i++) {
                if (array[i] != null) {
                    resultTxt.write(array[i].toString().getBytes());
                    resultTxt.write('\n');
                }
            }
            resultTxt.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    };

    public static void main(String[] args) {
        String[][]          signatures;
        String              parsedFile;
        LinkedList<String>  result = new LinkedList<String>();
        Scanner             scanner;
        String              fileName;

        try {
            scanner = new Scanner(System.in);
            signatures = getSignatures();

            while (true) {
                fileName = scanner.nextLine();

                if (fileName.equals("42")) {
                    break;
                }
                parsedFile = parseFile(fileName);
                result.add(getSignatureType(signatures, parsedFile));
            }
            writeResult(result);
            scanner.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
