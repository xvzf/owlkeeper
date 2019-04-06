package de.htwsaar.owlkeeper.ui.helper;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public final class CommonNodes {
    private static final String STYLE_BACKGROUND_COLOR = "-fx-background-color: ";
    private static final String STYLE_DATE = "date";
    private static final String STYLE_DATE_ERROR = "date--error";
    private static final String STYLE_DATE_WARNING = "date--warning";
    private static final String STYLE_DATE_SUCCESS = "date--success";
    private static final String STYLE_HR = "hr";
    private static final String STYLE_HR_LIGHT = "hr--light";
    private static final String STYLE_TAG = "tag";

    private CommonNodes() {
    }

    /**
     * Builds an ImageView Node
     *
     * @param uri path to the image
     * @param width fit width
     * @param height fit height
     * @return ImageView Node object
     */
    public static ImageView Image(String uri, int width, int height) {
        ImageView image = new ImageView(uri);
        image.setFitWidth(width);
        image.setFitHeight(height);
        image.setPreserveRatio(true);
        image.setPickOnBounds(true);
        return image;
    }

    /**
     * Builds an Date Label Node with the correct color
     *
     * @param date timestamp to render
     * @return Date represented as a Label Node Object
     * @todo 28.02.2019 make date-color dynamic maybe switch string to timestamp
     */
    public static Text Date(Timestamp date) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Text dateString = new Text(new SimpleDateFormat("dd.MM.yyyy").format(date));
        dateString.getStyleClass().add(STYLE_DATE);
        // TODO: hardcoded switch
        switch (0) {
        case 2:
            dateString.getStyleClass().add(STYLE_DATE_ERROR);
            break;
        case 1:
            dateString.getStyleClass().add(STYLE_DATE_WARNING);
            break;
        default:
            dateString.getStyleClass().add(STYLE_DATE_SUCCESS);
            break;
        }

        if (date.getTime() > (now.getTime() - 1000 * 60 * 60 * 24) && date.getTime() < now.getTime()) {
            dateString.getStyleClass().add(STYLE_DATE_WARNING);
        } else if (date.getTime() < now.getTime()) {
            dateString.getStyleClass().add(STYLE_DATE_ERROR);
        }

        return dateString;
    }

    /**
     * Builds a hairline as a Rectangle Node
     *
     * @param width hairline width
     * @param light true if the line is light
     * @return Rectangle Node Object
     */
    public static Rectangle Hr(int width, boolean light) {
        Rectangle hr = new Rectangle();
        hr.setWidth(width);
        hr.setHeight(1);
        ;
        hr.getStyleClass().add(STYLE_HR);
        if (light) {
            hr.getStyleClass().add(STYLE_HR_LIGHT);
        }
        return hr;
    }

    /**
     * Builds a Tag element as a Label Node
     *
     * @param text the tags text
     * @return Label Node Object
     */
    public static Label Tag(String text) {
        Label tag = new Label(text);
        tag.getStyleClass().add(STYLE_TAG);
        return tag;
    }

    /**
     * Builds a Tag element with a custom color as a Label Node
     *
     * @param text the tags text
     * @param color the tags color value as hex
     * @return Label Node Object
     */
    public static Label Tag(String text, String color) {
        Label tag = CommonNodes.Tag(text);
        tag.setStyle(STYLE_BACKGROUND_COLOR + color);
        return tag;
    }
}
