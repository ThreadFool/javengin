package threadfool.op.engine.scene;

import java.util.HashMap;
import java.util.Map;

public class NewGameObject
{
	public final Transform transform = new Transform();
	private final Map<Class<?>, Object> components = new HashMap<>();

	public <T> void addComponent(T component)
	{
		components.put(component.getClass(), component);
	}

	public <T> T getComponent(Class<T> type)
	{
		return type.cast(components.get(type));
	}

	public <T> boolean hasComponent(Class<T> type)
	{
		return components.containsKey(type);
	}
}
