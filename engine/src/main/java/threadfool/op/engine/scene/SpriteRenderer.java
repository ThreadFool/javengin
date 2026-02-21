package threadfool.op.engine.scene;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;

public class SpriteRenderer {
	public Mesh mesh;
	public Shader shader;

	public SpriteRenderer(Mesh mesh, Shader shader) {
		this.mesh = mesh;
		this.shader = shader;
	}
}
