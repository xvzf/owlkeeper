package de.htwsaar.owlkeeper.ui.helper;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public final class CommonNodes{
    private CommonNodes(){}

    /**
     * Builds an ImageView Node
     * @param uri path to the image
     * @param width fit width
     * @param height fit height
     * @return ImageView Node object
     */
    public static ImageView Image(String uri, int width, int height){
        ImageView image = new ImageView(uri);
        image.setFitWidth(width);
        image.setFitHeight(height);
        image.setPreserveRatio(true);
        image.setPickOnBounds(true);
        return image;
    }

    /**
     * Builds an Date Label Node with the correct color
     * @param value datetime-string
     * @todo 28.02.2019 make date-color dynamic maybe switch string to timestamp
     * @return Date represented as a Label Node Object
     */
    public static Text Date(String value){
        Text date = new Text(value);
        date.getStyleClass().add("date");
        switch (0){
            case 2:
                date.getStyleClass().add("date--error");
                break;
            case 1:
                date.getStyleClass().add("date--warning");
                break;
            default:
                date.getStyleClass().add("date--success");
                break;
        }
        return date;
    }

    /**
     * Builds a hairline as a Rectangle Node
     * @param width hairline width
     * @param light true if the line is light
     * @return Rectangle Node Object
     */
    public static Rectangle Hr(int width, boolean light){
        Rectangle hr = new Rectangle();
        hr.setWidth(400);
        hr.setHeight(1);;
        hr.getStyleClass().add("hr");
        if (light){
            hr.getStyleClass().add("hr--light");
        }
        return hr;
    }

    /**
     * Builds a Tag element as a Label Node
     * @param text the tags text
     * @return Label Node Object
     */
    public static Label Tag(String text){
        Label tag = new Label(text);
        tag.getStyleClass().add("tag");
        return tag;
    }

    /**
     * Builds a Tag element with a custom color as a Label Node
     * @param text the tags text
     * @param color the tags color value as hex
     * @return Label Node Object
     */
    public static Label Tag(String text, String color){
        Label tag = CommonNodes.Tag(text);
        tag.setStyle("-fx-background-color: " + color);
        return tag;
    }
}
