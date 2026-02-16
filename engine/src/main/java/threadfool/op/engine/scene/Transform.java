package threadfool.op.engine.scene;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Transform
{
	public  Vector2f position = new Vector2f(0, 0);
	public float rotation = 0f; // radians
	public Vector2f scale = new Vector2f(1, 1);

	private final Matrix4f modelMatrix = new Matrix4f();

	public Matrix4f getModelMatrix() {
		modelMatrix.identity()
				.translate(position.x, position.y, 0f)
				.rotateZ(rotation)
				.scale(scale.x, scale.y, 1f);
		return modelMatrix;
	}

	public void setRotation(float rot){
		rotation = rot;
	}
}
