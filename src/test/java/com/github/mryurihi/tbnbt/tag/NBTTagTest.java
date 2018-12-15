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

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.github.mryurihi.tbnbt.TagType;
import com.github.mryurihi.tbnbt.tag.NBTTag;
import com.github.mryurihi.tbnbt.tag.NBTTagByte;
import com.github.mryurihi.tbnbt.tag.NBTTagByteArray;
import com.github.mryurihi.tbnbt.tag.NBTTagCompound;
import com.github.mryurihi.tbnbt.tag.NBTTagDouble;
import com.github.mryurihi.tbnbt.tag.NBTTagFloat;
import com.github.mryurihi.tbnbt.tag.NBTTagInt;
import com.github.mryurihi.tbnbt.tag.NBTTagList;
import com.github.mryurihi.tbnbt.tag.NBTTagLong;
import com.github.mryurihi.tbnbt.tag.NBTTagShort;
import com.github.mryurihi.tbnbt.tag.NBTTagString;

class NBTTagTest {

	@Nested
	class testNewTagByType {
		
		DataInputStream getData(int ...bytes) {
			byte[] buf = new byte[bytes.length];
			for(int i = 0; i < bytes.length; i++) {
				buf[i] =  (byte) bytes[i];
			}
			return new DataInputStream(new ByteArrayInputStream(buf));
		}
		
		@Test
		void byteTag() throws IOException {
			DataInputStream dis = getData(0x12);
			assertEquals(new NBTTagByte((byte) 0x12), NBTTag.newTagByType(TagType.BYTE, dis));
			dis.close();
		}
		
		@Test
		void shortTag() throws IOException {
			DataInputStream dis = getData(0x7A, 0x39);
			assertEquals(new NBTTagShort((short) 0x7A39), NBTTag.newTagByType(TagType.SHORT, dis));
			dis.close();
		}
		
		@Test
		void intTag() throws IOException {
			DataInputStream dis = getData(0x86, 0xA8, 0xC4, 0x21);
			assertEquals(new NBTTagInt(0x86A8C421), NBTTag.newTagByType(TagType.INT, dis));
			dis.close();
		}
		
		@Test
		void longTag() throws IOException {
			DataInputStream dis = getData(
				0x18, 0x3B, 0x49, 0xD3,
				0x0E, 0x01, 0x2A, 0x19
			);
			assertEquals(new NBTTagLong(0x183B49D30E012A19l), NBTTag.newTagByType(TagType.LONG, dis));
			dis.close();
		}
		
		@Test
		void floatTag() throws IOException {
			DataInputStream dis = getData(0x3E, 0x20, 0x00, 0x00);
			assertEquals(new NBTTagFloat(0.15625f), NBTTag.newTagByType(TagType.FLOAT, dis));
			dis.close();
		}
		
		@Test
		void doubleTag() throws IOException {
			DataInputStream dis = getData(
				0x3F, 0xD5, 0x55, 0x55,
				0x55, 0x55, 0x55, 0x55
			);
			assertEquals(new NBTTagDouble(0.333333333333333314829616256247390992939472198486328125), NBTTag.newTagByType(TagType.DOUBLE, dis));
			dis.close();
		}
		
		@Test
		void byteArrayTag() throws IOException {
			DataInputStream dis = getData(
				0x00, 0x00, 0x00, 0x08,
				0x4D, 0x72, 0x59, 0x75,
				0x72, 0x69, 0x68, 0x69
			);
			assertEquals(new NBTTagByteArray(new byte[] {0x4D, 0x72, 0x59, 0x75, 0x72, 0x69, 0x68, 0x69}), NBTTag.newTagByType(TagType.BYTE_ARRAY, dis));
			dis.close();
		}
		
		@Test
		void stringTag() throws IOException {
			DataInputStream dis = getData(
				0x00, 0x08, 'M' , 'r',
				'Y' , 'u' , 'r' , 'i',
				'h' , 'i'
			);
			assertEquals(new NBTTagString("MrYurihi"), NBTTag.newTagByType(TagType.STRING, dis));
			dis.close();
		}
		
		@Test
		void listTag() throws IOException {
			DataInputStream dis = getData(
				0x08, 0x00, 0x00, 0x00,
				0x02, 0x00, 0x05, 'H' ,
				'e' , 'l' , 'l' , 'o' ,
				0x00, 0x07, 'G' , 'o' ,
				'o' , 'd' , 'b' , 'y' ,
				'e'
			);
			assertEquals(new NBTTagList(Arrays.asList(new NBTTagString("Hello"), new NBTTagString("Goodbye")), TagType.STRING), NBTTag.newTagByType(TagType.LIST, dis));
			dis.close();
		}
		
		@Test
		void compoundTag() throws IOException {
			DataInputStream dis = getData(
					0x03, 0x00, 0x07, 'v' ,
					'e' , 'r' , 's' , 'i' ,
					'o' , 'n' , 0x00, 0x00,
					0x00, 0x01, 0x08, 0x00,
					0x04, 'n' , 'a' , 'm' ,
					'e' , 0x00, 0x08, 'M' ,
					'r' , 'Y' , 'u' , 'r' ,
					'i' , 'h' , 'i' , 0x00
			);
			HashMap<String, NBTTag> data = new HashMap<>();
			data.put("version", new NBTTagInt(1));
			data.put("name", new NBTTagString("MrYurihi"));
			assertEquals(new NBTTagCompound(data), NBTTag.newTagByType(TagType.COMPOUND, dis));
			dis.close();
		}
	}

}
