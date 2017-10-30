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
package mryurihi.tbnbt.adapter.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.google.common.reflect.TypeToken;

import mryurihi.tbnbt.adapter.AdapterRegistry;
import mryurihi.tbnbt.adapter.NBTAdapter;
import mryurihi.tbnbt.adapter.NBTAdapterFactory;
import mryurihi.tbnbt.adapter.NBTParseException;
import mryurihi.tbnbt.parser.TagType;

public class ArrayAdapterFactory implements NBTAdapterFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> NBTAdapter<T> create(AdapterRegistry registry, TypeToken<T> type) {
		Class<?> compType = (Class<?>) type.getRawType().getComponentType();
		return (NBTAdapter<T>) new Adapter(registry.getAdapterForObject((TypeToken<?>) TypeToken.of(compType)), compType);
	}
	
	private class Adapter<T> extends NBTAdapter<T[]> {
		
		private NBTAdapter<T> contentAdapter;
		private Class<T> contentClass;
		
		public Adapter(NBTAdapter<T> contentAdapter, Class<T> contentClass) {
			this.contentAdapter = contentAdapter;
			this.contentClass = contentClass;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T[] fromNBT(TagType id, DataInputStream payload, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
			try {
				TagType contentId = TagType.getTypeById(payload.readByte());
				Object[] out = new Object[registry.fromInt(payload)];
				for(int i = 0; i < out.length; i++) {
					out[i] = contentAdapter.fromNBT(contentId, payload, TypeToken.of(contentClass), registry);
				}
				return (T[]) out;
			} catch (Exception e) {
				throw new NBTParseException(e);
			}
		}

		@Override
		public void toNBT(DataOutputStream out, Object object, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
			try {
				Object[] output = (Object[]) object;
				registry.writeByte(out, (byte) contentAdapter.getId().getId());
				registry.writeInt(out, output.length);
				for(Object o: output) {
					contentAdapter.toNBT(out, o, TypeToken.of(contentClass), registry);
				}
			} catch (Exception e) {
				throw new NBTParseException(e);
			}
		}

		@Override
		public TagType getId() {
			return TagType.LIST;
		}
		
	}
}