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
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import com.google.common.reflect.TypeToken;

import mryurihi.tbnbt.TagType;
import mryurihi.tbnbt.adapter.AdapterRegistry;
import mryurihi.tbnbt.adapter.NBTAdapter;
import mryurihi.tbnbt.adapter.NBTAdapterFactory;
import mryurihi.tbnbt.adapter.NBTParseException;

public class MapAdapterFactory implements NBTAdapterFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> NBTAdapter<T> create(AdapterRegistry registry, TypeToken<T> type) {
		Class<?> itemClass = (Class<?>) ((ParameterizedType) type.getType()).getActualTypeArguments()[1];
		return (NBTAdapter<T>) new Adapter(registry.getAdapterForObject(TypeToken.of(itemClass)), itemClass);
	}
	
	private static class Adapter<E> extends NBTAdapter<Map<String, E>> {
		
		private NBTAdapter<E> itemAdapter;
		private Class<E> itemClass;
		
		public Adapter(NBTAdapter<E> itemAdapter, Class<E> itemClass) {
			this.itemAdapter = itemAdapter;
			this.itemClass = itemClass;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Map<String, E> fromNBT(TagType id, DataInputStream payload, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
			try {
				Map<String, E> out = (Map<String, E>) type.getRawType().newInstance();
				byte nextTagType = payload.readByte();
				do {
					String tagName = registry.fromString(payload);
					out.put(tagName, itemAdapter.fromNBT(TagType.getTypeById(nextTagType), payload, TypeToken.of(itemClass), registry));
					nextTagType = payload.readByte();
				} while(nextTagType != 0);
			} catch (Exception e) {
				throw new NBTParseException(e);
			} 
			return null;
		}

		@Override
		public void toNBT(DataOutputStream out, Object object, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
			@SuppressWarnings("unchecked")
			Map<String, E> mapObj = (Map<String, E>) object;
			for(Map.Entry<String, E> entry: mapObj.entrySet()) {
				registry.writeByte(out, (byte) itemAdapter.getId().getId());
				registry.writeString(out, entry.getKey());
				itemAdapter.toNBT(out, entry.getValue(), TypeToken.of(itemClass), registry);
			}
			registry.writeByte(out, (byte) 0);
		}

		@Override
		public TagType getId() {
			return TagType.COMPOUND;
		}
		
	}
}
