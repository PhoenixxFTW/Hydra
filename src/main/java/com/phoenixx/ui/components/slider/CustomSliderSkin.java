package com.phoenixx.ui.components.slider;

import com.jfoenix.controls.JFXSlider;
import com.sun.javafx.scene.control.skin.SliderSkin;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.css.PseudoClass;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 5:17 PM [24-05-2023]
 *
 * Modified version of JFoenix's JFXSlider skin found here: {@link com.jfoenix.skins.JFXSliderSkin}
 * This version flips the animated thumb to be shown from the bottom of the slider bar instead of the top
 *
 */
public class CustomSliderSkin extends SliderSkin {
    private final Pane mouseHandlerPane = new Pane();
    private static final PseudoClass MIN_VALUE = PseudoClass.getPseudoClass("min");
    private static final PseudoClass MAX_VALUE = PseudoClass.getPseudoClass("max");

    private Text sliderValue;
    private StackPane coloredTrack;
    private StackPane thumb;
    private StackPane track;
    private StackPane animatedThumb;

    private Timeline timeline;

    private double indicatorRotation;
    private double horizontalRotation;
    private double shifting;
    private boolean isValid = false;

    private final static double SCALE = 0.75;


    public CustomSliderSkin(JFXSlider slider) {
        super(slider);

        track = (StackPane) getSkinnable().lookup(".track");
        thumb = (StackPane) getSkinnable().lookup(".thumb");

        coloredTrack = new StackPane();
        coloredTrack.getStyleClass().add("colored-track");
        coloredTrack.setMouseTransparent(true);

        sliderValue = new Text();
        sliderValue.setFont(Font.font(16));
        sliderValue.getStyleClass().setAll("slider-value");

        animatedThumb = new StackPane();
        animatedThumb.getStyleClass().add("animated-thumb");
        animatedThumb.getChildren().add(sliderValue);
        animatedThumb.setMouseTransparent(true);
        animatedThumb.setScaleX(0);
        animatedThumb.setScaleY(0);

        getChildren().add(getChildren().indexOf(thumb), coloredTrack);
        getChildren().add(getChildren().indexOf(thumb), animatedThumb);
        getChildren().add(0, mouseHandlerPane);

        registerChangeListener(slider.valueFactoryProperty(), "VALUE_FACTORY");

        initListeners();
    }

    @Override
    protected void handleControlPropertyChanged(String p) {
        super.handleControlPropertyChanged(p);
        if ("VALUE_FACTORY".equals(p)) {
            refreshSliderValueBinding();
        }
    }

    private void refreshSliderValueBinding() {
        sliderValue.textProperty().unbind();
        if (((JFXSlider) getSkinnable()).getValueFactory() != null) {
            sliderValue.textProperty()
                    .bind(((JFXSlider) getSkinnable()).getValueFactory().call((JFXSlider) getSkinnable()));
        } else {
            sliderValue.textProperty().bind(Bindings.createStringBinding(() -> {
                if (getSkinnable().getLabelFormatter() != null) {
                    return getSkinnable().getLabelFormatter().toString(getSkinnable().getValue());
                } else {
                    return String.valueOf(Math.round(getSkinnable().getValue()));
                }
            }, getSkinnable().valueProperty()));
        }
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        if (!isValid) {
            initializeVariables();
            initAnimation(getSkinnable().getOrientation());
            isValid = true;
        }

        double prefWidth = animatedThumb.prefWidth(-1);
        animatedThumb.resize(prefWidth, animatedThumb.prefHeight(prefWidth));

        boolean horizontal = getSkinnable().getOrientation() == Orientation.HORIZONTAL;
        double width, height, layoutX, layoutY;
        if (horizontal) {
            width = thumb.getLayoutX() - snappedLeftInset();
            height = track.getHeight();
            layoutX = track.getLayoutX();
            layoutY = track.getLayoutY();
            // Position animatedThumb below the track.
            animatedThumb.setLayoutX(thumb.getLayoutX() + thumb.getWidth() / 2 - animatedThumb.getWidth() / 2);
            animatedThumb.setLayoutY(track.getLayoutBounds().getMaxY() + animatedThumb.getHeight());
        } else {
            height = track.getLayoutBounds().getMaxY() + track.getLayoutY() - thumb.getLayoutY() - snappedBottomInset();
            width = track.getWidth();
            layoutX = track.getLayoutX();
            layoutY = thumb.getLayoutY() + 20;
            // Commented out the code that positions the animatedThumb above the track.
            // animatedThumb.setLayoutY(thumb.getLayoutY() + thumb.getHeight() / 2 - animatedThumb.getHeight() / 2);
        }

        coloredTrack.resizeRelocate(layoutX, layoutY, width, height);
        mouseHandlerPane.resizeRelocate(x, y, w, h);
    }

    private void initializeVariables() {
        animatedThumb.setScaleX(SCALE);
        animatedThumb.setScaleY(SCALE);

        // Quick hacky fix to stop the thumb from being visible during initialization
        animatedThumb.setVisible(false);

        shifting = 30 + thumb.getWidth();
        if (getSkinnable().getOrientation() != Orientation.HORIZONTAL) {
            horizontalRotation = -90;
        }
        if (((JFXSlider) getSkinnable()).getIndicatorPosition() != JFXSlider.IndicatorPosition.LEFT) {
            indicatorRotation = 180;
            shifting = -shifting;
        }
        final double rotationAngle = 45;
        sliderValue.setRotate(rotationAngle + indicatorRotation + 3 * horizontalRotation + 180);
        // Flip the animatedThumb 180 degrees when the orientation is horizontal.
        animatedThumb.setRotate(-rotationAngle + indicatorRotation + horizontalRotation + (getSkinnable().getOrientation() == Orientation.HORIZONTAL ? 180 : 0));
    }

    private void initListeners() {
        // delegate slider mouse events to track node
        mouseHandlerPane.setOnMousePressed(this::delegateToTrack);
        mouseHandlerPane.setOnMouseReleased(this::delegateToTrack);
        mouseHandlerPane.setOnMouseDragged(this::delegateToTrack);

        track.addEventHandler(MouseEvent.ANY, event -> {
            // Quick hacky fix to stop the thumb from being visible during initialization
            if(!animatedThumb.isVisible()) {
                animatedThumb.setVisible(true);
            }
        });

        // animate value node
        track.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> {
            timeline.setRate(1);
            timeline.play();
        });
        track.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> {
            timeline.setRate(-1);
            timeline.play();
        });
        thumb.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> {
            timeline.setRate(1);
            timeline.play();
        });
        thumb.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> {
            timeline.setRate(-1);
            timeline.play();
        });

        refreshSliderValueBinding();
        updateValueStyleClass();

        getSkinnable().valueProperty().addListener(observable -> updateValueStyleClass());
        getSkinnable().orientationProperty().addListener(observable -> initAnimation(getSkinnable().getOrientation()));
    }

    private void delegateToTrack(MouseEvent event) {
        if (!event.isConsumed()) {
            event.consume();
            track.fireEvent(event);
        }
    }

    private void updateValueStyleClass() {
        getSkinnable().pseudoClassStateChanged(MIN_VALUE, getSkinnable().getMin() == getSkinnable().getValue());
        getSkinnable().pseudoClassStateChanged(MAX_VALUE, getSkinnable().getMax() == getSkinnable().getValue());
    }

    private void initAnimation(Orientation orientation) {
        double thumbPos, thumbNewPos;
        DoubleProperty layoutProperty;

        if (orientation == Orientation.HORIZONTAL) {
            thumbPos = thumb.getLayoutY() + thumb.getHeight();
            thumbNewPos = thumbPos - 28.5;
            layoutProperty = animatedThumb.translateYProperty();
        } else {
            if (((JFXSlider) getSkinnable()).getIndicatorPosition() == JFXSlider.IndicatorPosition.RIGHT) {
                thumbPos = thumb.getLayoutX() - thumb.getWidth();
                thumbNewPos = thumbPos - shifting;
            } else {
                double width = animatedThumb.prefWidth(-1);
                thumbPos = thumb.getLayoutX() - width / 2;
                thumbNewPos = thumb.getLayoutX() - width - thumb.getWidth();
            }
            layoutProperty = animatedThumb.translateXProperty();
        }

        clearAnimation();

        timeline = new Timeline(
            new KeyFrame(
                    Duration.ZERO,
                    new KeyValue(animatedThumb.scaleXProperty(), 0, Interpolator.EASE_BOTH),  // changed from 0 to 0.5
                    new KeyValue(animatedThumb.scaleYProperty(), 0, Interpolator.EASE_BOTH),  // changed from 0 to 0.5
                    new KeyValue(layoutProperty, thumbPos, Interpolator.EASE_BOTH)),
            new KeyFrame(
                    Duration.seconds(0.2),
                    new KeyValue(animatedThumb.scaleXProperty(), SCALE, Interpolator.EASE_BOTH),  // changed from 1 to 0.5
                    new KeyValue(animatedThumb.scaleYProperty(), SCALE, Interpolator.EASE_BOTH),  // changed from 1 to 0.5
                    new KeyValue(layoutProperty, thumbNewPos, Interpolator.EASE_BOTH))
        );
    }


    @Override
    public void dispose() {
        super.dispose();
        clearAnimation();
    }

    private void clearAnimation() {
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline = null;
        }
    }
}