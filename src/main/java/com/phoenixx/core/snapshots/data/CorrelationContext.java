package com.phoenixx.core.snapshots.data;

import com.phoenixx.core.snapshots.correlation.CorrelationManager;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 1:41 PM [29-05-2023]
 */
public class CorrelationContext {

    /**
     * We store two versions of the keys and values because each refers to the object we're matching and matching against
     */
    public final String correlationKeyOne;
    public final String correlationKeyTwo;

    public final String correlationValOne;
    public final String correlationValTwo;

    public final int accuracy;

    public final CorrelationManager.CorrelationType correlationType;

    public CorrelationContext(String correlationKeyOne, String correlationKeyTwo, String correlationValOne, String correlationValTwo, int accuracy, CorrelationManager.CorrelationType correlationType) {

        this.correlationKeyOne = correlationKeyOne;
        this.correlationKeyTwo = correlationKeyTwo;
        this.correlationValOne = correlationValOne;
        this.correlationValTwo = correlationValTwo;
        this.accuracy = accuracy;
        this.correlationType = correlationType;
    }
}
