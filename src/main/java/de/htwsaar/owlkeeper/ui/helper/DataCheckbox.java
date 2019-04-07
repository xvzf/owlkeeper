package de.htwsaar.owlkeeper.ui.helper;

import javafx.scene.control.CheckBox;

/**
 * Checkbox extension to allow retrieval
 */
public class DataCheckbox<T> extends CheckBox{

    private T data;

    /**
     * saves the checkbox data
     *
     * @param data T value
     */
    public DataCheckbox(T data, String text){
        super(text);
        this.data = data;
    }

    public DataCheckbox(T data){
        this(data, "");
    }

    /**
     * Retrieves the checkbox data
     *
     * @return T  value
     */
    public T getData(){
        return this.data;
    }
}
