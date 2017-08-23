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

import java.util.List;

public abstract class NBTTag {
	protected String name = null;
	
	NBTTag setName(String name) {
		this.name = name;
		return this;
	}
	
	protected byte[] listToArray(List<Byte> list) {
		byte[] out = new byte[list.size()];
		for(int i = 0; i < list.size(); i++) out[i] = list.get(i);
		return out;
	}
	
	public String getName() {
		return name;
	}
	
	abstract List<Byte> getPayloadBytes();
	
	abstract byte getTagType();
	
	public NBTTagByte getAsTagByte() {
		return NBTTagByte.class.cast(this);
	}
	
	public NBTTagShort getAsTagShort() {
		return NBTTagShort.class.cast(this);
	}
	
	public NBTTagInt getAsTagInt() {
		return NBTTagInt.class.cast(this);
	}
	
	public NBTTagLong getAsTagLong() {
		return NBTTagLong.class.cast(this);
	}
	
	public NBTTagFloat getAsTagFloat() {
		return NBTTagFloat.class.cast(this);
	}
	
	public NBTTagDouble getAsTagDouble() {
		return NBTTagDouble.class.cast(this);
	}
	
	public NBTTagByteArray getAsTagByteArray() {
		return NBTTagByteArray.class.cast(this);
	}
	
	public NBTTagString getAsTagString() {
		return NBTTagString.class.cast(this);
	}
	
	public NBTTagList getAsTagList() {
		return NBTTagList.class.cast(this);
	}
	
	public NBTTagCompound getAsTagCompound() {
		return NBTTagCompound.class.cast(this);
	}
}
