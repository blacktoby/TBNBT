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

import java.util.List;

import mryurihi.tbnbt.parser.TagType;

public abstract class NBTTag {
	protected String name = null;
	
	/**
	 * Sets the name of the tag. Not recommended to use unless you know what you are doing
	 * @param name the name to set the tag to
	 * @return the Tag
	 */
	public NBTTag setName(String name) {
		this.name = name;
		return this;
	}
	
	protected byte[] listToArray(List<Byte> list) {
		byte[] out = new byte[list.size()];
		for(int i = 0; i < list.size(); i++) out[i] = list.get(i);
		return out;
	}
	
	/**
	 * Gets the name of the tag
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the payload bytes for this object
	 * @return the bytes that make up the payload for this object
	 */
	abstract public List<Byte> getPayloadBytes();
	
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
}
