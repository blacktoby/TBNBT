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

import java.util.ArrayList;
import java.util.List;

public class NBTTagList extends NBTTag {
	
	private List<NBTTag> value;
	
	private int typeId;
	
	public NBTTagList(List<NBTTag> value, int typeId) {
		if(value != null) for(NBTTag tag: value) {
			tag.setName(null);
			this.value.add(tag);
		}
		this.typeId = typeId;
	}
	
	public NBTTagList(List<NBTTag> value) {
		this.value = value;
		this.typeId = 0;
	}
	
	public List<NBTTag> getValue() {
		return value;
	}
	
	public NBTTag get(int index) {
		return value.get(index);
	}
	
	public int getTypeId() {
		return typeId;
	}
	
	public void setValue(List<NBTTag> value) {
		this.value = value;
	}
	
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public void add(NBTTag tag) {
		tag.setName(null);
		if(typeId == 0) typeId = tag.getTagType();
		if(tag.getTagType() == typeId) value.add(tag);
		else throw new IllegalArgumentException(tag.getClass().getName() + " is not the type " + typeId);
	}
	
	public void add(int index, NBTTag tag) {
		tag.setName(null);
		if(typeId == 0) typeId = tag.getTagType();
		if(tag.getTagType() == typeId) value.add(index, tag);
		else throw new IllegalArgumentException(tag.getClass().getName() + " is not the type " + typeId);
	}
	
	public void remove(int index) {
		value.remove(index);
	}

	@Override
	byte[] getPayloadBytes() {
		List<Byte> aux = new ArrayList<>();
		try {
			aux.add((byte) typeId);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(this.toString() + " does not have a typeId");
		}
		for(byte b: new NBTTagInt(value.size()).getPayloadBytes()) aux.add(b);
		for(NBTTag t: value) for(byte b: t.getPayloadBytes()) aux.add(b);
		byte[] out = new byte[aux.size()];
		for(int i = 0; i < aux.size(); i++) {
			out[i] = aux.get(i);
		}
		if(name != null) {
			out = addName(out);
		}
		return out;
	}

	@Override
	byte getTagType() {
		return 9;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
