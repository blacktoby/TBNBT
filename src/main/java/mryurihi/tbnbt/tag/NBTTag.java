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
package mryurihi.tbnbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mryurihi.tbnbt.TagType;

public abstract class NBTTag {
	
	/**
	 * writes the payload bytes for this object
	 * @param out the stream to write to
	 */
	abstract public void writePayloadBytes(DataOutputStream out) throws IOException;
	
	/**
	 * reads the payload bytes for this object
	 * @param in the stream to read to
	 */
	abstract public NBTTag readPayloadBytes(DataInputStream in) throws IOException;
	
	/**
	 * Gets the type of the tag
	 * @return the tag type
	 */
	abstract public TagType getTagType();
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagByte}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagByte}
	 */
	public NBTTagByte getAsTagByte() {
		return NBTTagByte.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagShort}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagShort}
	 */
	public NBTTagShort getAsTagShort() {
		return NBTTagShort.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagInt}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagInt}
	 */
	public NBTTagInt getAsTagInt() {
		return NBTTagInt.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagLong}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagLong}
	 */
	public NBTTagLong getAsTagLong() {
		return NBTTagLong.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagFloat}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagFloat}
	 */
	public NBTTagFloat getAsTagFloat() {
		return NBTTagFloat.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagDouble}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagDouble}
	 */
	public NBTTagDouble getAsTagDouble() {
		return NBTTagDouble.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagByteArray}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagByteArray}
	 */
	public NBTTagByteArray getAsTagByteArray() {
		return NBTTagByteArray.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagString}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagString}
	 */
	public NBTTagString getAsTagString() {
		return NBTTagString.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagList}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagList}
	 */
	public NBTTagList getAsTagList() {
		return NBTTagList.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagCompound}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagCompound}
	 */
	public NBTTagCompound getAsTagCompound() {
		return NBTTagCompound.class.cast(this);
	}
	
	/**
	 * Gets the tag as an {@link mryurihi.tbnbt.tag.NBTTagIntArray}
	 * @return the tag as {@link mryurihi.tbnbt.tag.NBTTagIntArray}
	 */
	public NBTTagIntArray getAsTagIntArray() {
		return NBTTagIntArray.class.cast(this);
	}
	
	public static NBTTag newTagByType(TagType type, DataInputStream in) throws IOException {
		switch(type) {
		case BYTE: return new NBTTagByte().readPayloadBytes(in);
		case SHORT: return new NBTTagShort().readPayloadBytes(in);
		case INT: return new NBTTagInt().readPayloadBytes(in);
		case LONG: return new NBTTagLong().readPayloadBytes(in);
		case FLOAT: return new NBTTagFloat().readPayloadBytes(in);
		case DOUBLE: return new NBTTagDouble().readPayloadBytes(in);
		case BYTE_ARRAY: return new NBTTagByteArray().readPayloadBytes(in);
		case STRING: return new NBTTagString().readPayloadBytes(in);
		case LIST: return new NBTTagList().readPayloadBytes(in);
		case COMPOUND: return new NBTTagCompound().readPayloadBytes(in);
		case INT_ARRAY: return new NBTTagIntArray().readPayloadBytes(in);
		case LONG_ARRAY: return new NBTTagLongArray().readPayloadBytes(in);
		default: return null;
		}
	}
}
