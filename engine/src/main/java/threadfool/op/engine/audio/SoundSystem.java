package threadfool.op.engine.audio;

import threadfool.op.engine.scene.GameObject;
import threadfool.op.engine.scene.Scene;

public class SoundSystem
{
	/** No-op per-frame — AL sources drive themselves once started.
	 *  Reserved for future spatial audio position updates. */
	public void update(Scene scene) {}

	/** Stop all SoundSource components in the scene. Call from GameScene.dispose(). */
	public void stopAll(Scene scene) {
		for (GameObject obj : scene.getObjectsWith(SoundSource.class)) {
			obj.getComponent(SoundSource.class).stop();
		}
	}
}
