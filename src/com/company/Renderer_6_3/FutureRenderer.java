package com.company.Renderer_6_3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FutureRenderer extends AbstractRenderer {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    void renderPage(CharSequence source) {
        final List<ImageInfo> imageInfos = scanForImageInfo(source);
//        use java < 1.8
//        Callable<List<ImageData>> task =
//                new Callable<List<ImageData>>() {
//                    public List<ImageData> call() {
//                        List<ImageData> result = new ArrayList<ImageData>();
//                        for (ImageInfo imageInfo : imageInfos) {
//                            result.add(imageInfo.downloadImage());
//                        }
//                        return result;
//                    }
//                };

        //use Java 1.8
        Callable<List<ImageData>> task = () -> {
            List<ImageData> result = new ArrayList<ImageData>();
            for (ImageInfo imageInfo : imageInfos) {
                result.add(imageInfo.downloadImage());
            }
            return result;
        };

        Future<List<ImageData>> future = executor.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            // Re-assert the thread’s interrupted status
            Thread.currentThread().interrupt();
            // We don’t need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }
}
