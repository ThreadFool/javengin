package threadfool.op.engine.systems;

import org.lwjgl.glfw.GLFW;

import threadfool.op.engine.core.Time;
import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.scene.NewGameObject;
import threadfool.op.engine.scene.NewScene;
import threadfool.op.engine.scene.Transform;

public class PlayerMovementSystem {

	public void update(NewScene scene, InputSystem input) {
		for (NewGameObject obj : scene.getObjectsWith(PlayerInput.class)) {
			Transform t = obj.getComponent(Transform.class);
			PlayerInput p = obj.getComponent(PlayerInput.class);

			if (input.isKeyDown(GLFW.GLFW_KEY_W)) t.position.y += p.speed * Time.delta();
			if (input.isKeyDown(GLFW.GLFW_KEY_S)) t.position.y -= p.speed * Time.delta();
			if (input.isKeyDown(GLFW.GLFW_KEY_A)) t.position.x -= p.speed * Time.delta();
			if (input.isKeyDown(GLFW.GLFW_KEY_D)) t.position.x += p.speed * Time.delta();
		}
	}
}
