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
	byte[] getPayloadBytes() {
		int length = value.length;
		int[] aux = new int[length + 1];
		byte[] out = new byte[length * 4 + 4];
		aux[0] = length;
		for(int i = 0; i < aux.length; i++) aux[i] = value[i];
		for(int i = 0; i < aux.length; i++) {
			byte[] bytes = new NBTTagInt(aux[i]).getPayloadBytes();
			for(int i2 = 0; i < bytes.length; i++) {
				out[i * bytes.length + i2] = bytes[i];
			}
		}
		
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
		String out = "[I;";
		for(int i: value) {
			out += String.valueOf(i) + ", ";
		}
		out = out.substring(0, out.length() - 2);
		out += "]";
		return out;
	}
}
