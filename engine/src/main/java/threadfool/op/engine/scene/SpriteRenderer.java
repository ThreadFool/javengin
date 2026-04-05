package threadfool.op.engine.scene;

import org.joml.Vector4f;

import threadfool.op.engine.gpu.Mesh;
import threadfool.op.engine.gpu.Shader;
import threadfool.op.engine.gpu.Texture;

import static threadfool.op.engine.util.ShaderUtlis.TEX_FRAGMENT;
import static threadfool.op.engine.util.ShaderUtlis.TEX_VERTEX;

public class SpriteRenderer {
	public Mesh mesh;
	public Shader shader;
	public Texture texture;
	public Vector4f tint = new Vector4f(1, 1, 1, 1);

	public SpriteRenderer(Mesh mesh, Shader shader) {
		this.mesh   = mesh;
		this.shader = shader;
	}

	public SpriteRenderer(Texture texture) {
		this.mesh    = Mesh.createQuad();
		this.shader  = new Shader(TEX_VERTEX, TEX_FRAGMENT);
		this.texture = texture;
	}
}
