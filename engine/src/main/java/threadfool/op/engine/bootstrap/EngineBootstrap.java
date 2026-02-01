package threadfool.op.engine.bootstrap;

import threadfool.op.engine.core.GameLoop;
import threadfool.op.engine.gpu.GLContext;
import threadfool.op.engine.platform.window.Window;

public class EngineBootstrap
{
	public static void start(){
		Window window = new Window();
		window.create(800, 600, "Engine v.0.1");

		GLContext.init();

		GameLoop loop = new GameLoop(window);

		loop.run();
	}
}
