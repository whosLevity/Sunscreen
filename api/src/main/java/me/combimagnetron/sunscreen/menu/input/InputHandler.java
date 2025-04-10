package me.combimagnetron.sunscreen.menu.input;

import me.combimagnetron.sunscreen.user.SunscreenUser;

public interface InputHandler {

    boolean active();

    int encoded();

    TextInput textInput();

    TextInput open();

    void quit();

    class Impl implements InputHandler {
        private final SunscreenUser<?> user;
        private boolean active = false;
        private int encoded = 0;
        private TextInput textInput = null;

        public Impl(SunscreenUser<?> user) {
            this.user = user;
        }

        public void active(boolean active) {
            this.active = active;
        }

        @Override
        public boolean active() {
            return this.textInput != null;
        }

        @Override
        public int encoded() {
            if (textInput == null) {
                return 0;
            }
            return textInput.encoded();
        }

        @Override
        public void quit() {
            this.textInput.quit();
            this.textInput = null;
            this.active = false;
        }

        @Override
        public TextInput open() {
            this.textInput = new TextInput.Impl(user);
            return textInput;
        }

        @Override
        public TextInput textInput() {
            return this.textInput;
        }
    }

}
