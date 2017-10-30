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
package mryurihi.tbnbt.stream;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import com.google.common.reflect.TypeToken;

import mryurihi.tbnbt.adapter.AdapterRegistry;
import mryurihi.tbnbt.adapter.NBTAdapter;
import mryurihi.tbnbt.adapter.NBTParseException;
import mryurihi.tbnbt.tag.NBTTag;

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
		List<Byte> bytes = tag.getPayloadBytes();
		bytes.add(0, tag.getTagType());
		byte[] out = new byte[bytes.size()];
		for(int i = 0; i < bytes.size(); i++) out[i] = bytes.get(i);
		os.write(out);
	}
	
	public <T> void writeFromObject(TypeToken<T> type, Object obj, AdapterRegistry registry) throws NBTParseException, IOException {
		DataOutputStream dos = new DataOutputStream(os);
		NBTAdapter<?> adapter = registry.getAdapterForObject(type);
		dos.writeByte(adapter.getId().getId());
		dos.writeShort(0);
		adapter.toNBT(dos, obj, type, registry);
	}
	
	public <T> void writeFromObject(TypeToken<T> type, Object obj) throws NBTParseException, IOException {
		writeFromObject(type, obj, new AdapterRegistry());
	}
	
	@Override
	public void close() throws IOException {
			os.close();
	}
}