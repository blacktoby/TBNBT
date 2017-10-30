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
import java.util.List;

public class NBTTagString extends NBTTag {
	
	private String value;
	
	public NBTTagString(String value) {
		if(value.length() > Short.MAX_VALUE) throw new IllegalArgumentException("String to long");
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		if(value.length() > Short.MAX_VALUE) throw new IllegalArgumentException("String to long");
		this.value = value;
	}
	
	@Override
	public List<Byte> getPayloadBytes() {
		List<Byte> out = new ArrayList<>();
		if(name != null) out.addAll(new NBTTagString(name).getPayloadBytes());
		out.addAll(new NBTTagShort((short) value.length()).getPayloadBytes());
		for(byte b: value.getBytes()) out.add(b);
		return out;
	}

	@Override
	public byte getTagType() {
		return 8;
	}
	
	@Override
	public String toString() {
		return value;
	}
}