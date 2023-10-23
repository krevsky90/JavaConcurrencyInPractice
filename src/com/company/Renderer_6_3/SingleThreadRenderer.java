package com.company.Renderer_6_3;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadRenderer extends AbstractRenderer {
    public void renderPage(CharSequence source) {
        renderText(source);
        List<AbstractRenderer.ImageData> imageData = new ArrayList<AbstractRenderer.ImageData>();
        for (AbstractRenderer.ImageInfo imageInfo : scanForImageInfo(source))
            imageData.add(imageInfo.downloadImage());
        for (AbstractRenderer.ImageData data : imageData)
            renderImage(data);
    }






}
