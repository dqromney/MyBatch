package com.stg.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Download utility class.
 * <p>
 * Created by dqromney on 3/4/17.
 */
public class Download {

    /**
     * Download file using a Stream Library.
     *
     * @param urlStr a {@link String} representing the URL
     * @param file   the {@link String} representing the target output file
     * @throws IOException
     */
    public static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    /**
     * Download file using a Network I/O library or NIO.
     *
     * @param urlStr a {@link String} representing the URL
     * @param file   the {@link String} representing the target output file
     * @throws IOException
     */
    public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        if (!new File(file).exists()) {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
        }
    }
}
