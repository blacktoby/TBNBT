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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NBTTagCompound extends NBTTag {

	private Map<String, NBTTag> value;
	
	public NBTTagCompound(Map<String, NBTTag> value) {
		this.value = new HashMap<>();
		for(Entry<String, NBTTag> entry: value.entrySet()) {
			NBTTag tag = entry.getValue();
			tag.setName(entry.getKey());
			this.value.put(entry.getKey(), tag);
		}
	}
	
	public Map<String, NBTTag> getValue() {
		return value;
	}
	
	public NBTTag get(String key) {
		return value.get(key);
	}
	
	public void setValue(Map<String, NBTTag> value) {
		Map<String, NBTTag> aux = new HashMap<>();
		value.forEach((k, v) -> {
			v.setName(k);
			aux.put(k, v);
		});
		this.value = aux;
	}
	
	public void put(String key, NBTTag value) {
		value.setName(key);
		this.value.put(key, value);
	}
	
	public void remove(String key) {
		this.value.remove(key);
	}
	
	@Override
	byte[] getPayloadBytes() {
		List<Byte> aux = new ArrayList<>();
		for(Entry<String, NBTTag> entry: value.entrySet()) {
			aux.add(entry.getValue().getTagType());
			for(byte b: entry.getValue().getPayloadBytes()) {
				aux.add(b);
			}
		}
		aux.add((byte) 0);
		
		byte[] out = new byte[aux.size()];
		for(int i = 0; i < aux.size(); i++) out[i] = aux.get(i).byteValue();
		if(name != null) {
			out = addName(out);
		}
		return out;
	}

	@Override
	byte getTagType() {
		return 10;
	}

}
