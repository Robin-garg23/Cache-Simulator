import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

//import CHW1.node;


public class Robin_2019092_FinalAssignment {
	
	static Scanner a=new Scanner(System.in);
	static int CL,B;
	
	static List<String> Block = new ArrayList<String>();
	static List<List<String>> Cachelines=new ArrayList<List<String>>();
	static List<Integer> BLtag = new ArrayList<Integer>();
	static List<List<List<String>>> Set = new ArrayList<List<List<String>>>();
	static List<Integer> LinSet=new ArrayList<Integer>();
	
	static int tag,index=0,offset;
	
	public static void CLS() {
	    try {
	        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }
	}
	
	public static void getch()
	{
		try {
            System.in.read();
         }
        catch (IOException e){
            System.out.println("Error reading from user");
        }
	}
	
	 static void stat()
     {
     System.out.println("What do you wanna do:");
     System.out.println("Presss 1 for read");
     System.out.println("Press 2 for write");
     System.out.println("Press 3 for exit");
     }
	 
	 static void separate(String S,int a,int b)
	 {
		 String Tag= S.substring(0,S.length()-a-b);
		 String Index=S.substring(S.length()-a-b,S.length()-a);
		 String Offset=S.substring(S.length()-a);
		 tag=Integer.parseInt(Tag,2);
		 if(!Index.contentEquals(""))
		 index=Integer.parseInt(Index,2);
		 offset=Integer.parseInt(Offset,2); 
	 }
	 

	 
	 static void directread(String S)
	 {	 
		 separate(S,B,CL);
		 
		 if(tag == BLtag.get(index))
		 {
			 System.out.println("Address Found");
			 System.out.print("The current value in the address is:  ");
    		 System.out.println(Cachelines.get(index).get(offset));
    		 getch();
		 }
		 else
		 {
			 System.out.println("Address Not Found");
			 System.out.println("The Address block has been added to Cache and removing the previous block present in cache line if any");
			 getch();
			 BLtag.set(index, tag);
			 Cachelines.get(index).clear();
			 Cachelines.add(new ArrayList<String>());
        	 for(int j=0;j<(int)Math.pow(2, B);j++)
        		 Cachelines.get(index).add("NULL");
		 }
		 
	 }
	 
	 static void directwrite(String d)
	 {
		 Cachelines.get(index).set(offset,d);
		 System.out.println("Given data is added to the Cache");
		 System.out.println("The New value in the address is:   " + Cachelines.get(index).get(offset));
		 getch();
	 }
	 
//  METHOD FOR DIRECT MAPPING
	 static void Direct()
     {
     	int N;
     	
     while(true)
     {
    	CLS();
    	stat();
    	
      	N=a.nextInt();
      	CLS();
      	if(N==1)
     	{
     		System.out.println("Input the address to be readed");
     		String adr=a.next();
     		directread(adr);
     	}
     	else if(N==2)
     	{
     		System.out.println("Input the address to be readed");
     		String adr=a.next();
     		directread(adr);
     		
     		System.out.println("Input the Data to be added");
     		String data=a.next();
     		
     		directwrite(data);
     		
     	}
     	else
     	{
     		System.exit(0);
     	}
     }
     }
	 
// METHOD FOR ASSOCIATIVE MAPPING
	 static void Associative()
	 {
		 nwayAssociative((int)Math.pow(2, CL));
	 }
     
//   METHOD FOR N-WAY SET ASSOCIATIVE     
     static void nassociativeread(String S,int b)
     {
    	 separate(S,B,b);
    	 int x=(int)Math.pow(2, CL-b);
    	 int s=0;
    	 for(int i=0;i<x;i++)
    	 {
	    	 if (tag== BLtag.get(index*x+i))
	    	 {
	    		 System.out.println("Address Found");
	    		 System.out.print("The current value in the address is:  ");
	    		 System.out.println(Cachelines.get(index*x+i).get(offset));
	    		 getch();
	    		 int f=LinSet.get(index*x+i);
	    		 LinSet.set(index*x+i,x);
	    		 for(int j=0;j<x;j++)
	    		 {
	    			 if(LinSet.get(index*x+j)>f)
	    			 {
	    				
	    				 LinSet.set(index*x+j,LinSet.get(index*x+j)-1);
	    			}
	    		 }
	    		 
	    		 
	    	 }
	    	 else
	    		 s++;
    	 }

    	 if(s==x)
    	 {
    		 int bda=x+1;
    		 System.out.println("Address Not Found");
    		 
    		 getch();
    		 for(int j=0;j<x;j++)
    		 {
    			 if(LinSet.get(index*x+j)<bda && LinSet.get(index*x+j)!=-1)
    				 bda=LinSet.get(index*x+j);
    		 }
    		 if(bda==1)
    		 {
    			 for(int j=0;j<x;j++)
        		 {
        			 if(LinSet.get(index*x+j)==1)
        			 {
        				
        				 for(int k=0;k<x;k++)
        	    		 {
        	    			 if(LinSet.get(index*x+k)!=1)
        	    				 LinSet.set(index*x+k,LinSet.get(index*x+k)-1);
        	    		 }
        				 LinSet.set(index*x+j,x);
        				 BLtag.set(index*x+j, tag);
        				 Cachelines.get(index*x+j).clear();
        				 Cachelines.add(new ArrayList<String>());
        				 System.out.println("The Cache block is being replaced by new Block following the LRU method");
        				 System.out.println("The Address has been added to Cache");
        				 getch();
        	        	 for(int l=0;l<(int)Math.pow(2, B);l++)
        	        		 Cachelines.get(index*x+j).add("NULL");
        	        	 break;

        			 }
        				 
        		 }
    		 }
    		 else
    		 {
    			 for(int j=0;j<x;j++)
        		 {
    				 if(LinSet.get(index*x+j)==-1)
    				 {
//    					 System.out.println(bda);
    					 System.out.println("The Address has been added to Cache");
    					 getch();
    					 LinSet.set(index*x+j,bda-1);
    					 BLtag.set(index*x+j, tag);
        				 Cachelines.get(index*x+j).clear();
        				 Cachelines.add(new ArrayList<String>());
        	        	 for(int l=0;l<(int)Math.pow(2, B);l++)
        	        		 Cachelines.get(index*x+j).add("NULL");
        	        	 break;
    					 
    				 }
        		 }
    		 }
    	 }
    		 
    		 
    		 
    	 
     }
     static void nassociativewrite(String d,int b) 
     {
    	 int x=(int)Math.pow(2, CL-b);
    	 
    	 for(int i=0;i<x;i++)
    	 {
	    	 if (tag== BLtag.get(index*x+i))
	    	 {
	    		 Cachelines.get(index*x+i).set(offset,d);
	    		 System.out.println("Given data is added to the Cache");
	        	 System.out.println("The New value in the address is:   " + Cachelines.get(index*x+i).get(offset));
	    	 }
    	 } 
    	 
    	 getch();
    	 
     }
     static void nwayAssociative(int n)
     {
    	int N;
      	int b=CL-(int)(Math.log(n)/Math.log(2));
      	int c=(int)Math.pow(2, b);
      	
      	for(int i=0;i<c;i++)
        {
        	 Set.add(new ArrayList<List<String>>());
  
        	 for(int j=0;j<n;j++)
        	 {
        		 Set.get(i).add(Cachelines.get(i*n+j));
        	 }
        }
         while(true)
         {
        	CLS();
        	stat();
          	N=a.nextInt();
          	CLS();
          	
         	if(N==1)
         	{
         		System.out.println("Input the address to be readed");
         		String adr=a.next();
         		nassociativeread(adr,b);
         	}
         	else if(N==2)
         	{
         		System.out.println("Input the address to be readed");
         		String adr=a.next();
         		nassociativeread(adr,b);
         		
         		System.out.println("Input the Data to be added");
         		String data=a.next();
         		nassociativewrite(data,b);
         		
         	}
         	else
         	{
         		System.exit(0);
         	}
         }
     }
     
	public static void main(String[] args) {
        
        int N;
        
        CLS();
        
        System.out.println("Input Number of Cache Lines");
        CL=a.nextInt();
        
        CLS();
        
        System.out.println("Input Block Size");
        B=a.nextInt();
        
        CLS();
        
        for(int i=0;i<CL;i++)
        {
        	 Cachelines.add(new ArrayList<String>());
        	 BLtag.add(-1);
        	 LinSet.add(-1);
        	 for(int j=0;j<B;j++)
        		 Cachelines.get(i).add("NULL");
        }
        
        CL=(int)(Math.log(CL)/Math.log(2));
        B=(int)(Math.log(B)/Math.log(2));	
        
        
        System.out.println("What do you wanna do:");
        System.out.println("Presss 1 for Direct Mapping");
        System.out.println("Press 2 for Associative Mapping");
        System.out.println("Press 3 for n-way Associative Mapping");
        System.out.println("Press 4 for Exit");
        N=a.nextInt();
        
        CLS();
        
        while(true)
        {
        	if(N==1)
        	{
        	 Direct();
        	}
        	else if(N==2)
        	{
        		Associative();
        	}
        	else if(N==3)
        	{
        		System.out.println("Enter the n for Associative mapping:");
        		int n;
        		n=a.nextInt();
        		nwayAssociative(n);
        	}
        	else if(N==4)
        	{
        		System.exit(1);
        	}
        		
        }
	}
       
       
}


