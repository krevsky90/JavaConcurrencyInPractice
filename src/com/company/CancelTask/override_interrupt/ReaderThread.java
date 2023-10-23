package com.company.CancelTask.override_interrupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Листинг 7.11 Инкапсулирование нестандартной отмены в потоке путем переопределения метода interrupt
 */
public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    public void interrupt() {
        try {
            //несмотря на то, что InputStream#read(...) имеет нерперываемую блокировку, если закрыть сокет, то read бросит IOException
            //этим мы и пользуемся, чтоб прервать процесс чтения
            socket.close();
        } catch (IOException ignored) {
        } finally {
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[100500];
            while (true) {
                int count = in.read(buf);
                if (count < 0)
                    break;
                else if (count > 0) {
                    // do smth
                    // processBuffer(buf, count);
                }
            }
        } catch (IOException e) {
            // Allow thread to exit
        }
    }
}
