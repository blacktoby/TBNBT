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
import java.util.ArrayList;
import java.util.List;

import mryurihi.tbnbt.TagType;

public class NBTTagList extends NBTTag {
	
	private List<NBTTag> value;
	
	private TagType typeId;
	
	public NBTTagList(List<NBTTag> value, TagType typeId) {
		this.value = new ArrayList<>();
		if(value != null) for(NBTTag tag: value) {
			this.value.add(tag);
		}
		this.typeId = typeId;
	}
	
	public NBTTagList(TagType typeId) {
		value = new ArrayList<>();
		this.typeId = typeId;
	}
	
	public NBTTagList(List<NBTTag> value) {
		this.value = value;
		if(value != null && ! value.isEmpty()) this.typeId = value.get(0).getTagType();
		else throw new IllegalArgumentException("Cannot get tag type of list");
	}
	
	NBTTagList() {}
	
	public List<NBTTag> getValue() {
		return value;
	}
	
	public NBTTag get(int index) {
		return value.get(index);
	}
	
	public TagType getTypeId() {
		return typeId;
	}
	
	public void setValue(List<NBTTag> value) {
		this.value = value;
	}
	
	public NBTTagList setTypeId(TagType typeId) {
		this.typeId = typeId;
		return this;
	}
	
	public NBTTagList add(NBTTag tag) {
		if(tag.getTagType() == typeId) value.add(tag);
		else throw new IllegalArgumentException(tag.getClass().getName() + " is not the type " + typeId);
		return this;
	}
	
	public NBTTagList add(int index, NBTTag tag) {
		if(tag.getTagType() == typeId) value.add(index, tag);
		else throw new IllegalArgumentException(tag.getClass().getName() + " is not the type " + typeId);
		return this;
	}
	
	public NBTTagList remove(int index) {
		value.remove(index);
		return this;
	}
	
	public NBTTagList set(int index, NBTTag tag) {
		if(typeId.equals(TagType.END)) typeId = tag.getTagType();
		if(tag.getTagType() == typeId) value.set(index, tag);
		else throw new IllegalArgumentException(tag.getClass().getName() + " is not the type " + typeId);
		return this;
	}

	@Override
	public void writePayloadBytes(DataOutputStream out) throws IOException {
		out.writeByte(typeId.getId());
		out.writeInt(value.size());
		for(NBTTag t: value) t.writePayloadBytes(out);
	}
	
	@Override
	public NBTTag readPayloadBytes(DataInputStream in) throws IOException {
		value = new ArrayList<>();
		byte id = in.readByte();
		int length = in.readInt();
		typeId = TagType.getTypeById(id);
		value = new ArrayList<>();
		for(int i = 0; i < length; i++) value.add(NBTTag.newTagByType(typeId, in));
		return this;
	}

	@Override
	public TagType getTagType() {
		return TagType.LIST;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
