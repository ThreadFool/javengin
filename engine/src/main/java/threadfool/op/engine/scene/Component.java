package threadfool.op.engine.scene;

import threadfool.op.engine.platform.input.InputSystem;

public abstract class Component
{
	protected GameObject gameObject;

	public void update(InputSystem input) {
	}

	public void start() {}
	public void destroy() {}
}
