package p00_Common_TM;

import static p00_Common_TM.Numbers.numbers;

import java.io.*;
import java.util.Random;

public class Writer extends Thread {

	private TableManager manager;
	private Random alea;
	private int id; // id cannot be 0;
	
	public Writer (int id, TableManager manager) {
		this.id = id;
		this.manager = manager;
		alea = new Random();
	}
	
	public void run () {
		int cellId;
		while (true) {
			// generate a random cell
			cellId = alea.nextInt(0, manager.numCells);
			if (manager.blockCell(cellId, id)) {
				manager.write(cellId, id, numbers[id]);
				manager.unBlockCell(cellId, id);
			}
			else {
				// when not allowed to write, writers can do other things
				cannotWriteNow(numbers[id]+" cannot write cell"+cellId);
				try {Thread.sleep(0,1);} catch (InterruptedException iex) {}
			}
		}
	}
	
	// ----------------------
	
	private static BufferedWriter bfw;
	private static volatile int lc = 0;
	
	static {
		try {
			bfw = new BufferedWriter(new FileWriter ("control.txt", false));
		}
		catch (IOException ioex) {
			System.err.println("System cannot start");
			System.exit(1);
		}
	}
	
	private static void cannotWriteNow (String s) {
		if (lc>=1000) return;
		lc++;
		try {
			bfw.write(s);
			bfw.newLine();
		}
		catch (IOException ioex) {
			System.err.println("exception writing file: "+ioex);
		}
	}
	
	public static void close() {
		try {
			bfw.close();
		}
		catch (IOException ioex) {
		}
	}
	
}
