package com.google.hash;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DataCenter {
	static int R, S, U, P, M;
	// R-rows, S-slotsPerRow, U-UnavailableSlots, P-Pools, M-Servers
	static Slot[] unavailableSlots;
	static ArrayList<Server> servers;
	public static void main(String[] args) {
		try {
			File file = new File("dc.in");

			Scanner input = new Scanner(file);
			R = input.nextInt();
			S = input.nextInt();
			U = input.nextInt();
			P = input.nextInt();
			M = input.nextInt();
			input.nextLine();

			unavailableSlots = new Slot[U];
			for (int i = 0; i < U; i++) {
				String[] data = input.nextLine().split(" ");
				unavailableSlots[i] = new Slot(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
			}
			servers = new ArrayList<Server>(M);
			for (int i = 0; i < M; i++) {
				String[] data = input.nextLine().split(" ");
				servers.add(new Server(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
			}
			input.close();
			Collections.sort(servers);

			Slot[][] matrix = new Slot[R][S];
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < S; j++) {
					matrix[i][j] = new Slot(i, j);
					matrix[i][j].available = isAvailable(matrix[i][j]);
				}
			}
			int countServers = 0;

			printMatrix(fillMatrix(matrix.clone(), 0));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// El siguiente algoritmo no funciona. No le des más vueltas
	public static Slot[][] fillMatrix(Slot[][] matrix, int server) {
		if (completed(matrix))
			return matrix;
		if(servers.size()<=server)
			return null;
		System.out.println(server);
		Server next = servers.get(server);
		for (int i = 0; i < R; i++) {
			List<Integer> places = fits(matrix, next, i);
			int place = -1;
			if(places.size()>0) 
				place = places.get(0);
			else 
				continue;
			//for (Integer place : places) {
			matrix = placeServer(matrix, next, i, place);
			if(completed(matrix))
				return matrix;
			Slot[][] aux = fillMatrix(matrix.clone(), server+1);
			if (aux != null){
				return aux;
			}
			matrix = removeServer(matrix, next, i, place);
			//}
		}
		return matrix;
	}

	public static boolean isRowFilled(Slot[][] matrix, int row) {
		for (int i = 0; i < S; i++) {
			if (!matrix[row][i].used)
				return false;
		}
		return true;
	}

	public static boolean completed(Slot[][] matrix) {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < S; j++) {
				if (matrix[i][j].available && !matrix[i][j].used)
					return false;
			}
		}
		return true;
	}

	public static void printMatrix(Slot[][] matrix) {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < S; j++)
				System.out.print(matrix[i][j]);
			System.out.println();
		}
	}

	public static Slot[][] placeServer(Slot[][] matrix, Server server, int initR, int initS) {
		for (int i = 0; i < server.size; i++) {
			matrix[initR][initS + i].used = true;
			server.slots.add(matrix[initR][initS + i]);
		}
		return matrix;
	}

	public static Slot[][] removeServer(Slot[][] matrix, Server server, int initR, int initS) {
		for (int i = 0; i < server.size; i++) {
			matrix[initR][initS + i].used = false;
			server.slots.remove(matrix[initR][initS + i]);
		}
		return matrix;
	}

	public static List<Integer> fits(Slot[][] matrix, Server server, int row) {
		int count = 0;
		int initSlot = -1;
		List<Integer> fits = new ArrayList<Integer>();
		for (int i = 0; i < S; i++) {
			if (matrix[row][i].available && !matrix[row][i].used) {
				count++;
				if (initSlot == -1)
					initSlot = i;
			} else {
				if (server.size <= count) {
					fits.add(initSlot);
				}
				initSlot = -1;
				count = 0;
			}
		}
		return fits;
	}

	public static boolean isAvailable(Slot s) {
		for (int i = 0; i < U; i++) {
			if (unavailableSlots[i].equals(s))
				return false;
		}
		return true;
	}

}
