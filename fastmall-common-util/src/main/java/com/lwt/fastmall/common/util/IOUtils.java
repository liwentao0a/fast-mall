package com.lwt.fastmall.common.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author lwt
 * @date 2019/12/8 23:03
 */
public class IOUtils {

    public static void close(Closeable closeable) {
        if (closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
