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
package com.github.mryurihi.tbnbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.github.mryurihi.tbnbt.TagType;

public class NBTTagByte extends NBTTag {
	
	private byte value;
	
	public NBTTagByte(byte value) {
		this.value = value;
	}
	
	NBTTagByte() {
	}
	
	public byte getValue() {
		return value;
	}
	
	public void setValue(byte value) {
		this.value = value;
	}
	
	@Override
	public TagType getTagType() {
		return TagType.BYTE;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value) + "b";
	}
	
	@Override
	public void writePayloadBytes(DataOutputStream out) throws IOException {
		out.writeByte(value);
	}
	
	@Override
	public NBTTag readPayloadBytes(DataInputStream in) throws IOException {
		this.value = in.readByte();
		return this;
	}
	
	@Override
	protected boolean equalsTag(NBTTag tag) {
		return tag.getTagType().equals(TagType.BYTE) && tag.getAsTagByte().getValue() == value;
	}
}
