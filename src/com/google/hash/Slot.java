package com.google.hash;

public class Slot {
	int R;
	int S;
	boolean available;
	boolean used;
	
	Slot(int r, int s) {
		this.R = r;
		this.S = s;
		this.used = false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Slot) {
			return (((Slot) obj).R == this.R && ((Slot) obj).S == this.S);
		}
		return false;
	}
}