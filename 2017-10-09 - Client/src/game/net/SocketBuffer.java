package game.net;

import java.nio.ByteBuffer;

public class SocketBuffer {
	
	byte[] data;
	
	int pointer = 0;
	
	public SocketBuffer() {
		allocate(1);
	}
	
	public SocketBuffer(int n) {
		allocate(n);
	}
	
	public void writeBytes(byte[] data) {
		int l = data.length;
		resize(pointer + l);
		for(int i = 0; i < l; i++) {
			this.data[pointer + i] = data[i];
		}
		pointer += l;
	}
	
	public void writeInt(int n) {
		
		writeBytes(new byte[] {
			(byte) (n & 0xFF),
			(byte) ((n >> 8) & 0xFF),
			(byte) ((n >> 16) & 0xFF),
			(byte) ((n >> 24) & 0xFF)
		});
	}
	
	public void writeFloat(float f) {
		writeBytes(ByteBuffer.allocate(4).putFloat(f).array());
	}
	
	public void writeString(String text) {
		writeBytes(text.getBytes());
	}
	
	public void resize(int newsize) {
		
		if(newsize > data.length) {
			
			byte[] newdata = new byte[newsize];
			
			for(int i = 0; i < data.length; i++) {
				newdata[i] = data[i];
			}
			
			data = new byte[newsize];
			
			for(int i = 0; i < data.length; i++) {
				data[i] = newdata[i];
			}
			
		}
	}
	
	public byte[] readBytes(int n) {
		n = Math.min(n, length() - pointer);
		byte[] outdata = new byte[n];
		for(int i = 0; i < n; i++)
			outdata[i] = data[pointer++];
		return outdata;
	}
	
	public String readString(int n) {
		return new String(readBytes(n));
	}
	
	public int readInt() {
		int n = 0;
		
		byte[] data = readBytes(4);
		
		for(int i = 0; i < 4; i++) {
			n |= (data[i] & 0xFF) << (i * 8);
		}
		
		return n;
	}
	
	public float readFloat() {
		byte data[] = readBytes(4);
		ByteBuffer bb = ByteBuffer.allocate(4).put(data);
		bb.flip();
		return bb.getFloat();
	}
	
	public void movePointer(int n) {
		pointer += n;
		pointer = Math.min(Math.max(0, pointer), length() - 1);
	}
	
	public void setPointer(int n) {
		pointer = n;
		pointer = Math.min(Math.max(0, pointer), length() - 1);
	}
	
	public void allocate(int n) {
		data = new byte[n];
	}
	
	public void printBytes() {
		for(int i = 0; i < data.length; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}
	
	public void printRange(int a, int b) {
		a = Math.max(0, a);
		b = Math.min(data.length, b);
		for(int i = a; i < b; i++) {
			System.out.print(data[i] + " ");
		}
		System.out.println();
	}
	
	public int length() {
		return data.length;
	}
	
	public void clear() {
		pointer = 0;
		data = new byte[1];
	}
	
}
