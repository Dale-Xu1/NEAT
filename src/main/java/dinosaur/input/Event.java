package dinosaur.input;

import javafx.scene.input.KeyCode;

@FunctionalInterface
public interface Event
{

    void update(KeyCode code);

}
