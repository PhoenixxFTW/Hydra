package com.phoenixx.core.loader;

import com.phoenixx.core.loader.parser.AbstractParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class FileLoader<T extends AbstractParser<?>> {
    private final T parser;
    private final ExecutorService executor;

    private Consumer<T> onFinish;

    //TODO Add callback so that once a file is loaded, we can do something with it
    public FileLoader(T parser, int numThreads) {
        this.parser = parser;
        this.executor = Executors.newFixedThreadPool(numThreads);
    }

    public void loadFiles(String[] fileNames) {
        for (String fileName: fileNames) {
            this.executor.execute(() -> {
                try (InputStream inputStream = Files.newInputStream(Paths.get(fileName))) {
                    this.parser.parse(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // Callback with the parser passed in so the parent class can do whatever with the current file
            if(this.onFinish != null) {
                this.onFinish.accept(this.parser);
            }
        }
        this.executor.shutdown();
        try {
            if (!this.executor.awaitTermination(60, TimeUnit.SECONDS)) {
                this.executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            this.executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void setOnFinish(Consumer<T> onFinish) {
        this.onFinish = onFinish;
    }

    public T getParser() {
        return parser;
    }
}