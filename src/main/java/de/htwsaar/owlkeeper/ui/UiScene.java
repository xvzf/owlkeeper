package de.htwsaar.owlkeeper.ui;

/**
 * Interface to define a new Scene in the main application GUI
 */
interface UiScene{
    String getName();
    ViewApplication.SceneBuilder getBuilder();
}