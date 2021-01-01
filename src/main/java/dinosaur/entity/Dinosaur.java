package dinosaur.entity;

import dinosaur.input.Input;
import dinosaur.input.InputEvent;
import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Dinosaur extends Entity
{

    private Vector2 velocity = new Vector2(0, 0);

    private boolean isJumping = false;
    private boolean isCrouching = false;


    public Dinosaur()
    {
        super(new Vector2(30, 0), new Vector2(30, 50)); // Crouching: (50, 25)

        Input input = Input.getInstance();

        input.subscribe(InputEvent.KEY_PRESSED, this::onKeyPressed);
        input.subscribe(InputEvent.KEY_RELEASED, this::onKeyReleased);
    }


    @Override
    public void update(float delta)
    {
        if (isJumping) System.out.println("hi");
    }

    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFill(Color.color(0, 0, 0, 0.3));
        gc.fillRect(position.x, position.y, dimensions.x, dimensions.y);
    }

    public void remove()
    {
        Input input = Input.getInstance();

        input.unsubscribe(InputEvent.KEY_PRESSED, this::onKeyPressed);
        input.unsubscribe(InputEvent.KEY_RELEASED, this::onKeyReleased);
    }


    private void onKeyPressed(KeyCode code)
    {
        switch (code)
        {
            case SPACE, UP -> isJumping = true;
            case DOWN -> isCrouching = true;
        }
    }

    private void onKeyReleased(KeyCode code)
    {
        switch (code)
        {
            case SPACE, UP -> isJumping = false;
            case DOWN -> isCrouching = false;
        }
    }

}
