package io.github.visualista.visualista.util;

public class Row<E>{
	private final E[] row;
	@SuppressWarnings("unchecked")
	public Row(int size){
		row = (E[])new Object[size];
	}
	private Row(E[] row){
		this.row = row;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Object clone(){
		return new Row(row.clone());
	}

	public int getLength(){
		return row.length;
	}
	
	public void setAt(int i, E object){
		row[i] = object;
	}
	
	public E getAt(int i){
		return row[i];
	}
}
