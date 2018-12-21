package bfs;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;


public class Main {
	private final static String path = System.getProperty("user.dir") + "//src//bfs//test";
	private static Graph graph;
	private static int[] trace;
	private static Queue<Integer> q;
	private static List<Thread> arrThread;
	private static int numThreads = 1;
	
	public static Graph getGraph() {
		return graph;
	}

	public static void setGraph(Graph graph) {
		Main.graph = graph;
	}

	public static int[] getTrace() {
		return trace;
	}

	public static void setTrace(int[] trace) {
		Main.trace = trace;
	}

	public static Queue<Integer> getQ() {
		return q;
	}

	public static void setQ(Queue<Integer> q) {
		Main.q = q;
	}

	public static void initialGraph(String line) throws NullPointerException {
		String[] arrStr = line.split(" ");
		int n = Integer.parseInt(arrStr[0]),
			first = Integer.parseInt(arrStr[1]),
			last = Integer.parseInt(arrStr[2]);
		graph = new Graph(n, first, last);
	}
	
	public static void changeGraph(String line) {
		String[] arrStr = line.split(" ");
		int a = Integer.parseInt(arrStr[0]) - 1,
			b = Integer.parseInt(arrStr[1]) - 1;
		int[][] matrix = graph.getMatrix();
		matrix[a][b] = matrix[b][a] = 1;
		graph.setMatrix(matrix);
	}
	
	public static void readFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			StringBuilder sb = new StringBuilder();
			// read first line
			String line = br.readLine();
			initialGraph(line);

			
			while ((line = br.readLine()) != null) {
				changeGraph(line);
				sb.append(line);
				sb.append(System.lineSeparator());
			}
		} catch(Exception e) {
			
		} finally {
			br.close();
		}
	}
	public static void initBFS() {
		trace = new int[graph.getN()];
		Arrays.fill(trace, -1);
		q = new LinkedList<>();
		q.add(graph.getFirst()-1);
		trace[graph.getFirst()-1] = -2;
	}

	public static void singleBFS() throws InterruptedException {
		long start = System.nanoTime();
		int[][] matrix = graph.getMatrix();
		while (!q.isEmpty()) {
			int currentVertex = q.remove();
			System.out.println(q);
			for (int nextVertex = 0; nextVertex < graph.getN(); nextVertex++) {
				if (matrix[currentVertex][nextVertex]!=0 && trace[nextVertex]==-1) {
					q.add(nextVertex);
					trace[nextVertex] = currentVertex;
				}
			}
		}
		
        long end = System.nanoTime();
        System.out.println((end - start)/1e6 + "ms");
        
	}
	public static void multithreadBFS() throws InterruptedException {
		while (!q.isEmpty()) {
			int currentVertex = q.remove();
			System.out.println(q);
			arrThread = new ArrayList<>();
			for (int i = 0; i < numThreads; i++) {
				Thread thread = 
					new Thread(
						new ThreadBFS(
								i+1, 
								graph.getN()*i/numThreads, 
								graph.getN()*(i+1)/numThreads, currentVertex));
				arrThread.add(thread);
				thread.start();
			}
			
			for (int i = 0; i < numThreads; i++) {
				arrThread.get(i).join();
			}
		}

	}
	
	public static synchronized void printPath() {
		int[] arr = new int[graph.getN()];
		int c = -1;
		if (trace[graph.getLast()-1] == -1)
			System.out.println("Not found");
		else {
			System.out.println("The shorstest path from vertex " + graph.getFirst() + " to " + graph.getLast() + " is:");
			int f = graph.getFirst()-1,
				l = graph.getLast()-1;
			while (f != l) {
				c++;
				arr[c] = l+1;
				l = trace[l];
			}
			c++;
			arr[c] = graph.getFirst(); 
			for (int i = c; i > 0; i--) {
				System.out.print(arr[i] + "->");
			}
			System.out.println(graph.getLast());
		}
		
	}
	
	public static void inputNumThread() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of thread (must be less than number of " + graph.getN() +  " vertex): ");
		int num = sc.nextInt();	
		numThreads = num > graph.getN() ? graph.getN() : num;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Main thread start");
		readFile();
		initBFS();
		Scanner sc = new Scanner(System.in);
		inputNumThread();
		multithreadBFS();
		printPath();
		System.out.println("Main thread exitting");

	}

}
