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

public class NBTTagByteArray extends NBTTag {

	private byte[] value;
	
	public NBTTagByteArray(byte[] value) {
		this.value = value;
	}
	
	public byte[] getValue() {
		return value;
	}
	
	public void setValue(byte[] value) {
		this.value = value;
	}
	
	@Override
	byte[] getPayloadBytes() {
		int length = value.length;
		byte[] out = new byte[length + 4];
		byte[] lengthB = new NBTTagInt(length).getPayloadBytes();
		for(int i = 0; i < 4; i++) out[i] = lengthB[i];
		for(int i = 4; i < out.length; i++) out[i] = value[i - 4];
		if(name != null) {
			out = addName(out);
		}
		return out;
	}

	@Override
	byte getTagType() {
		return 7;
	}
	
	@Override
	public String toString() {
		String out = "[";
		for(byte b: value) {
			out += String.valueOf(b) + ", ";
		}
		out = out.substring(0, out.length() - 2);
		out += "]";
		return out;
	}
}
