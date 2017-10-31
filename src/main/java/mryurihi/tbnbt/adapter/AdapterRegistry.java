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
package mryurihi.tbnbt.adapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.reflect.TypeToken;

import mryurihi.tbnbt.adapter.impl.ArrayAdapterFactory;
import mryurihi.tbnbt.adapter.impl.ByteArrayAdapter;
import mryurihi.tbnbt.adapter.impl.CollectionAdapterFactory;
import mryurihi.tbnbt.adapter.impl.IntegerArrayAdapter;
import mryurihi.tbnbt.adapter.impl.MapAdapterFactory;
import mryurihi.tbnbt.adapter.impl.ObjectAdapter;
import mryurihi.tbnbt.adapter.impl.StringAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.BooleanAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.ByteAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.DoubleAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.FloatAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.IntegerAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.LongAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.PrimitiveByteArrayAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.PrimitiveIntArrayAdapter;
import mryurihi.tbnbt.adapter.impl.primitive.ShortAdapter;
import mryurihi.tbnbt.parser.TagType;

public class AdapterRegistry {
	
	@SuppressWarnings("unchecked")
	public AdapterRegistry() {
		registry = new HashMap<>();
		factory = new HashMap<>();
		Class<?>[] rClass = new Class<?>[] {
			Byte.class,
			Short.class,
			Integer.class,
			Long.class,
			Float.class,
			Double.class,
			Byte[].class,
			byte[].class,
			String.class,
			Object.class,
			Integer[].class,
			int[].class
		};
		Class<? extends NBTAdapter<?>>[] rAdapter = (Class<? extends NBTAdapter<?>>[]) new Class<?>[] {
			ByteAdapter.class, 
			ShortAdapter.class,
			IntegerAdapter.class,
			LongAdapter.class,
			FloatAdapter.class,
			DoubleAdapter.class,
			ByteArrayAdapter.class,
			PrimitiveByteArrayAdapter.class,
			StringAdapter.class,
			ObjectAdapter.class,
			IntegerArrayAdapter.class,
			PrimitiveIntArrayAdapter.class
		};
		for(int i = 0; i < rClass.length; i++) registry.put(rClass[i], rAdapter[i]);
		factory.put(Collection.class, new CollectionAdapterFactory());
		factory.put(Object[].class, new ArrayAdapterFactory());
		factory.put(Map.class, new MapAdapterFactory());
	}
	
	private Map<Class<?>, Class<? extends NBTAdapter<?>>> registry;
	private Map<Class<?>, NBTAdapterFactory> factory;
	
	public void registerAdapter(Class<?> type, Class<? extends NBTAdapter<?>> adapter) {
		registry.put(type, adapter);
	}
	
	public NBTAdapter<?> getAdapterForObject(TypeToken<?> objectTypeToken) {
		return getAdapterForObject(objectTypeToken, objectTypeToken);
	}
	
	private NBTAdapter<?> getAdapterForObject(TypeToken<?> objectTypeToken, TypeToken<?> topTypeToken) {
		Class<?> objectType = objectTypeToken.getRawType();
		if(objectType.isPrimitive()) {
			if(objectType.equals(byte.class)) return new ByteAdapter();
			if(objectType.equals(short.class)) return new ShortAdapter();
			if(objectType.equals(int.class)) return new IntegerAdapter();
			if(objectType.equals(long.class)) return new LongAdapter();
			if(objectType.equals(float.class)) return new FloatAdapter();
			if(objectType.equals(double.class)) return new DoubleAdapter();
			if(objectType.equals(boolean.class)) return new BooleanAdapter();
		}
		if(registry.containsKey(objectType)) try {
			return registry.get(objectType).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} else if(factory.containsKey(objectType)) return factory.get(objectType).create(this, topTypeToken);
		else if(objectType.getInterfaces().length != 0) {
			for(Type t: objectType.getGenericInterfaces()) {
				NBTAdapter<?> adapter = getAdapterForObject(TypeToken.of(t), topTypeToken);
				if(adapter != null) return adapter;
			}
		}
		Class<?> superClass = objectType.getSuperclass();
		if(superClass == null) return null;
		NBTAdapter<?> out = getAdapterForObject(TypeToken.of(superClass), topTypeToken);
		return out;
	}
	
	public NBTAdapter<?> getByteAdapter() {
		try {
			return registry.get(Byte.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Byte fromByte(DataInputStream payload) {
		try {
			return (Byte) registry.get(Byte.class).newInstance().fromNBT(TagType.BYTE, payload, new TypeToken<Byte>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeByte(DataOutputStream out, byte object) {
		try {
			registry.get(Byte.class).newInstance().toNBT(out, object, new TypeToken<Byte>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getShortAdapter() {
		try {
			return registry.get(Short.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Short fromShort(DataInputStream payload) {
		try {
			return (Short) registry.get(Short.class).newInstance().fromNBT(TagType.SHORT, payload, new TypeToken<Short>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeShort(DataOutputStream out, short object) {
		try {
			registry.get(Short.class).newInstance().toNBT(out, object, new TypeToken<Short>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getIntAdapter() {
		try {
			return registry.get(Integer.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer fromInt(DataInputStream payload) {
		try {
			return (Integer) registry.get(Integer.class).newInstance().fromNBT(TagType.INT, payload, new TypeToken<Integer>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeInt(DataOutputStream out, int object) {
		try {
			registry.get(Integer.class).newInstance().toNBT(out, object, new TypeToken<Integer>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getLongAdapter() {
		try {
			return registry.get(Long.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Long fromLong(DataInputStream payload) {
		try {
			return (Long) registry.get(Long.class).newInstance().fromNBT(TagType.LONG, payload, new TypeToken<Long>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeLong(DataOutputStream out, long object) {
		try {
			registry.get(Long.class).newInstance().toNBT(out, object, new TypeToken<Long>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getFloatAdapter() {
		try {
			return registry.get(Float.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Float fromFloat(DataInputStream payload) {
		try {
			return (Float) registry.get(Float.class).newInstance().fromNBT(TagType.FLOAT, payload, new TypeToken<Float>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeFloat(DataOutputStream out, float object) {
		try {
			registry.get(Float.class).newInstance().toNBT(out, object, new TypeToken<Float>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getDoubleAdapter() {
		try {
			return registry.get(Double.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Double fromDouble(DataInputStream payload) {
		try {
			return (Double) registry.get(Double.class).newInstance().fromNBT(TagType.DOUBLE, payload, new TypeToken<Double>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeDouble(DataOutputStream out, double object) {
		try {
			registry.get(Double.class).newInstance().toNBT(out, object, new TypeToken<Double>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getByteArrayAdapter() {
		try {
			return registry.get(Byte[].class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Byte[] fromByteArray(DataInputStream payload) {
		try {
			return (Byte[]) registry.get(Byte[].class).newInstance().fromNBT(TagType.BYTE_ARRAY, payload, new TypeToken<Byte[]>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeByteArray(DataOutputStream out, Byte[] object) {
		try {
			registry.get(Byte[].class).newInstance().toNBT(out, object, new TypeToken<Byte[]>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getStringAdapter() {
		try {
			return registry.get(String.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String fromString(DataInputStream payload) {
		try {
			return (String) registry.get(String.class).newInstance().fromNBT(TagType.STRING, payload, new TypeToken<String>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeString(DataOutputStream out, String object) {
		try {
			registry.get(String.class).newInstance().toNBT(out, object, new TypeToken<String>() {}, this);
		} catch (Exception e) {
			
		}
	}
	
	public NBTAdapter<?> getIntArrayAdapter() {
		try {
			return registry.get(Byte.class).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer[] fromIntArray(DataInputStream payload) {
		try {
			return (Integer[]) registry.get(Integer[].class).newInstance().fromNBT(TagType.INT_ARRAY, payload, new TypeToken<Integer[]>() {}, this);
		} catch (Exception e) {
			return null;
		}
	}
	
	public void writeIntArray(DataOutputStream out, Integer[] object) {
		try {
			registry.get(Integer[].class).newInstance().toNBT(out, object, new TypeToken<Integer[]>() {}, this);
		} catch (Exception e) {
			
		}
	}
}
