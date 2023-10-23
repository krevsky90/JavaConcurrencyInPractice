package com.company.Renderer_6_3;

import java.util.List;
import java.util.concurrent.*;

/**
 * listing 6_15
 */
public class Renderer extends AbstractRenderer {
    private final ExecutorService executor;

    Renderer(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> info = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);
        for (final AbstractRenderer.ImageInfo imageInfo : info) {
            completionService.submit(() -> imageInfo.downloadImage());
        }
        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
                Future<AbstractRenderer.ImageData> f = completionService.take();
                AbstractRenderer.ImageData imageData = f.get();
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }
}
