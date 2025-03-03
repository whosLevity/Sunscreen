package me.combimagnetron.sunscreen.menu.input;

import org.jetbrains.annotations.Nullable;

public record Click(Type.MouseClick value, Type type) implements Input<Input.Type.MouseClick> {

}
