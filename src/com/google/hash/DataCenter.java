package com.google.hash;

import java.io.File;
import java.util.Scanner;

public class DataCenter {
	static int N, M;
	static int R = 0, C = 1, S = 2;
	static char[][] matrix;

	public static void main(String[] args) {
		try {
			File file = new File("right_angle.in");

			Scanner input = new Scanner(file);
			N = input.nextInt();
			M = input.nextInt();
			input.nextLine();
			matrix = new char[N][M];
			for (int i = 0; i < N; i++) {
				String line = input.nextLine();
				for (int j = 0; j < M; j++)
					matrix[i][j] = line.charAt(j);
			}
			input.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void printMatrix() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++)
				System.out.print(matrix[i][j]);
			System.out.println();
		}
	}

}
