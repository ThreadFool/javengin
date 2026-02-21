package threadfool.op.engine.systems;

import org.joml.Matrix4f;

import threadfool.op.engine.render.Camera;
import threadfool.op.engine.scene.NewGameObject;
import threadfool.op.engine.scene.NewScene;
import threadfool.op.engine.scene.SpriteRenderer;
import threadfool.op.engine.scene.Transform;

public class RenderSystem
{
	public void render(NewScene scene, Camera cam) {
		for (NewGameObject obj : scene.getObjectsWith(SpriteRenderer.class)) {
			SpriteRenderer r = obj.getComponent(SpriteRenderer.class);
			Transform t = obj.getComponent(Transform.class);

			Matrix4f mvp = new Matrix4f(cam.getProjectionView(800, 600))
					.mul(t.getModelMatrix());

			r.shader.bind();
			r.shader.setMat4("u_MVP", mvp);
			r.mesh.draw();
		}
	}
}
