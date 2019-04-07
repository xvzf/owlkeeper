package de.htwsaar.owlkeeper.ui.pages;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import javafx.scene.image.ImageView;

public class Team extends Page{

    public Team(){
        super("page-team");
    }

    @Override
    public String getName() {
        return "Team";
    }

    @Override
    public ImageView getIcon(){
        return CommonNodes.Image("/images/users.png", 19, 16);
    }

    @Override
    public boolean getForce(){
        return true;
    }
}
