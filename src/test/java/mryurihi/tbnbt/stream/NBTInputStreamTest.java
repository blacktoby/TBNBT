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
package mryurihi.tbnbt.stream;

import static org.junit.jupiter.api.Assertions.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.google.common.reflect.TypeToken;

import mryurihi.tbnbt.TagType;
import mryurihi.tbnbt.adapter.AdapterRegistry;
import mryurihi.tbnbt.adapter.NBTAdapter;
import mryurihi.tbnbt.adapter.NBTParseException;
import mryurihi.tbnbt.stream.NBTInputStreamTest.TestDataHolder2.CustomData;
import mryurihi.tbnbt.tag.NBTTagString;

class NBTInputStreamTest {
	
	NBTInputStream in;
	
	@AfterEach
	void afterEach() throws IOException {
		in.close();
	}
	
	@Test
	void testReadTagBoolean() throws IOException {
		in = new NBTInputStream(getClass().getClassLoader().getResourceAsStream("data/nbt/namedstring.nbt"), false);
		assertEquals(new NBTTagString("MrYurihi"), in.readTag());
	}
	
	@Test
	void testReadTag() throws IOException {
		in = new NBTInputStream(getClass().getClassLoader().getResourceAsStream("data/nbt/namedstring.nbt"), false);
		assertEquals(new NBTTagString("MrYurihi"), in.readTag());
	}
	
	@Test
	void testReadToType() throws IOException, NBTParseException {
		in = new NBTInputStream(getClass().getClassLoader().getResourceAsStream("data/nbt/serializetest1.nbt"), false);
		TestDataHolder expt = new TestDataHolder();
		expt.int1 = 0x00000F00;
		expt.string1 = "yay";
		assertEquals(expt, in.readToType(TypeToken.of(TestDataHolder.class)));
	}
	
	@Test
	void testClose() throws IOException {
		ProxyInputStream proxy = new ProxyInputStream();
		
		in = new NBTInputStream(proxy, false);
		in.close();
		
		assertTrue(proxy.closed);
	}
	
	static class ProxyInputStream extends InputStream {
		public boolean closed = false;
		
		@Override
		public int read() throws IOException {
			return 0;
		}
		
		@Override
		public void close() throws IOException {
			closed = true;
		}
	}
	
	static class TestDataHolder {
		String string1;
		int int1;
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TestDataHolder) {
				TestDataHolder obj2 = (TestDataHolder) obj;
				return string1.equals(obj2.string1) && int1 == obj2.int1;
			}
			return false;
		}
	}
	
	@Test
	void testReadToTypeWithAdapterRegistry() throws IOException, NBTParseException {
		AdapterRegistry reg = new AdapterRegistry.Builder()
			.addAdapter(CustomData.class, new TestDataHolder2.CustomDataAdapter())
			.create();
		
		TestDataHolder2 data = new TestDataHolder2();
		data.data1 = new CustomData(0x05, 0x21);
		data.data2 = new CustomData(0x01, 0x20);
		
		in = new NBTInputStream(getClass().getClassLoader().getResourceAsStream("data/nbt/customdata.nbt"), false);
		assertEquals(data, in.readToType(TypeToken.of(TestDataHolder2.class), reg));
	}
	
	static class TestDataHolder2 {
		
		CustomData data1;
		CustomData data2;
		
		static class CustomData {
			byte x, y;
			
			public CustomData(int x, int y) {
				this.x = (byte) x;
				this.y = (byte) y;
			}
			
			@Override
			public boolean equals(Object obj) {
				if(obj instanceof CustomData) {
					CustomData arg1 = (CustomData) obj;
					return arg1.x == x && arg1.y == y;
				}
				return false;
			}
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof TestDataHolder2) {
				TestDataHolder2 arg1 = (TestDataHolder2) obj;
				return arg1.data1.equals(data1) && arg1.data2.equals(data2);
			}
			return false;
		}
		
		static class CustomDataAdapter extends NBTAdapter<CustomData> {
			
			@Override
			public CustomData fromNBT(TagType id, DataInputStream payload, TypeToken<?> type, AdapterRegistry registry)
					throws NBTParseException {
				CustomData out = new CustomData(0, 0);
				Byte[] data = registry.fromByteArray(payload);
				out.x = data[0];
				out.y = data[1];
				return out;
			}
			
			@Override
			public void toNBT(DataOutputStream out, Object object, TypeToken<?> type, AdapterRegistry registry)
					throws NBTParseException {
				CustomData data = (CustomData) object;
				registry.writeByteArray(out, new Byte[] {
						data.x, data.y
				});
			}
			
			@Override
			public TagType getId() {
				return TagType.BYTE_ARRAY;
			}
			
		}
	}
}
