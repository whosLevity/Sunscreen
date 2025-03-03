package me.combimagnetron.sunscreen.menu.element.div;

import me.combimagnetron.sunscreen.util.Identifier;

public interface ScrollableDiv extends Div {

    ScrollableDiv scroll(double percentage);

    ScrollableDiv scroll(int pixel);

    class Impl extends Div.Impl implements ScrollableDiv {
        private double scroll = 0.0;

        public Impl(Identifier identifier) {
            super(identifier);
        }

        @Override
        public ScrollableDiv scroll(double percentage) {
            this.scroll = percentage;
            return this;
        }

        @Override
        public ScrollableDiv scroll(int pixel) {
            this.scroll = pixel;
            return this;
        }

    }

}
