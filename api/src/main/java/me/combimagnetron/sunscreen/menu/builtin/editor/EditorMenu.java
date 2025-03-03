package me.combimagnetron.sunscreen.menu.builtin.editor;

import me.combimagnetron.passport.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.image.Color;
import me.combimagnetron.sunscreen.menu.Menu;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.ColorPickerElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.ElementDrawerElement;
import me.combimagnetron.sunscreen.menu.builtin.editor.element.SectionElement;
import me.combimagnetron.sunscreen.menu.element.Position;
import me.combimagnetron.sunscreen.menu.element.div.Div;
import me.combimagnetron.sunscreen.menu.element.impl.TextElement;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.style.Text;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.util.Vec2d;

public class EditorMenu extends Menu.FloatImpl {
    private final static double PreviewScale = 0.7806122449;
    //private final Canvas sheet = Canvas.image(Canvas.ImageProvider.file(Path.of("assets/sunscreen/textures/editor/sheet.png")));
    private final SunscreenUser<?> viewer;
    private final Vec2d previewSize;

    public EditorMenu(SunscreenUser<?> viewer) {
        super(viewer);
        this.viewer = viewer;
        this.previewSize = viewer.screenSize().pixel().mul(PreviewScale);
        build();
        open(viewer);
    }

    private void build() {
        System.out.println(viewer.screenSize().pixel());
        System.out.println(previewSize);
        Div editor = Div.div(Identifier.of("editor", "preview"))
                .scale(Vector3d.vec3(0.78))
                .size(previewSize)
                .position(
                Position.position(viewer)
                        .x()
                        .percentage(100 - 78.06122449)
                            .pixel(-2)
                        .back()
                        .y()
                            .pixel(2)
                        .back()
                        .finish());
        div(editor);
        Vec2d sizeBarSize = Vec2d.of(viewer.screenSize().pixel().mul(1 - PreviewScale).sub(6, 0).x(), previewSize.y());
        Div sidebar = Div.div(Identifier.of("editor", "sidebar"))
                .size(sizeBarSize)
                .position(
                Position.position(viewer)
                        .x()
                        .percentage(100 - 22.04081633)
                            .pixel(-2)
                        .back()
                        .y()
                            .pixel(2)
                        .back()
                        .finish())
                .add(
                        SectionElement.sectionElement(Identifier.of("sidebar", "background"), Position.pixel(0, 0), sizeBarSize)
                );
        div(sidebar);
    }

    public static class Colors {
        public static final Color Background = Color.of(9, 10, 20);
        public static final Color Secondary = Color.of(16, 20, 31);
        public static final Color Tertiary = Color.of(21, 29, 40);
        public static final Color Quaternary = Color.of(32, 46, 55);
        public static final Color Fifth = Color.of(51, 65, 75);
        public static final Color Sixth = Color.of(76, 91, 101);

        public static final Color PrimaryText = Color.of(199, 220, 208);
        public static final Color SecondaryText = Color.of(155, 171, 178);

    }


}
