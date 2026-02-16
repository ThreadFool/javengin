package threadfool.op.engine.scene;

import org.lwjgl.glfw.GLFW;

import threadfool.op.engine.core.Time;
import threadfool.op.engine.platform.input.InputSystem;

public class PlayerController extends Component
{
	private float speed = 200f;

	@Override
	public void update(InputSystem input) {
		if(input.isKeyDown(GLFW.GLFW_KEY_W)) gameObject.transform.position.y += speed * Time.delta();
		if(input.isKeyDown(GLFW.GLFW_KEY_S)) gameObject.transform.position.y -= speed * Time.delta();
		if(input.isKeyDown(GLFW.GLFW_KEY_A)) gameObject.transform.position.x -= speed * Time.delta();
		if(input.isKeyDown(GLFW.GLFW_KEY_D)) gameObject.transform.position.x += speed * Time.delta();
	}
}
