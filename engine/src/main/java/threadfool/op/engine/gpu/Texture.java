package threadfool.op.engine.gpu;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

public class Texture
{
	private final int id;
	private final int width;
	private final int height;

	/** Creates a 1×1 white RGBA texture — useful as a placeholder. */
	public static Texture createWhite() {
		int texId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texId);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		pixel.put((byte) 0xFF).put((byte) 0xFF).put((byte) 0xFF).put((byte) 0xFF).flip();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 1, 1, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixel);
		glBindTexture(GL_TEXTURE_2D, 0);
		return new Texture(texId, 1, 1);
	}

	private Texture(int id, int width, int height) {
		this.id     = id;
		this.width  = width;
		this.height = height;
	}

	public Texture(String path)
	{
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);

		STBImage.stbi_set_flip_vertically_on_load(true);
		ByteBuffer data = STBImage.stbi_load(path, w, h, channels, 4);
		if (data == null) {
			throw new RuntimeException("Failed to load texture: " + path + " — " + STBImage.stbi_failure_reason());
		}

		this.width  = w.get(0);
		this.height = h.get(0);

		id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height,
				0, GL_RGBA, GL_UNSIGNED_BYTE, data);

		STBImage.stbi_image_free(data);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void bind(int unit) {
		glActiveTexture(GL_TEXTURE0 + unit);
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void dispose() {
		glDeleteTextures(id);
	}

	public int getWidth()  { return width; }
	public int getHeight() { return height; }
}
