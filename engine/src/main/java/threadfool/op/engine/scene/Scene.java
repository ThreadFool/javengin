package threadfool.op.engine.scene;

import java.util.ArrayList;
import java.util.List;

public class Scene
{
	private final List<GameObject> objects = new ArrayList<>();

	public void add(GameObject obj)
	{
		objects.add(obj);
	}

	public List<GameObject> getObjects()
	{
		return objects;
	}

	public <T> List<GameObject> getObjectsWith(Class<T> componentClass)
	{
		return objects.stream().filter(o -> o.hasComponent(componentClass)).toList();
	}
}
