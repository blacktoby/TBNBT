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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import mryurihi.tbnbt.parser.TagType;

public class NBTTagIntArray extends NBTTag {

	private int[] value;
	
	public NBTTagIntArray(int[] value) {
		this.value = value;
	}
	
	public int[] getValue() {
		return value;
	}
	
	public void setValue(int[] value) {
		this.value = value;
	}
	
	@Override
	public List<Byte> getPayloadBytes() {
		List<Byte> out = new ArrayList<>();
		if(name != null) out.addAll(new NBTTagString(name).getPayloadBytes());
		out.addAll(new NBTTagInt(value.length).getPayloadBytes());
		for(int i: value) for(byte b: ByteBuffer.allocate(4).putInt(i).array()) out.add(b);
		return out;
	}

	@Override
	public TagType getTagType() {
		return TagType.getTypeById(11);
	}
	
	@Override
	public String toString() {
		String out = "[I;";
		for(int i: value) {
			out += String.valueOf(i) + ", ";
		}
		out = out.substring(0, out.length() - 2);
		out += "]";
		return out;
	}
}
