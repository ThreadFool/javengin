package threadfool.op.engine.scene;

import java.util.ArrayList;
import java.util.List;

import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;

public class Scene
{
	private List<GameObject> objects = new ArrayList<>();

	public void add(GameObject obj) {
		objects.add(obj);
	}

	public void update(InputSystem inputSystem) {
		for (GameObject obj : objects) {
			obj.update(inputSystem);
		}
	}

	public void render(Camera cam, Window window) {
		for (GameObject obj : objects) {
			obj.render(cam, window);
		}
	}
}
