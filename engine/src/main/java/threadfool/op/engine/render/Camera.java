package threadfool.op.engine.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import threadfool.op.engine.scene.NewGameObject;
import threadfool.op.engine.scene.Transform;

public class Camera
{
	private final Matrix4f projection = new Matrix4f();
	private final Matrix4f view = new Matrix4f();
	public Vector2f position = new Vector2f(0, 0);
	public float zoom = 1f;

	public Matrix4f getViewMatrix(){
		return new Matrix4f().identity().translate(-position.x, -position.y, 0).scale(zoom, zoom,1);
	}

	public Matrix4f getProjectionMatrix(float width, float height){
		return new Matrix4f().ortho2D(0, width, 0, height);
	}

	public void followCentered(NewGameObject target, float screenWidth, float screenHeight) {
		Transform t = target.getComponent(Transform.class);
		if (t != null) {
			position.set(t.position.x + t.scale.x / 2f - screenWidth / 2f,
					t.position.y + t.scale.y / 2f - screenHeight / 2f);
		}
	}

	public Matrix4f getProjectionView(float screenWidth, float screenHeight) {
		Matrix4f proj = new Matrix4f().ortho2D(0, screenWidth, 0, screenHeight);

		Matrix4f view = new Matrix4f()
				.identity()
				.translate(-position.x, -position.y, 0)
				.scale(zoom, zoom, 1);

		return new Matrix4f(proj).mul(view);
	}
}
