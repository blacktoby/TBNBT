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
package mryurihi.tbnbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import mryurihi.tbnbt.TagType;

public class NBTTagCompound extends NBTTag {

	private Map<String, NBTTag> value;
	
	public NBTTagCompound(Map<String, NBTTag> value) {
		this.value = new HashMap<>();
		if(value != null) for(Entry<String, NBTTag> entry: value.entrySet()) {
			NBTTag tag = entry.getValue();
			this.value.put(entry.getKey(), tag);
		}
	}
	
	NBTTagCompound() {}
	
	public Map<String, NBTTag> getValue() {
		return value;
	}
	
	public NBTTag get(String key) {
		return value.get(key);
	}
	
	public NBTTagCompound setValue(Map<String, NBTTag> value) {
		Map<String, NBTTag> aux = new HashMap<>();
		value.forEach((k, v) -> {
			aux.put(k, v);
		});
		this.value = aux;
		return this;
	}
	
	public NBTTagCompound put(String key, NBTTag value) {
		this.value.put(key, value);
		return this;
	}
	
	public NBTTagCompound remove(String key) {
		this.value.remove(key);
		return this;
	}
	
	@Override
	public void writePayloadBytes(DataOutputStream out) throws IOException {
		value = new HashMap<>();
		for(Entry<String, NBTTag> entry: value.entrySet()) {
			out.writeByte((byte) entry.getValue().getTagType().getId());
			new NBTTagString(entry.getKey()).writePayloadBytes(out);
			entry.getValue().writePayloadBytes(out);
		}
		out.writeByte(0);
	}
	
	@Override
	public NBTTag readPayloadBytes(DataInputStream in) throws IOException {
		byte type = 0;
		do {
			type = in.readByte();
			value.put(new NBTTagString().readPayloadBytes(in).getAsTagString().getValue(), NBTTag.newTagByType(TagType.getTypeById(type), in));
		} while(type != 0);
		return this;
	}

	@Override
	public TagType getTagType() {
		return TagType.COMPOUND;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
