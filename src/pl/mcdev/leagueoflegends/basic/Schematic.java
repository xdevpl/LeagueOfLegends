package pl.mcdev.leagueoflegends.basic;

public class Schematic {
	
	private byte[] blocks;
    private byte[] data;
    private short width;
    private short length;
    private short height;
 
    public Schematic(byte[] blocks, byte[] data, short width, short length, short height) {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
    }
 
    public byte[] getBlocks() {
        return this.blocks;
    }
 
    public byte[] getData() {
        return this.data;
    }
 
    public short getWidth() {
        return this.width;
    }
 
    public short getLength() {
        return this.length;
    }
 
    public short getHeight() {
        return this.height;
    }
}
