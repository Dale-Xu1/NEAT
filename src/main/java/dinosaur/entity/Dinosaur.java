package dinosaur.entity;

import dinosaur.input.Input;
import dinosaur.input.InputEvent;
import dinosaur.math.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Dinosaur extends Entity
{

    private static final float SCALE = 100;
    private static final Vector2 GRAVITY = new Vector2(0, -16).mult(SCALE);

    private static final Vector2 STANDING = new Vector2(30, 50);
    private static final Vector2 CROUCHING = new Vector2(50, 25);

    private static final Vector2 JUMP = new Vector2(0, 5).mult(SCALE);
    private static final Vector2 FALL = new Vector2(0, -40).mult(SCALE);


    private Vector2 velocity = new Vector2(0, 0);

    private boolean isJumping = false;
    private boolean isCrouching = false;

    private boolean isGrounded = true;


    public Dinosaur()
    {
        super(new Vector2(30, 0), STANDING);

        Input input = Input.getInstance();

        input.subscribe(InputEvent.KEY_PRESSED, this::onKeyPressed);
        input.subscribe(InputEvent.KEY_RELEASED, this::onKeyReleased);
    }


    @Override
    public void update(float delta)
    {
        // Can only jump if on the ground and is not crouching
        if (!isCrouching && isJumping && isGrounded)
        {
            isGrounded = false;
            velocity = velocity.add(JUMP); // Not multiplying by delta because jump is an impulse
        }

        // Change bounding box dimensions when crouching
        dimensions = isCrouching ? CROUCHING : STANDING;

        if (isCrouching)
        {
            // Fall down when crouching
            velocity = velocity.add(FALL.mult(delta));
        }

        // Integrate physics
        velocity = velocity.add(GRAVITY.mult(delta)); // Multiply by delta because gravity is a force
        position = position.add(velocity.mult(delta));

        if (position.y < 0)
        {
            // Prevent dinosaur from falling through the ground
            position = new Vector2(position.x, 0);
            velocity = new Vector2(velocity.x, 0);

            isGrounded = true;
        }
    }

    @Override
    public void render(GraphicsContext gc)
    {
        gc.setFill(Color.color(0, 0, 0, 0.3));
        gc.fillRect(position.x, position.y, dimensions.x, dimensions.y);
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
