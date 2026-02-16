package threadfool.op.engine.render;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import threadfool.op.engine.scene.GameObject;

public class Camera
{
	public Vector2f position = new Vector2f(0, 0);
	public float zoom = 1f;

	public Matrix4f getViewMatrix(){
		return new Matrix4f().identity().translate(-position.x, -position.y, 0).scale(zoom, zoom,1);
	}

	public Matrix4f getProjectionMatrix(float width, float height){
		return new Matrix4f().ortho2D(0, width, 0, height);
	}

	public void follow(GameObject target){
		position.lerp(target.transform.position, 0.1f);
	}

	public void followCentered(GameObject target, float windowWidth, float windowHeight) {
		position.set(
				target.transform.position.x - windowWidth / 2f,
				target.transform.position.y - windowHeight / 2f
		);
	}
}
