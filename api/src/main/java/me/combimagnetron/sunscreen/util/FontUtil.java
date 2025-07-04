package me.combimagnetron.sunscreen.util;

import com.github.retrooper.packetevents.protocol.component.ComponentType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

import java.util.HashMap;
import java.util.Map;

public class FontUtil {
    private static final String OFFSET_FONT_NAME = "comet:offset";
    private static final String NAMESPACE = "comet";
    private static final Map<Integer, Character> OFFSET_MAP;
    static {
        OFFSET_MAP = new HashMap<>();
        OFFSET_MAP.put(-1, 'a');
        OFFSET_MAP.put(-2, 'b');
        OFFSET_MAP.put(-4, 'c');
        OFFSET_MAP.put(-8, 'd');
        OFFSET_MAP.put(-16, 'e');
        OFFSET_MAP.put(-32, 'f');
        OFFSET_MAP.put(-64, 'g');
        OFFSET_MAP.put(-128, 'h');
        OFFSET_MAP.put(1, 'i');
        OFFSET_MAP.put(2, 'j');
        OFFSET_MAP.put(4, 'k');
        OFFSET_MAP.put(8, 'l');
        OFFSET_MAP.put(16, 'm');
        OFFSET_MAP.put(32, 'n');
        OFFSET_MAP.put(64, 'o');
        OFFSET_MAP.put(128, 'p');
    }

    public static TextComponent offset(int pixels) {
        return (TextComponent) Component.text(textOffset(pixels)).font(Key.key(OFFSET_FONT_NAME));
    }

    public static Component multiOffset(Component text, Vec2d addedPos) {
        int x = (int) addedPos.x();
        int y = (int) addedPos.y();
        if (x < 0) {
            if (x < -122)
                x = -122;
            else if (x > 122) {
                x -= 122;
            }
            x = x * -1;
        }
        if (y < 0) {
            if (y < -122)
                y = -122;
            else if (y > 122) {
                y -= 122;
            }
            y = y * -1;
        }
        return text.color(TextColor.color(250, x, y));
    }

    public static String textOffset(int pixels) {
        final StringBuilder builder = new StringBuilder();
        if (pixels == 0)
            return builder.toString();

        final boolean negative = Integer.signum(pixels) == -1;
        pixels = Math.abs(pixels);

        while (pixels > 0) {
            int highestBit = Integer.highestOneBit(pixels);
            if (highestBit > 128)
                highestBit = 128;
            builder.append(OFFSET_MAP.get(negative ? -highestBit : highestBit));

            pixels -= highestBit;
        }

        return builder.toString();
    }

    private enum DefaultFontSize {
        A('A', 5),
        a('a', 5),
        B('B', 5),
        b('b', 5),
        C('C', 5),
        c('c', 5),
        D('D', 5),
        d('d', 5),
        E('E', 5),
        e('e', 5),
        F('F', 5),
        f('f', 4),
        G('G', 5),
        g('g', 5),
        H('H', 5),
        h('h', 5),
        I('I', 3),
        i('i', 1),
        J('J', 5),
        j('j', 5),
        K('K', 5),
        k('k', 4),
        L('L', 5),
        l('l', 1),
        M('M', 5),
        m('m', 5),
        N('N', 5),
        n('n', 5),
        O('O', 5),
        o('o', 5),
        P('P', 5),
        p('p', 5),
        Q('Q', 5),
        q('q', 5),
        R('R', 5),
        r('r', 5),
        S('S', 5),
        s('s', 5),
        T('T', 5),
        t('t', 4),
        U('U', 5),
        u('u', 5),
        V('V', 5),
        v('v', 5),
        W('W', 5),
        w('w', 5),
        X('X', 5),
        x('x', 5),
        Y('Y', 5),
        y('y', 5),
        Z('Z', 5),
        z('z', 5),
        NUM_1('1', 5),
        NUM_2('2', 5),
        NUM_3('3', 5),
        NUM_4('4', 5),
        NUM_5('5', 5),
        NUM_6('6', 5),
        NUM_7('7', 5),
        NUM_8('8', 5),
        NUM_9('9', 5),
        NUM_0('0', 5),
        EXCLAMATION_POINT('!', 1),
        AT_SYMBOL('@', 6),
        NUM_SIGN('#', 5),
        DOLLAR_SIGN('$', 5),
        PERCENT('%', 5),
        UP_ARROW('^', 5),
        AMPERSAND('&', 5),
        ASTERISK('*', 5),
        LEFT_PARENTHESIS('(', 4),
        RIGHT_PERENTHESIS(')', 4),
        MINUS('-', 5),
        UNDERSCORE('_', 5),
        PLUS_SIGN('+', 5),
        EQUALS_SIGN('=', 5),
        LEFT_CURL_BRACE('{', 4),
        RIGHT_CURL_BRACE('}', 4),
        LEFT_BRACKET('[', 3),
        RIGHT_BRACKET(']', 3),
        COLON(':', 1),
        SEMI_COLON(';', 1),
        DOUBLE_QUOTE('"', 3),
        SINGLE_QUOTE('\'', 1),
        LEFT_ARROW('<', 4),
        RIGHT_ARROW('>', 4),
        QUESTION_MARK('?', 5),
        SLASH('/', 5),
        BACK_SLASH('\\', 5),
        LINE('|', 1),
        TILDE('~', 5),
        TICK('`', 2),
        PERIOD('.', 1),
        COMMA(',', 1),
        SPACE(' ', 3),
        DEFAULT('a', 4);

        private final char character;
        private final int length;

        DefaultFontSize(char character, int length) {
            this.character = character;
            this.length = length;
        }

        public static DefaultFontSize getDefaultFontSize(char c) {
            for (DefaultFontSize dFI : DefaultFontSize.values()) {
                if (dFI.getCharacter() == c) {
                    return dFI;
                }
            }
            return DefaultFontSize.DEFAULT;
        }

        public char getCharacter() {
            return character;
        }

        public int getLength() {
            return length;
        }

        public int getBoldLength() {
            if (this == DefaultFontSize.SPACE) {
                return getLength();
            }
            return length + 1;
        }
    }

    public static int width(String message) {
        int witdh = 0;
        for (int i = 0; i < message.length(); i++) {
            witdh = witdh + DefaultFontSize.getDefaultFontSize(message.charAt(i)).length;
        }
        return witdh;
    }

    public static Component offsetToZero(String message) {
        return offset(-width(message) - 1);
    }

}