package ex03;

import java.lang.Runnable;
import java.nio.channels.*;
import java.io.*;
import java.net.*;

public class DownloadFile implements Runnable {
    String  urlArray[];
    Object  lock;
    int     threadIndex;
    int     urlArraySize;

    DownloadFile(String urlArray[], Object lock, int i, int urlArraySize) {
        this.urlArray = urlArray;
        this.lock = lock;
        this.threadIndex = i;
        this.urlArraySize = urlArraySize;
    }

    private String getFileName(String url) {
        String tmp[];

        tmp = url.split("/");
        return ("downloaded_" + tmp[tmp.length - 1]);
    }

    private HttpURLConnection getUrlConnection(String url) {
        URL                 website;
        HttpURLConnection   connection = null;
        
        try {
            website = new URL(url);
            connection = (HttpURLConnection)website.openConnection();
            connection.setRequestProperty("User-Agent", "Test");
            connection.setInstanceFollowRedirects(false);
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return connection;
    }

    private void readFromUrlConnection(HttpURLConnection connection, String fileName, int fileNumber) {
        ReadableByteChannel rbc;
        FileOutputStream    fos;
        
        try {
            rbc = Channels.newChannel(connection.getInputStream());
            fos = new FileOutputStream(fileName);
            System.out.printf("Thread-%d start download file number %d\n", this.threadIndex, fileNumber);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            connection.disconnect();
            rbc.close();
            fos.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private void downloadFile(String url, int fileNumber) {
        HttpURLConnection   connection = null;
        String              redirectLocation;
        
        connection = getUrlConnection(url);
        redirectLocation = connection.getHeaderField("Location");
        
        if (redirectLocation != null) {
            connection = getUrlConnection(redirectLocation);
        };
        readFromUrlConnection(connection, getFileName(url), fileNumber);
        System.out.printf("Thread-%d finish download file number %d\n", this.threadIndex, fileNumber);
    };
    
    public void run () {
        boolean flag = true;
        String  url;
        int     i;
        
        while (true) {
            url = null;

            synchronized (lock) {
                for (i = 0; i < this.urlArraySize; i++) {
                    if (this.urlArray[i] != null) {
                        url = this.urlArray[i];
                        this.urlArray[i] = null;
                        break;
                    }
                }

                if (i == this.urlArraySize) {
                    return ;
                }
            }

            if (url != null) {
                downloadFile(url, i + 1);
            }
        }
    }
}
