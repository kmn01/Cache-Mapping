import java.util.*;

//code for fully associative mapping

public class fullyassociative {

	static HashMap<Integer, Double[]> table = new HashMap<Integer, Double[]>();
	static int cl; //number of cache lines
	static int b;	//block size
	static int off;	//number of offset bits
	static int index;	//number of index bits
	static long m;	//memory size
	static int adsize;	//number of address bits
	static int word = 32;
	static int filled = 0;
	
	static int front=-1, rear=-1;
	static int[] q = new int[10000000];
	
	public static void dequeue() 
	{
		if(front==-1)
		{
			return;
		}
		else if(front==rear)
		{
			front=rear=-1;
		}
		else
		{
			front++;
		}
	}
	public static void enqueue(int val) 
	{
		if(rear==-1)
		{
			rear=front=0;
		}
		else if(rear==q.length-1)
		{
			return;
		}
		else
		{
			rear++;
		}
		q[rear]= val;
	}
	
	public static void read(String address) 
	{
		int tagval = Integer.parseInt(address.substring(0, adsize - off), 2); 
		int offval = Integer.parseInt(address.substring(adsize - off), 2);
		int flag = 0;
		if(filled == 0)
		{
			System.out.println("READ MISS");
		}
		else
		{
			for(int i : table.keySet()) 
			{
				if(i == tagval)
				{
					flag = 1;
					break;
				}
			}
			if(flag == 1)
			{
				System.out.println(table.get(tagval)[offval]);
			}
			else
			{
				System.out.println("READ MISS");
			}
		}
	}
	public static void write(String address, double input) 
	{
		int tagval = Integer.parseInt(address.substring(0, adsize - off), 2); 
		int offval = Integer.parseInt(address.substring(adsize - off), 2);
		int flag = 0;
		for(int i : table.keySet()) 
		{
			if(i == tagval)
			{
				flag = 1;
				break;
			}
		}
		if(flag == 1)
		{
			table.get(tagval)[offval] = input;
		}
		else if(filled < b/word)
		{
			Double[] newrow = new Double[b/word];
			table.put(tagval, newrow);
			table.get(tagval)[offval] = input;
			filled++;
			enqueue(tagval);
		}
		else
		{
			table.remove(q[front]);
			dequeue();
			Double[] newrow = new Double[b/word];
			table.put(tagval, newrow);
			enqueue(tagval);
			table.get(tagval)[offval] = input;
			System.out.println("Address replacing the block: " + address);
		}
	}	
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Number of lines in cache:");
		cl = sc.nextInt();
		System.out.println("Block size:");
		b = sc.nextInt();
		System.out.println("Memory size:");
		m = sc.nextLong();
		
		off = (int)( Math.log(b/word) / Math.log(2) );
		index = (int)( Math.log(cl) / Math.log(2) );
		adsize = (int)( Math.log(m/word) / Math.log(2) );
		
		System.out.println("Enter choice:");
		System.out.println( "1. Read");
		System.out.println( "2. Write");
		System.out.println( "3. Exit");
		int ch = sc.nextInt();
		
		while(ch!=3)
		{
			if(ch==1)
			{
				System.out.println("Address to read:");
				String ad = String.format("%32s", Integer.toBinaryString(sc.nextInt())).replaceAll(" ", "0");
				read(ad);
				//System.out.println("YES");
			}
			else if(ch==2)
			{
				System.out.println("Address to write and data:");
				String ad = String.format("%32s", Integer.toBinaryString(sc.nextInt())).replaceAll(" ", "0");
				double in = sc.nextDouble();
				write(ad, in);
				//System.out.println("NO");
			}
			else
			{
				System.out.println("Invalid choice");
			}
			System.out.println();
			System.out.println("Enter choice:");
			System.out.println( "1. Read");
			System.out.println( "2. Write");
			System.out.println( "3. Exit");
			ch = sc.nextInt();
		}
	}
}
