package com.discretesoftworks.framework;

import java.util.ArrayList;

public class RaggedDictionary<T,E>{
	
	private Pair<T,ArrayList<E>>[] data;
	private int amount;
	private ArrayList<E> compiledData;

	
	@SuppressWarnings("unchecked")
	public RaggedDictionary(int size){
		data = new Pair[size];
		compiledData = new ArrayList<E>();
		amount = 0;
	}
	
	@SuppressWarnings("unchecked")
	public void clear(){
		data = new Pair[data.length];
		compiledData = new ArrayList<E>();
		amount = 0;
	}
	
	public void add(T key, E value){
		int k = indexOf(key);
		if (k < 0){
			k = amount;
			amount += 1;
			data[k] = new Pair<T, ArrayList<E>>(key, new ArrayList<E>());
		}
		data[k].b.add(value);
		compileData();
	}
	
	public void move(E value, T key){
		removeAll(value);
		add(key,value);
	}
	
	public boolean removeAll(E value){
		boolean found = false;
		for (int i = 0; i < amount; i++){
			boolean f = data[i].b.remove(value);
			if (f)
				found = true;
		}
		compiledData.remove(value);
		return found;
	}
	
	public int indexOf(T key){
		for (int i = 0; i < amount; i++)
			if (data[i].a == key)
				return i;
		return -1;
	}
	
	public Pair<T,ArrayList<E>> find(T key){
		for (int i = 0; i < amount; i++)
			if (data[i].a == key)
				return data[i];
		return null;
	}
	
	public void compileData(){
		compiledData.clear();
		for (int i = 0; i < amount; i++){
			for (E e : data[i].b)
				compiledData.add(e);
		}
		System.out.println("Here is the data: ");
		for (E e : compiledData)
			System.out.println(e);
	}
	
	public ArrayList<E> getCompiledData(){
		return compiledData;
	}
	
}
