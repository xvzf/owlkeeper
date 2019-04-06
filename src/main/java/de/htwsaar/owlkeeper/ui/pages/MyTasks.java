package de.htwsaar.owlkeeper.ui.pages;

import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import javafx.scene.image.ImageView;

public class MyTasks extends Page{

    public MyTasks() {
        super("page");
    }

    @Override
    public String getName() {
        return "My Tasks";
    }

    @Override
    public ImageView getIcon(){
        return CommonNodes.Image("/images/home.png", 19, 21);
    }

    @Override
    public boolean getForce(){
        return true;
    }
}
