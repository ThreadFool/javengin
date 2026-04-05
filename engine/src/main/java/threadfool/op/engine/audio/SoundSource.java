package threadfool.op.engine.audio;

import static org.lwjgl.openal.AL10.*;

public class SoundSource
{
	private final int sourceId;

	public SoundSource() {
		sourceId = alGenSources();
	}

	public void setBuffer(SoundBuffer buffer) {
		alSourcei(sourceId, AL_BUFFER, buffer.getId());
	}

	public void setLooping(boolean loop) {
		alSourcei(sourceId, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
	}

	public void setGain(float gain) {
		alSourcef(sourceId, AL_GAIN, gain);
	}

	public void play()  { alSourcePlay(sourceId); }
	public void pause() { alSourcePause(sourceId); }
	public void stop()  { alSourceStop(sourceId); }

	public boolean isPlaying() {
		return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
	}

	public void dispose() { alDeleteSources(sourceId); }
}
