import java.util.*;

// code for direct mapping

public class direct {

	static int[] tag;
	static double[][] data;
	static int cl; //number of cache lines
	static int b;	//block size
	static int off;	//number of offset bits
	static int index;	//number of index bits
	static long m;	//memory size
	static int adsize;	//number of address bits
	static int word = 32;
	
	public static void read(String address) 
	{
		int inval = Integer.parseInt(address.substring(0, adsize - index), 2);
		int tagval = Integer.parseInt(address.substring(adsize - index, adsize - off), 2); 
		int offval = Integer.parseInt(address.substring(adsize - off), 2);
		if(tag[inval] == tagval)
		{
			System.out.println(data[inval][offval]);
		}
		else
		{
			System.out.println("READ MISS");
		}
		
	}
	public static void write(String address, double input) 
	{
		int inval = Integer.parseInt(address.substring(0, adsize - index), 2);
		int tagval = Integer.parseInt(address.substring(adsize - index, adsize - off), 2); 
		int offval = Integer.parseInt(address.substring(adsize - off), 2);
		if(tag[inval] == tagval)
		{
			data[inval][offval] = input;
		}
		else
		{
			tag[inval] = tagval;
			for(int i=0; i < b/word ; i++)
			{
				data[inval][i] = 0;
			}
			data[inval][offval] = input;
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
		
		tag = new int[cl];
		data = new double[cl][b/word];
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
