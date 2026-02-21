package threadfool.op.engine.scene;

import java.util.ArrayList;
import java.util.List;

public class NewScene
{
	private final List<NewGameObject> objects = new ArrayList<>();

	public void add(NewGameObject obj)
	{
		objects.add(obj);
	}

	public List<NewGameObject> getObjects()
	{
		return objects;
	}

	public <T> List<NewGameObject> getObjectsWith(Class<T> componentClass)
	{
		return objects.stream().filter(o -> o.hasComponent(componentClass)).toList();
	}
}
