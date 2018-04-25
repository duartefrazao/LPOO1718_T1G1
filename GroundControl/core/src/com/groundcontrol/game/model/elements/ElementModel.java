package com.groundcontrol.game.model.elements;

public abstract class ElementModel {

    private float x;

    private float y;

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    private float rotation;

    public enum ModelType {PLANET,PLAYER};

    private ModelType type;


    ElementModel(float x, float y, float rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ModelType getType() {
        return type;
    }
}
