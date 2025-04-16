package me.combimagnetron.sunscreen.session;

import me.combimagnetron.passport.user.User;
import me.combimagnetron.sunscreen.user.SunscreenUser;
import me.combimagnetron.sunscreen.util.Identifier;

import java.util.*;
import java.util.function.Function;

public class SessionHandler {
    private final Map<UUID, Session> sessions = new HashMap<>();

    public Session session(SunscreenUser<?> user) {
        return sessions.get(user.uniqueIdentifier());
    }

    public Session session(Session session) {
        return sessions.put(session.user().uniqueIdentifier(), session);
    }

}
