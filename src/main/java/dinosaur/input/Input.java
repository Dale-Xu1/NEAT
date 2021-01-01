package dinosaur.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Input
{

    private static final Input instance = new Input();

    public static Input getInstance()
    {
        return instance;
    }


    private final List<Event> keyPressedEvents = new ArrayList<>();
    private final List<Event> keyReleasedEvents = new ArrayList<>();


    public void subscribeScene(Scene scene)
    {
        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);
    }

    private void onKeyPressed(KeyEvent key)
    {
        KeyCode code = key.getCode();

        // Broadcast to subscribed events
        for (Event event : keyPressedEvents)
        {
            event.update(code);
        }
    }

    private void onKeyReleased(KeyEvent key)
    {
        KeyCode code = key.getCode();

        // Broadcast to subscribed events
        for (Event event : keyReleasedEvents)
        {
            event.update(code);
        }
    }


    public void subscribe(InputEvent type, Event event)
    {
        switch (type)
        {
            case KEY_PRESSED -> keyPressedEvents.add(event);
            case KEY_RELEASED -> keyReleasedEvents.add(event);
        }
    }

    public void unsubscribe(InputEvent type, Event event)
    {
        switch (type)
        {
            case KEY_PRESSED -> keyPressedEvents.remove(event);
            case KEY_RELEASED -> keyReleasedEvents.remove(event);
        }
    }

}
