package com.discretesoftworks.framework;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;

public class ModelLoader {
	public static String convertStreamToString(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}
	public static RenderModel loadOBJ(RenderModel object, String filename, AssetManager assets) throws IOException {
		String file = convertStreamToString(assets.open(filename));
		System.out.println(file.substring(0,10)); //Prove that we opened it by printing out the first characters (# Blender)
		return object;
	}

}
