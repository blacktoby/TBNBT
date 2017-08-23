/*
MIT License

Copyright (c) 2017 MrYurihi Redstone

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package mryurihi.tbnbt;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class NBTOutputStream implements Closeable {

	private OutputStream os;
	
	public NBTOutputStream(OutputStream out, boolean compressed) throws IOException {
		if(compressed) {
			out = new GZIPOutputStream(out);
		}
		os = out;
	}
	
	public NBTOutputStream(OutputStream out) throws IOException {
		this(out, true);
	}
	
	public void writeTag(NBTTag tag, String name) throws IOException {
		tag.setName(name);
		byte[] bytes = tag.getPayloadBytes();
		byte[] out = new byte[bytes.length + 1];
		System.arraycopy(bytes, 0, out, 1, bytes.length);
		out[0] = tag.getTagType();
		os.write(out);
	}
	
	@Override
	public void close() throws IOException {
			os.close();
	}
}