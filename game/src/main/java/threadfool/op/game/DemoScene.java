package threadfool.op.game;

import threadfool.op.engine.audio.SoundBuffer;
import threadfool.op.engine.audio.SoundSource;
import threadfool.op.engine.audio.SoundSystem;
import threadfool.op.engine.gpu.Texture;
import threadfool.op.engine.platform.input.InputSystem;
import threadfool.op.engine.platform.window.Window;
import threadfool.op.engine.render.Camera;
import threadfool.op.engine.scene.Collider;
import threadfool.op.engine.scene.GameScene;
import threadfool.op.engine.scene.GameObject;
import threadfool.op.engine.scene.SpriteRenderer;
import threadfool.op.engine.scene.Transform;
import threadfool.op.engine.systems.CollisionSystem;
import threadfool.op.engine.systems.PlayerInput;
import threadfool.op.engine.systems.PlayerMovementSystem;
import threadfool.op.engine.systems.RenderSystem;

public class DemoScene extends GameScene
{
	private final RenderSystem       renderSystem    = new RenderSystem();
	private final PlayerMovementSystem moveSystem    = new PlayerMovementSystem();
	private final CollisionSystem    collisionSystem = new CollisionSystem();
	private final SoundSystem        soundSystem     = new SoundSystem();

	private GameObject player;
	private SoundSource musicSource;

	@Override
	public void init(Window window, InputSystem input) {
		this.window = window;

		// ---------------------------------------------------------------
		// Textures — fall back to solid-colour placeholders if missing
		// ---------------------------------------------------------------
		Texture playerTex = loadTexture("game/assets/textures/player.png");
		Texture wallTex   = loadTexture("game/assets/textures/wall.png");
		Texture floorTex  = loadTexture("game/assets/textures/floor.png");

		// ---------------------------------------------------------------
		// Floor tiles (static, no collider)
		// ---------------------------------------------------------------
		int tileSize = 32;
		int cols = window.getWidth()  / tileSize + 2;
		int rows = window.getHeight() / tileSize + 2;
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				GameObject tile = new GameObject();
				Transform tt = new Transform();
				tt.position.set(col * tileSize, row * tileSize);
				tt.scale.set(tileSize, tileSize);
				tile.addComponent(tt);
				tile.addComponent(new SpriteRenderer(floorTex));
				scene.add(tile);
			}
		}

		// ---------------------------------------------------------------
		// Static wall
		// ---------------------------------------------------------------
		GameObject wall = new GameObject();
		Transform wt = new Transform();
		wt.position.set(400, 200);
		wt.scale.set(64, 64);
		wall.addComponent(wt);
		wall.addComponent(new SpriteRenderer(wallTex));
		wall.addComponent(new Collider(64, 64, true));
		scene.add(wall);

		// Second wall
		GameObject wall2 = new GameObject();
		Transform wt2 = new Transform();
		wt2.position.set(250, 350);
		wt2.scale.set(96, 32);
		wall2.addComponent(wt2);
		wall2.addComponent(new SpriteRenderer(wallTex));
		wall2.addComponent(new Collider(96, 32, true));
		scene.add(wall2);

		// ---------------------------------------------------------------
		// Player
		// ---------------------------------------------------------------
		player = new GameObject();
		Transform pt = new Transform();
		pt.position.set(100, 100);
		pt.scale.set(32, 32);
		player.addComponent(pt);
		player.addComponent(new PlayerInput());
		player.addComponent(new SpriteRenderer(playerTex));
		player.addComponent(new Collider(32, 32));
		scene.add(player);

		// ---------------------------------------------------------------
		// Background music (optional — skipped if file is missing)
		// ---------------------------------------------------------------
		SoundBuffer music = loadOgg("game/assets/audio/theme.ogg");
		if (music != null) {
			musicSource = new SoundSource();
			musicSource.setBuffer(music);
			musicSource.setLooping(true);
			musicSource.setGain(0.5f);
			musicSource.play();

			GameObject musicObj = new GameObject();
			musicObj.addComponent(new Transform());
			musicObj.addComponent(musicSource);
			scene.add(musicObj);
		}
	}

	@Override
	public void update(InputSystem input) {
		moveSystem.update(scene, input);
		collisionSystem.update(scene);
		soundSystem.update(scene);
	}

	@Override
	public void render(Camera camera, Window window) {
		camera.followCentered(player, window.getWidth(), window.getHeight());
		renderSystem.render(scene, camera, window);
	}

	@Override
	public void dispose() {
		soundSystem.stopAll(scene);
		if (musicSource != null) musicSource.dispose();
	}

	// ---------------------------------------------------------------
	// Helpers
	// ---------------------------------------------------------------
	private Texture loadTexture(String path) {
		try {
			return new Texture(path);
		} catch (Exception e) {
			System.out.println("Texture not found: " + path + " — using white placeholder");
			return Texture.createWhite();
		}
	}

	private SoundBuffer loadOgg(String path) {
		try {
			return SoundBuffer.loadOgg(path);
		} catch (Exception e) {
			System.out.println("Audio not found: " + path + " — music disabled");
			return null;
		}
	}
}
