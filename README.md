  <img src="javengine.png" width="300"/>

# Game Engine

A lightweight 2D game engine built on LWJGL 3 (OpenGL 3.3, OpenAL, GLFW) and JOML.
Designed for top-down 2D games. Uses an entity-component-system (ECS) architecture.

---

## Requirements

- Java 17+
- Gradle (wrapper included)
- macOS ARM64 (native libs are configured for `macos-arm64` — change in `engine/build.gradle.kts` for other platforms)

---

## Running the demo

```bash
./gradlew :desktop:run
```

Drop art assets into `game/assets/textures/` and audio into `game/assets/audio/` to replace the white placeholder tiles. The engine falls back to white 1×1 textures if files are missing, so the game runs without any assets.

---

## Project structure

```
engine/   — core engine (GPU, windowing, ECS, systems)
game/     — your game code and assets
desktop/  — entry point / launcher
```

Your game code lives entirely in the `game` module. The `engine` module should not need to be touched.

---

## Creating a game

### 1. Create a scene

Every game is made of scenes. Extend `GameScene` and override four methods:

```java
public class MyScene extends GameScene {

    private final RenderSystem      renderSystem    = new RenderSystem();
    private final PlayerMovementSystem moveSystem   = new PlayerMovementSystem();
    private final CollisionSystem   collisionSystem = new CollisionSystem();

    @Override
    public void init(Window window, InputSystem input) {
        // Build your scene here — create GameObjects and add components
    }

    @Override
    public void update(InputSystem input) {
        // Run your systems every frame
        moveSystem.update(scene, input);
        collisionSystem.update(scene);
    }

    @Override
    public void render(Camera camera, Window window) {
        // Move the camera, then render
        camera.followCentered(player, window.getWidth(), window.getHeight());
        renderSystem.render(scene, camera, window);
    }

    @Override
    public void dispose() {
        // Release resources (textures, audio sources) when the scene exits
    }
}
```

### 2. Launch your scene

Pass your scene to `EngineBootstrap.start()` from the desktop launcher:

```java
// desktop/src/main/java/threadfool/op/launcher/DesktopLauncher.java
public static void main(String[] args) {
    EngineBootstrap.start(new MyScene());
}
```

---

## Core concepts

### GameObject

An entity that holds components. Components are stored by class — one per type.

```java
GameObject obj = new GameObject();
obj.addComponent(new Transform());
obj.addComponent(new SpriteRenderer(myTexture));
obj.addComponent(new Collider(32, 32));

// Retrieve later
Transform t = obj.getComponent(Transform.class);
```

### Transform

Position, rotation (radians), and scale in 2D world space.

```java
Transform t = new Transform();
t.position.set(200, 150);  // world-space X, Y
t.scale.set(32, 32);       // width, height in pixels
t.rotation = 0f;           // radians
```

Transforms use pixel-space coordinates. The ortho camera maps pixels 1:1 to screen pixels at zoom=1.

### Scene

A container for GameObjects. Systems iterate over it.

```java
scene.add(obj);

// Query objects by component
List<GameObject> movables = scene.getObjectsWith(Transform.class);
```

`GameScene` provides a `scene` field already — use `this.scene` inside `init()`.

---

## Rendering

### Loading a texture

```java
Texture tex = new Texture("game/assets/textures/player.png");  // filesystem path
```

Paths are relative to the working directory (project root when running via Gradle).

### SpriteRenderer

Attach a `SpriteRenderer` to any `GameObject` that has a `Transform`. The `RenderSystem` will draw it.

```java
// Textured quad (most common)
obj.addComponent(new SpriteRenderer(myTexture));

// Coloured quad with a tint
SpriteRenderer sr = new SpriteRenderer(myTexture);
sr.tint.set(1f, 0.3f, 0.3f, 1f);  // red tint
obj.addComponent(sr);
```

### RenderSystem

Call `renderSystem.render(scene, camera, window)` inside your scene's `render()` method. It draws every `GameObject` that has both a `SpriteRenderer` and a `Transform`.

### Camera

The `Camera` is owned by `GameLoop` and passed into `render()`. Common operations:

```java
// Follow a target, centering it on screen
camera.followCentered(player, window.getWidth(), window.getHeight());

// Zoom in/out
camera.zoom = 2f;  // 2x zoom

// Free-move
camera.position.set(x, y);
```

---

## Input

Poll keyboard state inside `update()` via the `InputSystem` argument:

```java
import org.lwjgl.glfw.GLFW;

if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) {
    // jump, shoot, etc.
}
```

Key constants are in `org.lwjgl.glfw.GLFW` — `GLFW_KEY_W`, `GLFW_KEY_LEFT`, etc.

### Built-in player movement

Attach `PlayerInput` to a `GameObject` and run `PlayerMovementSystem` each frame for free WASD movement:

```java
player.addComponent(new PlayerInput());   // speed defaults to 100.0
// Increase speed:
player.getComponent(PlayerInput.class).speed = 200f;
```

```java
// In update():
moveSystem.update(scene, input);
```

---

## Collision

### Collider component

```java
// Dynamic (gets pushed out of static objects)
obj.addComponent(new Collider(32, 32));

// Static (immovable obstacle)
obj.addComponent(new Collider(64, 64, true));

// With offset (AABB doesn't start at transform.position)
Collider c = new Collider(24, 24);
c.offsetX = 4f;
c.offsetY = 4f;
obj.addComponent(c);
```

The collider size is independent of the Transform scale — set them to match when you want pixel-perfect boxes.

### CollisionSystem

Run it every frame before rendering. It pushes dynamic objects out of anything they overlap:

```java
// In update():
collisionSystem.update(scene);
```

No callbacks yet — resolution is purely positional push.

---

## Audio

### Initialisation

`AudioContext` is initialised automatically by `EngineBootstrap`. Nothing to do.

### Loading sounds

```java
SoundBuffer sfx   = SoundBuffer.loadWav("game/assets/audio/footstep.wav");  // WAV (PCM)
SoundBuffer music = SoundBuffer.loadOgg("game/assets/audio/theme.ogg");     // OGG/Vorbis
```

### Playing sounds

```java
SoundSource source = new SoundSource();
source.setBuffer(music);
source.setLooping(true);
source.setGain(0.5f);   // 0.0 – 1.0
source.play();

// Later
source.pause();
source.stop();
source.dispose();  // call in scene.dispose()
```

### Background music pattern

```java
// In init():
SoundBuffer music = SoundBuffer.loadOgg("game/assets/audio/theme.ogg");
musicSource = new SoundSource();
musicSource.setBuffer(music);
musicSource.setLooping(true);
musicSource.play();

// Attach to a dummy object so SoundSystem can manage it
GameObject musicObj = new GameObject();
musicObj.addComponent(new Transform());
musicObj.addComponent(musicSource);
scene.add(musicObj);

// In dispose():
soundSystem.stopAll(scene);
musicSource.dispose();
```

---

## Scene management

Switch scenes from anywhere via `SceneManager`:

```java
// From inside a scene — pass SceneManager in or store a reference
sceneManager.switchTo(new GameOverScene());  // replaces current, calls dispose()
sceneManager.push(new PauseMenuScene());     // overlay — current scene stays beneath
sceneManager.pop();                          // return to previous scene
```

`GameLoop` owns the `SceneManager`. To access it from a scene you can pass it in via the constructor, or trigger the switch by storing a reference from `init()`.

---

## Delta time

Use `Time.delta()` for frame-rate-independent movement (seconds since last frame):

```java
t.position.x += speed * Time.delta();
```

`PlayerMovementSystem` already uses this.

---

## Mesh (low-level)

For custom geometry beyond quads:

```java
// Solid-colour triangle (no UVs, stride 3)
float[] verts = { -0.5f, -0.5f, 0f,  0.5f, -0.5f, 0f,  0f, 0.5f, 0f };
Mesh mesh = new Mesh(verts);

// Textured quad (UVs interleaved, stride 5: X Y Z U V)
Mesh quad = Mesh.createQuad();  // ready-made unit quad

// Custom textured geometry
float[] uvVerts = {
    -0.5f, -0.5f, 0f,  0f, 0f,
     0.5f, -0.5f, 0f,  1f, 0f,
     0.0f,  0.5f, 0f,  0.5f, 1f,
};
Mesh tri = new Mesh(uvVerts, true);
```

Pair with a `Shader` and use `SpriteRenderer(mesh, shader)` to render it.

---

## Adding a new system

Systems are plain classes. They receive a `Scene` and iterate over objects with specific components:

```java
public class HealthSystem {
    public void update(Scene scene) {
        for (GameObject obj : scene.getObjectsWith(Health.class)) {
            Health h = obj.getComponent(Health.class);
            if (h.current <= 0) {
                // handle death
            }
        }
    }
}
```

Instantiate it in your `GameScene` and call `update()` from `update(InputSystem)`.

---

## Asset path convention

| Asset type | Directory |
|---|---|
| Textures (PNG) | `game/assets/textures/` |
| Audio — music (OGG) | `game/assets/audio/` |
| Audio — SFX (WAV) | `game/assets/audio/` |

Paths are relative to the project root. When running with `./gradlew :desktop:run` the working directory is the project root.

---

## Platform natives

Natives are configured in `engine/build.gradle.kts`. Change the classifier to match your platform:

| Platform | Classifier |
|---|---|
| macOS ARM (M1/M2/M3) | `natives-macos-arm64` |
| macOS Intel | `natives-macos` |
| Linux x64 | `natives-linux` |
| Windows x64 | `natives-windows` |
