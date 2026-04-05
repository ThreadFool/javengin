package threadfool.op.engine.audio;

import static org.lwjgl.openal.AL10.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;

public class SoundBuffer
{
	private final int id;

	private SoundBuffer(int alBufferId) {
		this.id = alBufferId;
	}

	/** Load an OGG/Vorbis file from the filesystem. */
	public static SoundBuffer loadOgg(String path) {
		ByteBuffer fileData = readToBuffer(path);

		IntBuffer error = BufferUtils.createIntBuffer(1);
		long decoder = STBVorbis.stb_vorbis_open_memory(fileData, error, null);
		if (decoder == 0) {
			throw new RuntimeException("Failed to open OGG: " + path + " (error " + error.get(0) + ")");
		}

		STBVorbisInfo info = STBVorbisInfo.malloc();
		STBVorbis.stb_vorbis_get_info(decoder, info);
		int channels   = info.channels();
		int sampleRate = info.sample_rate();
		info.free();

		int numSamples = STBVorbis.stb_vorbis_stream_length_in_samples(decoder);
		ShortBuffer pcm = BufferUtils.createShortBuffer(numSamples * channels);
		STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
		STBVorbis.stb_vorbis_close(decoder);

		int format = (channels == 1) ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
		int bufId = alGenBuffers();
		alBufferData(bufId, format, pcm, sampleRate);

		return new SoundBuffer(bufId);
	}

	/** Load a WAV file (PCM, 8-bit or 16-bit) from the filesystem. */
	public static SoundBuffer loadWav(String path) {
		ByteBuffer data = readToBuffer(path);

		// Skip "RIFF" + 4-byte file size + "WAVE"
		data.position(12);

		int format    = AL_FORMAT_MONO16;
		int sampleRate = 44100;
		ByteBuffer pcmData = null;

		while (data.remaining() >= 8) {
			int chunkId   = data.getInt();   // big-endian tag as int
			int chunkSize = Integer.reverseBytes(data.getInt()); // WAV is little-endian

			if (chunkId == 0x666D7420) { // "fmt "
				int audioFormat   = Short.toUnsignedInt(Short.reverseBytes(data.getShort()));
				int channels      = Short.toUnsignedInt(Short.reverseBytes(data.getShort()));
				sampleRate        = Integer.reverseBytes(data.getInt());
				data.getInt(); // byte rate
				data.getShort(); // block align
				int bitsPerSample = Short.toUnsignedInt(Short.reverseBytes(data.getShort()));
				// skip extra fmt bytes
				if (chunkSize > 16) data.position(data.position() + chunkSize - 16);

				if (channels == 1) {
					format = (bitsPerSample == 8) ? AL_FORMAT_MONO8 : AL_FORMAT_MONO16;
				} else {
					format = (bitsPerSample == 8) ? AL_FORMAT_STEREO8 : AL_FORMAT_STEREO16;
				}
			} else if (chunkId == 0x64617461) { // "data"
				byte[] bytes = new byte[chunkSize];
				data.get(bytes);
				pcmData = ByteBuffer.wrap(bytes);
			} else {
				// skip unknown chunk
				data.position(data.position() + chunkSize);
			}
		}

		if (pcmData == null) throw new RuntimeException("No data chunk found in WAV: " + path);

		int bufId = alGenBuffers();
		alBufferData(bufId, format, pcmData, sampleRate);
		return new SoundBuffer(bufId);
	}

	private static ByteBuffer readToBuffer(String path) {
		try (InputStream in = SoundBuffer.class.getClassLoader().getResourceAsStream(path)) {
			if (in != null) {
				byte[] bytes = in.readAllBytes();
				ByteBuffer buf = MemoryUtil.memAlloc(bytes.length);
				buf.put(bytes).flip();
				return buf;
			}
		} catch (IOException ignored) {}

		// Fall back to filesystem path
		try {
			byte[] bytes = java.nio.file.Files.readAllBytes(java.nio.file.Path.of(path));
			ByteBuffer buf = MemoryUtil.memAlloc(bytes.length);
			buf.put(bytes).flip();
			return buf;
		} catch (IOException e) {
			throw new RuntimeException("Cannot read audio file: " + path, e);
		}
	}

	public int getId() { return id; }

	public void dispose() { alDeleteBuffers(id); }
}
