package event;

import javafx.event.Event;
import javafx.event.EventType;


public class EmailInvadersExit extends Event {

    public static final EventType<EmailInvadersExit> EMAIL_INVADERS_EXIT =
            new EventType<>(Event.ANY, "EmailInvadersExit");

    // Konstruktor
    public EmailInvadersExit() {
        super(EMAIL_INVADERS_EXIT);
    }

}
