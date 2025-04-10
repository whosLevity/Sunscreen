package me.combimagnetron.sunscreen.placeholder;

import me.clip.placeholderapi.PlaceholderAPI;
import me.combimagnetron.passport.data.Identifier;
import me.combimagnetron.passport.util.matcher.Token;
import me.combimagnetron.passport.util.placeholder.Placeholder;
import me.combimagnetron.passport.util.placeholder.PlaceholderProvider;
import org.bukkit.entity.Player;

public class PapiPlaceholderProvider implements PlaceholderProvider<Player> {

    @Override
    public Identifier identifier() {
        return Identifier.of("sunscreen", "placeholders/papi");
    }

    @Override
    public Token.Type format() {
        return Token.Type.of("%.+%");
    }

    @Override
    public <V> V parse(Placeholder<Player> placeholder) {
        return (V) PlaceholderAPI.setPlaceholders(placeholder.player(), placeholder.placeholder());
    }
}
