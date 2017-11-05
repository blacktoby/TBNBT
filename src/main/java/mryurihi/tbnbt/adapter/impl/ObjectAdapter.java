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
import java.lang.reflect.Field;

import com.google.common.reflect.TypeToken;

import mryurihi.tbnbt.adapter.AdapterRegistry;
import mryurihi.tbnbt.adapter.NBTAdapter;
import mryurihi.tbnbt.adapter.NBTParseException;
import mryurihi.tbnbt.annotations.SerializedName;
import mryurihi.tbnbt.parser.TagType;

public class ObjectAdapter extends NBTAdapter<Object> {

	@Override
	public Object fromNBT(TagType id, DataInputStream payload, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
		if(! id.equals(TagType.COMPOUND)) throw new NBTParseException(String.format("id %s does not match required id 10", id.getId()));
		Object out;
		try {
			out = type.getRawType().newInstance();
			byte nextTagType = payload.readByte();
			fieldSearch: do {
				String tagName = registry.fromString(payload);
				Field objField = null;
				try {
					System.out.println(tagName);
					objField = type.getRawType().getDeclaredField(tagName);
				} catch(NoSuchFieldException e) {
					for(Field f: type.getRawType().getDeclaredFields()) {
						if(f.isAnnotationPresent(SerializedName.class) && f.getAnnotation(SerializedName.class).value().equals(tagName)) {
							objField = f;
						}
					}
					if(objField == null) continue fieldSearch;
				}
				objField.setAccessible(true);
				
				NBTAdapter<?> adapter = registry.getAdapterForObject(TypeToken.of(objField.getGenericType()));
				objField.set(out, adapter.fromNBT(TagType.getTypeById(nextTagType), payload, TypeToken.of(objField.getGenericType()), registry));
				nextTagType = payload.readByte();
			} while(nextTagType != 0);
		} catch (Exception e) {
			throw new NBTParseException(e);
		}
		return out;
	}
	
	@Override
	public void toNBT(DataOutputStream out, Object object, TypeToken<?> type, AdapterRegistry registry) throws NBTParseException {
		try {
			for(Field f: type.getRawType().getDeclaredFields()) {
				f.setAccessible(true);
				NBTAdapter<?> adapter = registry.getAdapterForObject(TypeToken.of(f.getGenericType()));
				out.writeByte(adapter.getId().getId());
				registry.writeString(out, f.isAnnotationPresent(SerializedName.class)? f.getAnnotation(SerializedName.class).value(): f.getName());
				adapter.toNBT(out, f.get(object), TypeToken.of(f.getGenericType()), registry);
			}
			out.writeByte(0);
		} catch (Exception e) {
			throw new NBTParseException(e);
		}
	}

	@Override
	public TagType getId() {
		return TagType.COMPOUND;
	}

}
