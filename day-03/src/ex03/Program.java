package ex03;

import java.io.*;

class Program {
    private static int getTthreadsCount(String str) {
        String  array[] = str.split("=");
        int     n = 0;

        if ((array.length != 2) || (!array[0].equals("--threadsCount"))) {
            System.err.println("Error. Wrong argument. Example: java Program --threadsCount=3");
            System.exit(1);
        }

        try {
            n = Integer.parseInt(array[1]);
        } catch (NumberFormatException e) {
            System.err.print("Error. ");
            System.err.println(e.getMessage());
            System.err.println("Example: java Program --threadsCount=3");
            System.exit(1);
        }

        if (n <= 0) {
            System.err.println("Error. ThreadsCount cannot be less than 1");
            System.exit(1);
        }
        return n;
    }

    private static String[] getUrlsFromFile() {
        String[]        urlArray;
        FileInputStream f;
        byte[]          buffer;
        int             size;

        try {
            f = new FileInputStream("files_urls.txt");
            size = f.available();
            buffer = new byte[size];
            f.read(buffer);
            urlArray = new String(buffer).split("\n");
            f.close();
            return urlArray;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    };

    public static void main(String[] args) {
        Thread[]    threads;
        int         threadsCount;
        String[]    urlArray;
        int         urlArraySize;
        Object      lock = new Object();

        if (args.length != 1) {
            System.err.println("Wrong number of arguments");
            System.exit(1);
        }
        threadsCount = getTthreadsCount(args[0]);
        urlArray = getUrlsFromFile();
        urlArraySize = urlArray.length;
        threads = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(new DownloadFile(urlArray, lock, i + 1, urlArraySize));
            threads[i].start();
        }

        for (int i = 0; i < threadsCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
    }
}