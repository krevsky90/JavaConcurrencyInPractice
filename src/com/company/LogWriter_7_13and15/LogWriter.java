package com.company.LogWriter_7_13and15;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Листинг 7.13 Служба логирования производитель-потребитель без поддержки завершения работы
 *
 * Операция логгирования перемещена в отдельный поток. LogWriter передает сообщения логгирующему потоку через BlockingQueue.
 * Мб несколько производителей и один потребитель. Если логгирующий поток отстает, то BlockingQueue ЗАБЛОКИРУЕТ производителей
 *
 * если мы завершим логгирующий поток (т.к. метод queue#take() чувствителен к прерыванию).
 * если логгирующий поток будет завершать работу при перехвате InterruptedException, то прерывание логгирующего потока ОСТАНОВИТ РАБОТЫ СЛУЖБЫ!
 *
 * ПРОБЛЕМА: такое резкое прерывание логгирующего потока
 * 1) приведет к сбросу сообщений, ожидающих записи в лог
 * 2) потоки-производители, заблокированные в методе log, НИКОГДА НЕ БУДУТ РАЗБЛОКИРОВАНЫ!
 *
 * ИТОГО: необходима отмена активности и потребителя, и производителя!
 */
public class LogWriter {
    private static final int CAPACITY = 100;

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;

    public LogWriter(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        private LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        public void run() {
            try {
                while (true)
                    writer.println(queue.take());
            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }
    }
}
