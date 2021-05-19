import java.util.*;

//code for set associative mapping

public class setassociative {

	static int[] tag;
	static double[][] data;
	//static HashMap<Integer, Double[]> table = new HashMap<Integer, Double[]>();
	static int cl; //number of cache lines
	static int b;	//block size
	static int k;	//set size
	static int off;	//number of offset bits
	static int index;	//number of set index bits
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
			//System.out.println(q[front]);
			front=rear=-1;
		}
		else
		{
			//System.out.println(q[front]);
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
		int inval = Integer.parseInt(address.substring(0, adsize - index), 2);
		int tagval = Integer.parseInt(address.substring(adsize - index, adsize - off), 2); 
		int offval = Integer.parseInt(address.substring(adsize - off), 2);
		int flag = 0;
		if(filled == 0)
		{
			System.out.println("READ MISS");
		}
		else
		{
			for(int i = inval - 1; i<k; i++) 
			{
				if(i == tagval)
				{
					flag = 1;
					System.out.println(data[tagval][offval]);
					break;
				}
			}
			if(flag == 0)
			{
				System.out.println("READ MISS");
			}
		}
	}
	public static void write(String address, double input) 
	{
		int inval = Integer.parseInt(address.substring(0, adsize - index), 2);
		int tagval = Integer.parseInt(address.substring(adsize - index, adsize - off), 2); 
		int offval = Integer.parseInt(address.substring(adsize - off), 2);
		int flag = 0;
		int posemp = 0;
		int posfr = 0;
		for(int i = inval; i<k; i++) 
		{
			if(tag[i] == tagval)
			{
				flag = 1;
				data[i][offval] = input;
				break;
			}
			if(tag[i] == q[front])
			{
				posfr = i;
			}
			if(tag[i] == 0)
			{
				posemp = i;
			}
		}
		if(flag == 0)
		{
			if(filled < b/word)
			{
				data[posemp][offval] = input;
				tag[posemp] = tagval;
				filled++;
				enqueue(tagval);
			}
			else
			{
				data[posfr][offval] = input;
				tag[posfr] = tagval;
				dequeue();
				enqueue(tagval);
				System.out.println("Address replacing the block: " + address);
			}
		}
	}	
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Number of lines in cache:");
		cl = sc.nextInt();
		System.out.println("Set size:");
		k = sc.nextInt();
		System.out.println("Block size:");
		b = sc.nextInt();
		System.out.println("Memory size:");
		m = sc.nextLong();
		
		tag = new int[cl];
		data = new double[cl][b/word];
		off = (int)( Math.log(b/word) / Math.log(2) );
		index = (int)( Math.log(cl/k) / Math.log(2) );
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
