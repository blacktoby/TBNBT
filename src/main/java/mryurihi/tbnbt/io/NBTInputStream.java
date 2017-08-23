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
package mryurihi.tbnbt.io;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import mryurihi.tbnbt.parser.NBTParser;
import mryurihi.tbnbt.tag.NBTTag;

public class NBTInputStream implements Closeable {
	
	private DataInputStream dis;
	
	public NBTInputStream(InputStream is, boolean compressed) throws IOException {
		if(compressed) {
			is = new GZIPInputStream(is);
		}
		dis = new DataInputStream(is);
	}
	
	public NBTInputStream(InputStream is) throws IOException {
		this(is, true);
	}
	
	public NBTTag readTag() throws IOException {
		byte type = dis.readByte();
		return NBTParser.parseTagById(dis, type, true);
	}

	@Override
	public void close() throws IOException {
		dis.close();
	}

}
