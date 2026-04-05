package threadfool.op.engine.bootstrap;

import threadfool.op.engine.audio.AudioContext;
import threadfool.op.engine.core.GameLoop;
import threadfool.op.engine.gpu.GLContext;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.scene.GameScene;

public class EngineBootstrap
{
	public static void start(GameScene initialScene)
	{
		Window window = new Window();
		window.create(800, 600, "Engine v.0.1");

		GLContext.init();

		AudioContext audio = new AudioContext();
		audio.init();

		GameLoop loop = new GameLoop(window, initialScene);
		loop.run();

		audio.dispose();
	}
}
