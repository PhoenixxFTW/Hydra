package com.phoenixx.ui.components.slider;

import com.jfoenix.controls.JFXSlider;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.StringConverter;

import java.io.IOException;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 4:13 PM [24-05-2023]
 */
public class CustomSliderOption extends VBox {
    public SliderOption sliderOption;
    public Label jfxLabel;
    public JFXSlider jfxSlider;

    public CustomSliderOption() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hydra/fxml/components/CustomComp.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        // ONLY USED FOR IMPORTING THIS INTO SCENE BUILDER
        //fxmlLoader.setClassLoader(getClass().getClassLoader());

        this.jfxLabel = new Label("Slider name");
        this.jfxLabel.setFont(Font.font("system", FontPosture.REGULAR, 15));
        this.jfxLabel.setStyle("-fx-text-fill: white");

        this.jfxSlider = new JFXSlider(0,5, 2);
        this.jfxSlider.setShowTickLabels(true);
        this.jfxSlider.setShowTickMarks(true);
        this.jfxSlider.setMajorTickUnit(6);
        this.jfxSlider.setMinorTickCount(1);
        this.jfxSlider.setCursor(Cursor.HAND);

        jfxSlider.setSkin(new CustomSliderSkin(jfxSlider));

        this.setStyle("-fx-background-color: #22272e");
        this.setPrefWidth(400);
        this.setPrefHeight(60);
        this.getStylesheets().add("/hydra/fxml/css/fullpackstyling.css");

        this.getChildren().add(this.jfxLabel);
        this.getChildren().add(this.jfxSlider);

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setSliderOption(final SliderOption sliderOption) {
        this.sliderOption = sliderOption;

        this.jfxSlider.setMin(sliderOption.min);
        this.jfxSlider.setMax(sliderOption.max);
        this.jfxSlider.setValue(((Number) sliderOption.value).doubleValue());
        this.jfxLabel.setText(sliderOption.name);
        this.jfxSlider.setMajorTickUnit(sliderOption.majorTickCount);
        this.jfxSlider.setMinorTickCount(sliderOption.minorTickCount);
        this.jfxSlider.setSnapToTicks(sliderOption.snapToTick);
        this.jfxSlider.setBlockIncrement(sliderOption.increment.doubleValue());

        if (sliderOption.maxWidth != 0) {
            this.jfxSlider.setPrefWidth(sliderOption.maxWidth);
            this.jfxSlider.setMinWidth(sliderOption.maxWidth);
            this.jfxSlider.setMaxWidth(sliderOption.maxWidth);
        }

        if (sliderOption.increment instanceof Float || sliderOption.increment instanceof Double) {
            this.jfxSlider.setLabelFormatter(new SliderLabelFormatter());
        }

        // Update slider value
        this.jfxSlider.valueProperty().addListener((observable, oldValue, newValue) -> sliderOption.value = Math.round(this.jfxSlider.getValue() * 10) / 10.0);
    }

    @FXML
    public void initialize() {

    }

    public static class SliderLabelFormatter extends StringConverter<Double> {
        @Override
        public String toString(Double object) {
            return String.valueOf(Math.round(object * 10) / 10.0);
        }

        @Override
        public Double fromString(String string) {
            return Math.round(Double.parseDouble(string) * 10) / 10.0;
        }
    }

    public static class SliderOption {
        public final String name;
        public Number value;
        public final double min;
        public final double max;
        public final Number increment;

        public double maxWidth;
        public int majorTickCount;
        public int minorTickCount;
        public boolean snapToTick;

        public SliderOption(String name, Number value, double min, double max, Number increment, int majorTickCount, int minorTickCount, double maxWidth, boolean snapToTick) {
            this.name = name;
            this.value = value;
            this.min = min;
            this.max = max;
            this.increment = increment;
            this.majorTickCount = majorTickCount;
            this.minorTickCount = minorTickCount;
            this.maxWidth = maxWidth;
            this.snapToTick = snapToTick;
        }
    }
}

