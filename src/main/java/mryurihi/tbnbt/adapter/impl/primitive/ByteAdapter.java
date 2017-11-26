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
package mryurihi.tbnbt.adapter.impl.primitive;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.google.common.reflect.TypeToken;

import mryurihi.tbnbt.TagType;
import mryurihi.tbnbt.adapter.AdapterRegistry;
import mryurihi.tbnbt.adapter.NBTAdapter;
import mryurihi.tbnbt.adapter.NBTParseException;

public class ByteAdapter extends NBTAdapter<Byte> {

	@Override
	public Byte fromNBT(TagType id, DataInputStream payload, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
		if(! id.equals(TagType.BYTE)) throw new NBTParseException(String.format("id %s does not match required id 1", id.getId()));
		try {
			return new Byte(payload.readByte());
		} catch(Exception e) {
			throw new NBTParseException(e);
		}
	}

	@Override
	public void toNBT(DataOutputStream out, Object object, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
		try {
			out.writeByte(((Byte)object).byteValue());
		} catch (Exception e) {
			throw new NBTParseException(e);
		}
	}
	
	@Override
	public TagType getId() {
		return TagType.BYTE;
	}
}
