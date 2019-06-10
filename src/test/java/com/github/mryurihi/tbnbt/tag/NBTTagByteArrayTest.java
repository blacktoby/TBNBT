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
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NBTTagByteArrayTest {
	
	@Nested
	class testWritePayloadBytes {
		@Test
		void shouldWriteCorrectData() throws IOException {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			NBTTagByteArray tag = new NBTTagByteArray(new byte[] {
				1, 3, 8, 3, 17, 12
			});
			tag.writePayloadBytes(new DataOutputStream(bout));
			assertArrayEquals(bout.toByteArray(), new byte[] {
				0x00, 0x00, 0x00, 0x06, 1, 3, 8, 3, 17, 12
			});
		}
	}
	
	@Nested
	class testReadPayloadBytes {
		@Test
		void shouldReadCorrectData() throws IOException {
			ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
				0x00, 0x00, 0x00, 0x04, 2, 3, 5, 7
			});
			NBTTagByteArray tag = new NBTTagByteArray(null);
			tag.readPayloadBytes(new DataInputStream(bin));
			assertEquals(tag, new NBTTagByteArray(new byte[] {
				2, 3, 5, 7
			}));
		}
		
		@Test
		void shouldReadCorrectDataWithExtra() throws IOException {
			ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
				0x00, 0x00, 0x00, 0x04, 2, 3, 5, 7, 11, 13, 17
			});
			NBTTagByteArray tag = new NBTTagByteArray(null);
			tag.readPayloadBytes(new DataInputStream(bin));
			assertEquals(tag, new NBTTagByteArray(new byte[] {
				2, 3, 5, 7
			}));
		}
		
		@Test
		void shouldThrowEofException() throws IOException {
			ByteArrayInputStream bin = new ByteArrayInputStream(new byte[] {
				0x00, 0x00, 0x00, 0x04, 2, 3, 5
			});
			NBTTagByteArray tag = new NBTTagByteArray(null);
			assertThrows(IOException.class, () -> tag.readPayloadBytes(new DataInputStream(bin)));
		}
	}
	
	@Nested
	class testEqualsTag {
		@Test
		void shouldBeTrueWhenEqual() {
			NBTTagByteArray tag = new NBTTagByteArray(new byte[] {
				2, 3, 5, 7, 11
			});
			NBTTagByteArray tag2 = new NBTTagByteArray(new byte[] {
				2, 3, 5, 7, 11
			});
			assertTrue(tag.equalsTag(tag2));
		}
		
		@Test
		void shouldBeTrueWhenBothValuesNull() {
			NBTTagByteArray tag = new NBTTagByteArray(null);
			NBTTagByteArray tag2 = new NBTTagByteArray(null);
			assertTrue(tag.equalsTag(tag2));
		}
		
		@Test
		void shouldBeFalseWhenDifferentTag() {
			NBTTagByteArray tag = new NBTTagByteArray(new byte[] {
				1, 2, 3, 4, 5, 6, 7, 8, 9
			});
			NBTTagByte tag2 = new NBTTagByte((byte) 4);
			assertFalse(tag.equalsTag(tag2));
		}
	}
	
	@Nested
	class testToString {
		@Test
		void shouldReturnCorrect() {
			NBTTagByteArray tag = new NBTTagByteArray(new byte[] {
				1, 2, 3, 4
			});
			assertEquals("[B;1, 2, 3, 4]", tag.toString());
		}
	}
	
}
