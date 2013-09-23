package com.discretesoftworks.framework;

import java.util.ArrayList;

// A Dictionary where all values are arrays.
public class RaggedDictionary<T extends Comparable<T>,E>{
	
	private ArrayList<Pair<T,ArrayList<E>>> data;
	private ArrayList<E> compiledData;
	
	public static final byte LEFT_TO_RIGHT = 1;
	public static final byte RIGHT_TO_LEFT = 2;
	
	private int sorting;

	
	public RaggedDictionary(byte sorting){
		data = new ArrayList<Pair<T,ArrayList<E>>>();
		compiledData = new ArrayList<E>();
		this.sorting = sorting;
	}
	
	public void clear(){
		data = new ArrayList<Pair<T,ArrayList<E>>>();
		compiledData = new ArrayList<E>();
	}
	
	public void add(T key, E value){
		int k = indexOf(key);
		if (k < 0){
			int i = 0;
			for (i = 0; i < data.size(); i++){
				if (sorting == LEFT_TO_RIGHT)
					if (key.compareTo(data.get(i).a) < 0){
						break;
					}
				else
					if (key.compareTo(data.get(i).a) > 0){
						i++;
						break;
					}
			}
			data.add(i, new Pair<T, ArrayList<E>>(key, new ArrayList<E>()));
			k = i;
		}
		data.get(k).b.add(0,value);
		compileData();
	}
	
	public void move(E value, T key){
		removeAll(value);
		add(key,value);
	}
	
	public boolean removeAll(E value){
		boolean found = false;
		for (int i = 0; i < data.size(); i++){
			boolean f = data.get(i).b.remove(value);
			if (f)
				found = true;
		}
		compiledData.remove(value);
		return found;
	}
	
	public int indexOf(T key){
		for (int i = 0; i < data.size(); i++)
			if (data.get(i).a == key)
				return i;
		return -1;
	}
	
	public Pair<T,ArrayList<E>> find(T key){
		for (int i = 0; i < data.size(); i++)
			if (data.get(i).a == key)
				return data.get(i);
		return null;
	}
	
	public void compileData(){
		compiledData.clear();
		for (int i = 0; i < data.size(); i++){
			for (E e : data.get(i).b)
				compiledData.add(e);
		}
	}
	
	public ArrayList<E> getCompiledData(){
		return compiledData;
	}
	
}
