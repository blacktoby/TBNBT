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

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTParser {
	
	public static NBTTag parseTagById(DataInputStream in, int id, boolean named) throws IOException {
		switch(id) {
			case 0: return null;
			case 1: return parseTagByte(in, named);
			case 2: return parseTagShort(in, named);
			case 3: return parseTagInt(in, named);
			case 4: return parseTagLong(in, named);
			case 5: return parseTagFloat(in, named);
			case 6: return parseTagDouble(in, named);
			case 7: return parseTagByteArray(in, named);
			case 8: return parseTagString(in, named);
			case 9: return parseTagList(in, named);
			case 10: return parseTagCompound(in, named);
			case 11: return parseTagIntArray(in, named);
			default: return null;
		}
	}
	
	public static NBTTagByte parseTagByte(DataInputStream in, boolean named) throws IOException {
		NBTTagByte out = new NBTTagByte((byte) 0);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		out.setValue(in.readByte());
		return out;
	}
	
	public static NBTTagShort parseTagShort(DataInputStream in, boolean named) throws IOException {
		NBTTagShort out = new NBTTagShort((short) 0);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		out.setValue(in.readShort());
		return out;
	}
	
	public static NBTTagInt parseTagInt(DataInputStream in, boolean named) throws IOException {
		NBTTagInt out = new NBTTagInt(0);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		out.setValue(in.readInt());
		return out;
	}
	
	public static NBTTagLong parseTagLong(DataInputStream in, boolean named) throws IOException {
		NBTTagLong out = new NBTTagLong(0);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		out.setValue(in.readLong());
		return out;
	}
	
	public static NBTTagFloat parseTagFloat(DataInputStream in, boolean named) throws IOException {
		NBTTagFloat out = new NBTTagFloat(0);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		out.setValue(in.readFloat());
		;
		return out;
	}
	
	public static NBTTagDouble parseTagDouble(DataInputStream in, boolean named) throws IOException {
		NBTTagDouble out = new NBTTagDouble(0);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		out.setValue(in.readDouble());
		return out;
	}
	
	public static NBTTagByteArray parseTagByteArray(DataInputStream in, boolean named) throws IOException {
		NBTTagByteArray out = new NBTTagByteArray(null);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		int length = in.readInt();
		byte[] b = new byte[length];
		for(int i = 0; i < length; i++) {
			b[i] = in.readByte();
		}
		out.setValue(b);
		;
		return out;
	}
	
	public static NBTTagString parseTagString(DataInputStream in, boolean named) throws IOException {
		NBTTagString out = new NBTTagString("");
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		int length = in.readShort();
		byte[] str = new byte[length];
		for(int i = 0; i < length; i++) {
			str[i] = in.readByte();
		}
		out.setValue(new String(str));
		return out;
	}
	
	public static NBTTagList parseTagList(DataInputStream in, boolean named) throws IOException {
		NBTTagList out = new NBTTagList(null);
		List<NBTTag> aux = new ArrayList<>();
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		int typeId = in.readByte();
		int length = in.readInt();
		for(int i = 0; i < length; i++) {
			aux.add(parseTagById(in, typeId, false));
		}
		out.setValue(aux);
		out.setTypeId(typeId);
		return out;
	}
	
	public static NBTTagCompound parseTagCompound(DataInputStream in, boolean named) throws IOException {
		NBTTagCompound out = new NBTTagCompound(null);
		Map<String, NBTTag> aux = new HashMap<>();
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		int typeId = in.readByte();
		do {
			NBTTag tag = parseTagById(in, typeId, true);
			aux.put(tag.getName(), tag);
			typeId = in.readByte();
		} while(typeId != 0);
		out.setValue(aux);
		return out;
	}
	
	public static NBTTagIntArray parseTagIntArray(DataInputStream in, boolean named) throws IOException {
		NBTTagIntArray out = new NBTTagIntArray(null);
		if(named) {
			out.setName(parseTagString(in, false).getValue());
		}
		int length = in.readInt();
		int[] ints = new int[length];
		for(int i = 0; i < length; i++) {
			ints[i] = in.readInt();
		}
		out.setValue(ints);
		return out;
	}
}
