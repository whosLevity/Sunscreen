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

    public void session(Session session) {
        sessions.put(session.user().uniqueIdentifier(), session);
    }

}
