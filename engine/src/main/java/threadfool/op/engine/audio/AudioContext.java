package threadfool.op.engine.audio;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;

public class AudioContext
{
	private long device;
	private long context;

	public void init() {
		device = ALC10.alcOpenDevice((java.nio.ByteBuffer) null);
		if (device == 0) throw new RuntimeException("OpenAL: no audio device found");

		IntBuffer attribs = BufferUtils.createIntBuffer(1);
		attribs.put(0).flip();
		context = ALC10.alcCreateContext(device, attribs);
		ALC10.alcMakeContextCurrent(context);

		ALCCapabilities alcCaps = ALC.createCapabilities(device);
		AL.createCapabilities(alcCaps);

		System.out.println("OpenAL initialised");
	}

	public void dispose() {
		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
	}
}
