package com.phoenixx.core.loader;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 11:07 a.m. [02-05-2023]
 */
public class FileLoader {
    private final ExecutorService executor;

    public FileLoader(int numThreads) {
        this.executor = Executors.newFixedThreadPool(numThreads);
    }

    public void loadFile(File file, IParser parser) {
        Runnable task = () -> parser.parse(file);
        this.executor.execute(task);
    }

    public void shutdown() {
        this.executor.shutdown();
        try {
            if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                this.executor.shutdownNow();
                if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    //TODO Add singleton based LogManager
                    System.err.println("FileLoader: ThreadPool did not terminate");
                }
            }
        } catch (InterruptedException e) {
            this.executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
