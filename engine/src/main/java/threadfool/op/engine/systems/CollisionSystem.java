package threadfool.op.engine.systems;

import java.util.List;

import threadfool.op.engine.scene.Collider;
import threadfool.op.engine.scene.GameObject;
import threadfool.op.engine.scene.Scene;
import threadfool.op.engine.scene.Transform;

public class CollisionSystem
{
	public void update(Scene scene) {
		List<GameObject> collidables = scene.getObjectsWith(Collider.class)
				.stream().filter(o -> o.hasComponent(Transform.class)).toList();

		for (int i = 0; i < collidables.size(); i++) {
			for (int j = i + 1; j < collidables.size(); j++) {
				GameObject a = collidables.get(i);
				GameObject b = collidables.get(j);

				Collider ca = a.getComponent(Collider.class);
				Collider cb = b.getComponent(Collider.class);
				Transform ta = a.getComponent(Transform.class);
				Transform tb = b.getComponent(Transform.class);

				if (ca.isStatic && cb.isStatic) continue;
				if (!overlaps(ca, ta, cb, tb)) continue;

				resolveOverlap(ca, ta, cb, tb);
			}
		}
	}

	private boolean overlaps(Collider ca, Transform ta, Collider cb, Transform tb) {
		return ca.minX(ta) < cb.maxX(tb) && ca.maxX(ta) > cb.minX(tb)
			&& ca.minY(ta) < cb.maxY(tb) && ca.maxY(ta) > cb.minY(tb);
	}

	private void resolveOverlap(Collider ca, Transform ta, Collider cb, Transform tb) {
		float overlapX = Math.min(ca.maxX(ta), cb.maxX(tb)) - Math.max(ca.minX(ta), cb.minX(tb));
		float overlapY = Math.min(ca.maxY(ta), cb.maxY(tb)) - Math.max(ca.minY(ta), cb.minY(tb));

		if (overlapX < overlapY) {
			float push = (ca.minX(ta) < cb.minX(tb)) ? -overlapX : overlapX;
			if (!ca.isStatic && !cb.isStatic) {
				ta.position.x += push / 2f;
				tb.position.x -= push / 2f;
			} else if (!ca.isStatic) {
				ta.position.x += push;
			} else {
				tb.position.x -= push;
			}
		} else {
			float push = (ca.minY(ta) < cb.minY(tb)) ? -overlapY : overlapY;
			if (!ca.isStatic && !cb.isStatic) {
				ta.position.y += push / 2f;
				tb.position.y -= push / 2f;
			} else if (!ca.isStatic) {
				ta.position.y += push;
			} else {
				tb.position.y -= push;
			}
		}
	}
}
