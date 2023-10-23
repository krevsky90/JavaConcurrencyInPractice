
package com.company.listing5_12_FutureTask;

/**
 * IF LOADING_DURATION < MAIN_ACTIONS_DURATION then PreLoader works in parallel with main thread -> we save LOADING_DURATION time
 * IF LOADING_DURATION >= MAIN_ACTIONS_DURATION then PreLoader works in parallel with main thread -> we save MAIN_ACTIONS_DURATION time
 * (but need wait loading process (LOADING_DURATION - MAIN_ACTIONS_DURATION) time)
 *
 */
public class FutureTaskTest {
    public static final long LOADING_DURATION = 2000;
    public static final long MAIN_ACTIONS_DURATION = 3000;

    public static void main(String[] args) throws InterruptedException {
        final Preloader preloader = new Preloader();
        preloader.start();

        System.out.println("start main smth. time = " + System.nanoTime());
        Thread.sleep(MAIN_ACTIONS_DURATION);
        System.out.println("finish main smth. time = " + System.nanoTime());
        try {
            ProductInfo productInfo = preloader.get();  // no matter what will be returned
            System.out.println("productInfo is got. time = " + System.nanoTime());
        } catch (DataLoadException e) {
            System.out.println(e.getMessage());
        }
    }
}
