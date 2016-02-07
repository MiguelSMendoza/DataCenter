package com.google.hash;

import java.util.ArrayList;
import java.util.List;

public class Server implements Comparable<Server> {
	int size;
	int capacity;
	int pool;
	List<Slot> slots;

	Server(int z, int c) {
		this.size = z;
		this.capacity = c;
		slots = new ArrayList<Slot>(size);
	}

	double getValue() {
		return (double) size / capacity;
	}

	@Override
	public int compareTo(Server o) {
		if (this.getValue() > o.getValue() || (this.getValue() == o.getValue() && this.capacity > o.capacity))
			return -1;
		if (this.getValue() < o.getValue() || (this.getValue() == o.getValue() && this.capacity < o.capacity))
			return 1;
		return 0;
	}

	@Override
	public String toString() {
		return "Server: " + size + " " + capacity;
	}
}
