package me.combimagnetron.sunscreen;

import me.combimagnetron.passport.user.User;
import me.combimagnetron.sunscreen.image.Canvas;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.annotation.Logic;
import me.combimagnetron.sunscreen.menu.draft.Draft;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.menu.element.div.Edit;
import me.combimagnetron.sunscreen.menu.element.impl.ImageElement;
import me.combimagnetron.sunscreen.menu.input.Click;
import me.combimagnetron.sunscreen.menu.input.Input;
import me.combimagnetron.sunscreen.menu.input.KeyPress;
import me.combimagnetron.sunscreen.menu.input.Scroll;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;

public class PlanetScreen extends Menu.FloatImpl {
    public PlanetScreen(SunscreenUser<?> viewer) {
        super(viewer);
        Div div = Div.div(Identifier.of("planet_element"));
        ImageElement imageElement = ImageElement.imageElement(Canvas.image(Canvas.ImageProvider.url("https://i.imgur.com/79sGWjB.png")), Identifier.of("planet_element"), Position.pixel(0,0));
        div.add(imageElement);
        div.size(imageElement.size());
        div(div);
        open(viewer);
    }

    @Logic
    public void click(Click input) {
        apply(Draft.draft().edit(Edit.div().identifier(Identifier.of("planet_element")).done()));
    }

    @Logic
    public void scroll(Scroll input) {
        if (input.value().direction == Input.Type.Scroll.Direction.UP) {

        }
    }

    @Logic
    public void keyPress(KeyPress input) {

    }

}


