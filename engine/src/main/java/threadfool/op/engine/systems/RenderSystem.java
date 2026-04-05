package threadfool.op.engine.systems;

import org.joml.Matrix4f;

import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;
import threadfool.op.engine.scene.GameObject;
import threadfool.op.engine.scene.Scene;
import threadfool.op.engine.scene.SpriteRenderer;
import threadfool.op.engine.scene.Transform;

public class RenderSystem
{
	public void render(Scene scene, Camera camera, Window window) {
		float w = window.getWidth();
		float h = window.getHeight();

		for (GameObject obj : scene.getObjectsWith(SpriteRenderer.class)) {
			SpriteRenderer r = obj.getComponent(SpriteRenderer.class);
			Transform t = obj.getComponent(Transform.class);
			if (t == null) continue;

			Matrix4f mvp = new Matrix4f(camera.getProjectionView(w, h))
					.mul(t.getModelMatrix());

			r.shader.bind();
			r.shader.setMat4("u_MVP", mvp);

			if (r.texture != null) {
				r.texture.bind(0);
				r.shader.setInt("u_Texture", 0);
				r.shader.setVec4("u_Tint", r.tint.x, r.tint.y, r.tint.z, r.tint.w);
			}

			r.mesh.draw();

			if (r.texture != null) r.texture.unbind();
		}
	}
}
