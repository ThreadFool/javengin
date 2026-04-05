package threadfool.op.engine.scene;

public class Collider
{
	public float width;
	public float height;
	public float offsetX = 0f;
	public float offsetY = 0f;
	public boolean isStatic = false;

	public Collider(float width, float height) {
		this.width  = width;
		this.height = height;
	}

	public Collider(float width, float height, boolean isStatic) {
		this(width, height);
		this.isStatic = isStatic;
	}

	public float minX(Transform t) { return t.position.x + offsetX; }
	public float minY(Transform t) { return t.position.y + offsetY; }
	public float maxX(Transform t) { return minX(t) + width; }
	public float maxY(Transform t) { return minY(t) + height; }
}
