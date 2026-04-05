package threadfool.op.engine.scene;

import java.util.ArrayDeque;
import java.util.Deque;

import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;

public class SceneManager
{
	private final Deque<GameScene> stack = new ArrayDeque<>();
	private final Window window;
	private final InputSystem input;

	public SceneManager(Window window, InputSystem input) {
		this.window = window;
		this.input  = input;
	}

	/** Replace the current scene with a new one. */
	public void switchTo(GameScene scene) {
		if (!stack.isEmpty()) stack.pop().dispose();
		scene.init(window, input);
		stack.push(scene);
	}

	/** Push a scene on top (e.g. pause overlay). */
	public void push(GameScene scene) {
		scene.init(window, input);
		stack.push(scene);
	}

	/** Pop the top scene and resume the one beneath. */
	public void pop() {
		if (!stack.isEmpty()) stack.pop().dispose();
	}

	public boolean isEmpty() { return stack.isEmpty(); }

	public void update() {
		if (!stack.isEmpty()) stack.peek().update(input);
	}

	public void render(Camera camera) {
		if (!stack.isEmpty()) stack.peek().render(camera, window);
	}
}
