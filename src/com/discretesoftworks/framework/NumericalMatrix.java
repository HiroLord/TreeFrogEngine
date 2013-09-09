package com.discretesoftworks.framework;

// A numerical matrix.
public class NumericalMatrix {

	private double[] array;
	private int cols;

	public NumericalMatrix(final int rows, final int cols) {
		// Set colums and create a new array
		this.cols = cols;
		this.array = new double[rows * cols];
	}

	public void put(final double value, final int row, final int col)
			throws IndexOutOfBoundsException {
		// Add a new value using row-major
		array[row*cols + col] = value;
	}

	public double get(int row, int col) throws IndexOutOfBoundsException {
		// Get a value using row-major
		return array[row*cols + col];
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
				double data = 0;
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
