package me.combimagnetron.sunscreen.menu.element;

import me.combimagnetron.sunscreen.util.Vec2d;

import java.util.function.Consumer;

public interface Interactable {

    boolean reactiveToHover();

    boolean reactiveToClick();

    void hover(Vec2d pos);

    void click(Vec2d pos);

}
