package model;

public class ItemMenu {
	public static final int CLIENTES = 0;
	public static final int ARTICULOS = 1;
	public static final int LOGOUT = 2;
	
	private int op;
	private String name;
	public ItemMenu(int op, String name) {
		super();
		this.op = op;
		this.name = name;
	}
	public int getOp() {
		return op;
	}
	public void setOp(int op) {
		this.op = op;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
