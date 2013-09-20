package com.discretesoftworks.framework;

// A numerical matrix.
public class NumericalMatrix {

	private float[] array;
	private int cols;

	public NumericalMatrix(final int rows, final int cols) {
		// Set colums and create a new array
		this.cols = cols;
		this.array = new float[rows * cols];
	}
	
	public int getIndex(int row, int col){
		return (cols - 1 - row)*cols + col;
	}

	public void put(final float value, final int row, final int col)
			throws IndexOutOfBoundsException {
		// Add a new value using row-major
		array[(cols - 1 - row)*cols + col] = value;
	}

	public float get(int row, int col) throws IndexOutOfBoundsException {
		// Get a value using row-major
		return array[(cols - 1 - row)*cols + col];
	}
	
	public boolean isEvenBox(int r, int c){
		return (r + c)%2 == 0;
	}
	
	public boolean isOddBox(int r, int c){
		return (r + c)%2 != 0;
	}

	public NumericalMatrix add(final NumericalMatrix rhs) throws IllegalArgumentException {
		// If the dimensions are not correct, throw an exception
		if (rhs.getRowDimension() != getRowDimension() || rhs.getColumnDimension() != getColumnDimension())
			throw new IllegalArgumentException();

		// Add each element of each matrix together and put it in the output matrix
		NumericalMatrix output = new NumericalMatrix(getRowDimension(),getColumnDimension());
		for (int r = 0; r < getRowDimension(); r++){
			for (int c = 0; c < getColumnDimension(); c++){
				output.put(get(r,c) + rhs.get(r,c), r, c);
			}
		}
		return output;
	}

	public NumericalMatrix sub(final NumericalMatrix rhs) throws IllegalArgumentException {
		// If the dimensions are not correct, throw an exception
		if (rhs.getRowDimension() != getRowDimension() || rhs.getColumnDimension() != getColumnDimension())
			throw new IllegalArgumentException();
		
		// Subtract each element of each matrix together and put it in the output matrix
		NumericalMatrix output = new NumericalMatrix(getRowDimension(),getColumnDimension());
		for (int r = 0; r < getRowDimension(); r++){
			for (int c = 0; c < getColumnDimension(); c++){
				output.put(get(r,c) - rhs.get(r,c), r, c);
			}
		}
		return output;
	}

	public NumericalMatrix mult(final NumericalMatrix rhs) throws IllegalArgumentException {
		// If the dimensions are not correct, throw an exception
		if (getColumnDimension() != rhs.getRowDimension())
			throw new IllegalArgumentException();

		// Create an output matrix
		NumericalMatrix output = new NumericalMatrix(getRowDimension(),rhs.getColumnDimension());
		
		// For every output row,
		for (int r = 0; r < getRowDimension(); r++){
			// For every output column,
			for (int c = 0; c < rhs.getColumnDimension(); c++){
				float data = 0;
				// Grab all the correct data from the two multiplying matricies
				for (int i = 0; i < getColumnDimension(); i++){
					// Multiple and add to out putput
					data += get(r, i) * rhs.get(i, c);
				}
				// Place the data
				output.put(data, r, c);
			}
		}
		return output;
	}

	public int getRowDimension() {
		// Total length of array divided by the number of columns
		return array.length/cols;
	}

	public int getColumnDimension() {
		// Number of columns
		return cols;
	}
}
