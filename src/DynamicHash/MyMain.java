/*******************************
 * Onom/mo: Kostis Konstantinos
 *  
 *  
 *  A.M : 2011030036
 *  
 *******************************/





package DynamicHash;
import java.util.Scanner;

import Tree.BinaryTree;


public class MyMain {
	public static final int size=100;
	public static Hash hash= new Hash(size);
	public static int N;

	public MyMain() {
		// TODO Auto-generated constructor stub
	}

	
	public static void main(String[] args) {
		int size= 100;
		Hash hash = new Hash(size);
		int choice;
		while((choice= getChoice())!=-1){
			switch(choice){
			case 1:
				insert();
				insert_N_elements();
				break;
			case 2:
				search();
				break;
			case 3:
				delete();
				break;
			case 4:
				insert_N_elements();
				break;
			case 5:
				insert_100elements_biggerN();
				break;
			case 6:
				search_N_elements();
				break;
			case 7:
				delete_N_elements();
				break;
			case 8:
				System.out.println("Coming soon");
				break;
			}
			choice=0;
		}
		System.out.println("Program exited succcesfully...");
		
	}
	
	public static int extractBits(int x, int numBits) {
	    if (numBits < 1) {
	        return 0;
	    }
	    if (numBits > 32) {
	        return x;
	    }
	    int mask = (1 << numBits) - 1;
	    return x & mask;
	}
	
	static int getBit(int number, int index)
	{
	  return (number >> index) & 1;
	}
	
	public static int getChoice(){
		printMenu();
		int choice=0;
		Scanner sc= new Scanner(System.in);
		if(sc.hasNextInt()){choice=sc.nextInt();}
		while(choice!=9){
			if(choice<=0 || choice>9){
				System.out.println("\nPlease enter an admissible choice (1-9): ");
				if(sc.hasNext()){choice= sc.nextInt();}
			}else{
				System.out.println("\nChoice= "+choice);
				return choice;
			}
		}
		return -1;		
	}
	
	public static void printMenu(){
		System.out.println("-----Menu-----");
		System.out.println("\t1) Insert keys manually\n" +
						   "\t2) Search keys manually \n" +
						   "\t3) Delete keys manually\n" +
						   "\t4) Insert keys from 1-N\n" +
						   "\t5) Insert 100 random keys bigger than N\n" +
						   "\t6) Search 100 random keys from 1-N\n" +
						   "\t7) Delete 100 random keys from 1-N\n" +
						   "\t8) Run All\n" +
						   "\t9) Exit\n" );
		System.out.printf("Enter your choice: ");
	}
	
	public static void insert(){
		Scanner sc= new Scanner(System.in);
		int numKeys= -1;
		int key= -1;
		
		System.out.println("-----Insert Mode----\n" +
				"How many keys you want to insert?");
		if(sc.hasNextInt()){numKeys= sc.nextInt();}
		for(int i=0; i<numKeys; i++){
			System.out.printf("\nEnter the "+(i+1)+" key ("+(numKeys-i-1)+" keys remaining): ");
			if(sc.hasNextInt()){key= sc.nextInt();}
			hash.insert(key);
		}
		
		
	}

	public static void search(){
		int numKeys=-1; int key=-1;
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("----Search Mode----");
		System.out.println("How many keys you want to search?");
		if(sc.hasNextInt()){numKeys = sc.nextInt();}
		for(int i=0; i<numKeys; i++){
			System.out.printf("\nEnter the "+(i+1)+" key ("+(numKeys-i-1)+" keys remaining): ");
			if(sc.hasNextInt()){key= sc.nextInt();}
			hash.search(key);
		}
	}
	
	public static void delete(){
		int numKeys = -1; int key=-1;
		Scanner sc= new Scanner(System.in);
		
		System.out.println("----Delete Mode----");
		System.out.printf("How many keys you want to delete?: ");
		if(sc.hasNextInt()){ numKeys= sc.nextInt();}
		for(int i=0; i<numKeys; i++){
			System.out.println("\nEnter the "+(i+1)+" key ("+(numKeys-i-1)+" keys remaining): ");
			if(sc.hasNextInt()){ key = sc.nextInt();}
			hash.delete(key);
		}
	}
	
	public static void insert_N_elements(){
		//int N=0;
		System.out.println("-----Insert_N_Elements Mode-----\n");
		System.out.printf("Enter the N: ");
		Scanner sc= new Scanner(System.in);
		if(sc.hasNextInt()){N=sc.nextInt();}
		for(int i=1;i<=N;i++){
			hash.insert(i);	
		}
		System.out.println("\nThe average depth of the binary trees after "+N+ "inserts is: "+hash.getAvgLevel());
	}
	
	public static void insert_100elements_biggerN(){
		int N=1000;
		float counter=0;
		int[] tmpAr= new int[100];
		for (int i=0; i<tmpAr.length;i++){
			tmpAr[i]=generateRandomKey(N, N+500);
			hash.insert(tmpAr[i]);
			counter+= hash.getComp_count();
		}
		System.out.println("\nThe average comparisons for inserting "+N+" elements is: "+counter/100);
	}
	
	public static void search_N_elements(){
		float counter=0;
		for(int i=0;i<100;i++){
			hash.search(generateRandomKey(1, N));
			counter += hash.getComp_count();
		}
		System.out.println("\nThe average comparisons for searching "+ N+" elements is: "+counter/100);
	}
	
	public static void delete_N_elements(){
		float counter=0;
		for(int i=0;i<100;i++){
			hash.delete(generateRandomKey(1, N));
			counter += hash.getComp_count();
		}
		System.out.println("\n The average comparisons for deleting "+N+ " elements is: "+counter/100);
	}
	
	public static int generateRandomKey(int firstNumber, int lastNumber){
		/*Methodos pou paragei ena tyxaio kleidi anamesa se duo arithmous*/
		return (int) (Math.floor(Math.random() * lastNumber) + firstNumber);
	}
}
