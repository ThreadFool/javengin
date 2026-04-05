package threadfool.op.engine.scene;

import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;

public abstract class GameScene
{
	protected final Scene scene = new Scene();
	protected Window window;

	public abstract void init(Window window, InputSystem input);
	public abstract void update(InputSystem input);
	public abstract void render(Camera camera, Window window);

	public void dispose() {}

	public Scene getScene() { return scene; }
}
