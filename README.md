# Cache-Mapping
Implementation of a standalone one-level cache that can be read from and written to. 
The three types of mappings considered:
1. Direct 
2. Fully Associative 
3. k-way Set Associative


Inputs:

- ch: nature of query- read, write or exit
- cl: number of cache lines
- b: block size
- m: memory size (multiple of wordlength, 32)
- k: set size for k-way set associative
- For read, address to be read from
- For write, address to write to and data to be written


Output:

- For read
	* in case of miss: READ MISS
	* in case of hit: <data at the given address>
- For write
	* in case of miss: WRITE MISS. New block address: <address>
	* in case of hit: WRITE HIT
- Contents of cache after every query


Assumptions:

- Word size: 32 bit
- Empty elements contain data as 0 and null in fully associative code (this is the output if read from empty location, valid block)
- cl, b, m, k are all in powers of 2 
- Data stored in the cells is of type double
- Range for memory size taken as input is long, data stored in cells is double, all other variables within range of integers
- Small number of testcases 
- Addresses are zero-indexed
- Range for addresses is 0 to (m/4 -1)
