package DynamicHash;
import Tree.BinaryTree;

public class Hash {
	private int size;
	public BinaryTree[] index_trees;
	public BinaryTree subtree;
	public int[] avgLevel;
	private int compCount;
	
	
	public Hash(int size) {
		this.size = size;
		index_trees= new BinaryTree[size];
		avgLevel = new int[size];
		for(int i=0;i<size;i++){ avgLevel[i]=0;}
		for(int i=0; i<size;i++){
			index_trees[i]= new BinaryTree(-1);
			index_trees[i].flushvalues();
			index_trees[i].setLevel(0);
		}
	}
	
	public int h(int key) {
		return key % size;
	}
	
	
	public void insert(int key){
		setCompCount(0); //arxikopoiisi tou counter
		//ypologismos hash function
		int index= h(key);
		//XXX comparison
		comparison_count();
		System.out.println("The key "+key+" goes to index "+ index +" of the directory array" );
		if (index_trees[index].isLeaf()){
			if (index_trees[index].isFull()){
				index_trees[index]=split(index_trees[index]);
				// comparison gia to getBit
				if(getBit(key, index_trees[index].getLevel())==1){
					index_trees[index].getRightChild().setValue(key);
				}else{
					index_trees[index].getLeftChild().setValue(key);
				}
				// comparison gia to setValue
				comparison_count();
			}
			else
			{
				index_trees[index].setValue(key);
				// comparison
				comparison_count();
			}
		}
		else
		{
			int level= index_trees[index].getLevel();
			if(getBit(key, level)==1){
				// comparison
				comparison_count();
				if(index_trees[index].getRightChild()!=null){
					subtree = index_trees[index].getRightChild();
				}else{
					System.out.println("Den eixe dexi paidi");
					index_trees[index].setRightChild(subtree);
					subtree.setLevel(level+1);
					subtree.setValue(key);
					// comparison
					comparison_count();
				}
			}else{
				if(index_trees[index].getLeftChild()!=null){
					subtree = index_trees[index].getLeftChild();
				}else{
					System.out.println("Den eixe aristero paidi");
					index_trees[index].setLeftChild(subtree);
					subtree.setLevel(level+1);
					subtree.setValue(key);
					// comparison
					comparison_count();
				}
			}
			
			while( !subtree.isLeaf() ){
				BinaryTree curr = subtree;
				level=curr.getLevel();
				if(getBit(key, level)==1){
					if( curr.getRightChild()!=null) {
						subtree= curr.getRightChild();
					}
					else{
						curr.setRightChild(subtree);
						subtree.setLevel(level+1);
					}
				}else{
					if( curr.getLeftChild()!=null){
						subtree= curr.getLeftChild();
					}else{
						curr.setLeftChild(subtree);
						subtree.setLevel(level+1);
					}
				}
			}
			if(subtree.isFull()){
				subtree=split(subtree);
				level=subtree.getLevel();
				// comparison gia to getBit
				comparison_count();
				if(getBit(key, level)==1){
					subtree.getRightChild().setValue(key);
					System.out.println("Mpike sto dexi paidi");
				}else{
					subtree.getLeftChild().setValue(key);
					System.out.println("Mpike sto aristero paidi");
				}
				// comparison gia to setValue
				comparison_count();
			}else{
				subtree.setValue(key);
				// comparison
				comparison_count();
			}
			compAvLevel(index, subtree.getLevel());
		}
		
	}
	
	public BinaryTree search(int key){
		setCompCount(0);
		int index= h(key);
		// comparison
		comparison_count();
		int [] tmp_value;
		if(index_trees[index].isLeaf()){
			tmp_value = index_trees[index].getValue();
			for(int i=0;i<tmp_value.length;i++){
				if(tmp_value[i] == key){
					// comparison
					System.out.println("The key "+key+" found in root");
					return subtree;
				}
			}
		}else{
			subtree=index_trees[index];
			while(!subtree.isLeaf()){
				// comparison
				comparison_count();
				if(getBit(key, subtree.getLevel())==1){
					
					subtree = subtree.getRightChild();
				}else{
					subtree = subtree.getLeftChild();
				}
				if(subtree.isLeaf()){
					System.out.println("Eftase se fullo");
				}
			}
			tmp_value = subtree.getValue();
			for(int i=0;i<tmp_value.length;i++){
				if(tmp_value[i] == key){
					System.out.println("The key "+key+" found in level "+ subtree.getLevel());
					return subtree;
				}	
			}
			// comparison
			comparison_count();
		}
		System.out.println("Key "+key+" not found in trees. Please try again.");
		return null;
	}
	
	
	public void delete(int key){
		BinaryTree tree= search(key);
		if(tree==null){
			System.out.println("Den yparxei to kleidi");
		}else{
			int[] tmp_val = tree.getValue();
			comparison_count();
			if(tmp_val[0]==key){
				System.out.println("Key "+key+" successfully deleted");
				tmp_val[0]=-1;
			}else{
				System.out.println("Key "+key+" successfully deleted");
				tmp_val[1]=-1;
			}
			tree.setValue(tmp_val);
		}
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	static int getBit(int number, int index)
	{
	  return (number >> index) & 1;
	}
	
	public BinaryTree split(BinaryTree subtree){
		int[] tmp_values= subtree.getValue();
		int level= 0;
		BinaryTree curr= new BinaryTree(); 
		subtree.flushvalues();
		while(getBit(tmp_values[0],subtree.getLevel())==getBit(tmp_values[1], subtree.getLevel())){
			//// comparison
			comparison_count();
			curr= new BinaryTree();
			System.out.println("Mpainei");
			level= subtree.getLevel();
			if(getBit(tmp_values[0], level)==0){
				subtree.setLeftChild(curr);
				curr.setLevel(level+1);
				subtree.flushvalues();
			}else{
				subtree.setRightChild(curr);
				curr.setLevel(level+1);
				subtree.flushvalues();
			}
			subtree=curr;
		}
		
		
		level= subtree.getLevel();
		
		for(int i=0;i<tmp_values.length;i++){
			curr= new BinaryTree();
			if(getBit(tmp_values[i], level)==1){
				// comparison
				comparison_count();
				if(subtree.getRightChild()==null){//an den exei paidi
					subtree.setRightChild(curr);
					curr.setLevel(level+1);
					curr.setValue(tmp_values[i]);
				}else{
					subtree.getRightChild().setValue(tmp_values[i]);//an exei paidi (dld na mpei stin idia thesi)
				}
			}else{
				if(subtree.getLeftChild()==null){//an den exei paidi
					subtree.setLeftChild(curr);
					curr.setLevel(level+1);
					curr.setValue(tmp_values[i]);
				}else{
					subtree.getLeftChild().setValue(tmp_values[i]);//an exei paidi (dld na mpei stin idia thesi)
				}
			}
			// comparison
			comparison_count();
		}
		
		return subtree;
		
	}
	
	
	public void compAvLevel(int index, int level){
		if(level>avgLevel[index]){
			avgLevel[index]= level;
		}
	}
	
	public float getAvgLevel(){
		float average=0;
		for(int i=0;i<100;i++){
			average += avgLevel[i];
		}
		average= average/size;
		return average;
	}
	
	public void setCompCount(int value){
		this.compCount=value;
	}
	
	public void comparison_count(){
		compCount++;
	}
	public int getComp_count(){
		return compCount;
	}
	

}
