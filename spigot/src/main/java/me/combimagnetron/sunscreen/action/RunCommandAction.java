package me.combimagnetron.sunscreen.action;

import me.combimagnetron.sunscreen.logic.action.Action;
import me.combimagnetron.sunscreen.logic.action.Argument;
import me.combimagnetron.sunscreen.logic.action.ArgumentType;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class RunCommandAction extends Action.AbstractAction {
    public static final Identifier ActionIdentifier = Identifier.of("sunscreen", "run_command");

    public RunCommandAction() {
        super(ActionIdentifier);
    }

    static {
        Action.ACTION_MAP.put(ActionIdentifier, new RunCommandAction());
    }

    @Override
    public void execute(SunscreenUser<?> user, Argument<?>... arguments) {
        if (!validate(arguments)) {
            throw new IllegalArgumentException("Invalid arguments provided to action " + ActionIdentifier);
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        String command = (String) arguments[0].value();
        boolean console = arguments.length == 2 && (Boolean) arguments[1].value();
        if (console) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            return;
        }
        Player player = (Player) user.platformSpecificPlayer();
        player.chat("/" + command);
    }

    @Override
    public boolean validate(Argument<?>... arguments) {
        return arguments.length == 1 && arguments[0].type() == String.class ||
                arguments.length == 2 && arguments[0].type() == String.class && arguments[1].type() == Boolean.class;
    }

    @Override
    public Collection<ArgumentType> argumentType() {
        return List.of(ArgumentType.of("command", String.class), ArgumentType.of("console", Boolean.class));
    }

}
