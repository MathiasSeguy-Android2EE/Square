/**
 * <ul>
 * <li>OkioDecoratorSample</li>
 * <li>com.android2ee.formation.lib.squarelibs.tool</li>
 * <li>27/01/2016</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.formation.lib.squarelibs.tool;

import android.util.Log;

import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Timeout;

/**
 * Created by Mathias Seguy - Android2EE on 27/01/2016.
 */
public class SinkDecoratorSample implements Sink {
	/**
	 * Sink into which the GZIP format is written.
	 */
	private final BufferedSink sink;

	public SinkDecoratorSample(Sink sink) {
		if (sink == null) throw new IllegalArgumentException("sink == null");
		this.sink = Okio.buffer(sink);
	}

	/**
	 * Removes {@code byteCount} bytes from {@code source} and appends them to this.
	 *
	 * @param source
	 * @param byteCount
	 */
	@Override
	public void write(Buffer source, long byteCount) throws IOException {
		try {
			Log.e("SinkDecoratorSample", "write has been called");
			//find the bytearray to write
			ByteString bytes = ((BufferedSource) source).readByteString(byteCount);
			//here there is an instanciation
			String data = bytes.utf8();
			int currentIndex=0,substringSize=20;
			for (int i = 0; i+substringSize < data.length(); i=i+substringSize) {
				currentIndex=i+substringSize;
				Log.e("SinkDecoratorSample", data.substring(i,i+substringSize));
			}
			Log.e("SinkDecoratorSample", "The source retunrs " + data.substring(currentIndex));
			sink.write(bytes);
		} catch (Exception e) {
			Log.e("SinkDecoratorSample", "a crash occurs :", e);
		}
	}

	/**
	 * Pushes all buffered bytes to their final destination.
	 */
	@Override
	public void flush() throws IOException {
		sink.flush();
	}

	/**
	 * Returns the timeout for this sink.
	 */
	@Override
	public Timeout timeout() {
		return sink.timeout();
	}

	/**
	 * Pushes all buffered bytes to their final destination and releases the
	 * resources held by this sink. It is an error to write a closed sink. It is
	 * safe to close a sink more than once.
	 */
	@Override
	public void close() throws IOException {
		sink.close();
	}
}
