package threadfool.op.engine.scene;

import java.util.ArrayList;
import java.util.List;

import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;
import threadfool.op.engine.render.Renderer;

public class GameObject
{
	public Transform transform = new Transform();
	private Renderer renderer;

	List<Component> components = new ArrayList<>();

	public GameObject(Renderer renderer){
		this.renderer = renderer;
	}

	public void update(InputSystem input){
		for(Component c : components){
			c.update(input);
		}
	}

	public void addComponent(Component c){
		components.add(c);
		c.gameObject = this;
		c.start();
}

	public void render(Camera cam, Window window){
		renderer.render(transform, cam, window);
	}
}
