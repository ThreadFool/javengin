package threadfool.op.launcher;

import threadfool.op.engine.bootstrap.EngineBootstrap;
import threadfool.op.game.DemoScene;

public class DesktopLauncher {
	public static void main(String[] args)
	{
		EngineBootstrap.start(new DemoScene());
	}
}
