import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractKeywords {
	public static void main(String[] args) throws IOException {
		
		//Read in the code files and select the completion level
		String f_path = "";          //file_path
		int comp_level = 0;          //completion level
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the path of the file: ");
		f_path = sc.next();
		System.out.println("Please enter the completion level what we want: ");
		comp_level = sc.nextInt();
		sc.close();
		
		int level[] = {1,2,3,4};
		String reader;
		FileInputStream inputStream = new FileInputStream(f_path);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		int num_else = 0;
		int num_if_else_if_else = 0;
		int num_if_else = 0;
		int num_keyWords = 0;
		
		String []key=  {
				"auto","double","int","struct","break","else","long","switch","case","enum","register",
				"typedef","char","extern","return","union", "const","float","short","unsigned","continue",
				"for","signed","void","default","goto","sizeof","volatile","do","if","while","static",
				"elseif"};
		StringBuilder str =new StringBuilder();
		while((reader = bufferedReader.readLine()) != null) {
			reader = reader.replace(':', ' ');
		    if(reader.matches("(.*)//(.*)")){
		    	String b[] = reader.split("//");
		        str.append(b[0] + " ");
		    }else{
		    	str.append(reader + " ");
		    }
		}
		inputStream.close();
		bufferedReader.close();
		
		//Refactor the code read in
		String s = str.toString();
		Pattern pat = Pattern.compile("\"(.*?)\"");
		Matcher mat = pat.matcher(s);
		while(mat.find()){
		    s = s.replace(mat.group()," ");
		    pat = Pattern.compile("\"(.*?)\"");
		    mat = pat.matcher(s);
		}
		pat = Pattern.compile("/\\**(.*?)/");
		mat = pat.matcher(s);
		while(mat.find()){
		    s = s.replace(mat.group()," ");
		    mat = pat.matcher(s);
		}
		if(s.isEmpty()){
			System.out.println("Wrong Format");
		    System.exit(0);
		}
		
		s=s.replace("["," ");
		s=s.replace("]"," ");
		s=s.replace("-","a");
		s=s.replace("*","a");
		s=s.replace("/","a");
		s=s.replace("+","a");
		s=s.replace(">","a");
		s=s.replace("=","a");
		s=s.replace("!","a");
		s=s.replace(":","a");
		s=s.replace("\\","a");
		s= s.replaceAll("[^a-zA-Z]", " ");
		String []s1=s.split("[  ' ']");
	    
		
		//Level 1:count the number of all the keywords
		if(comp_level == level[0] || comp_level == level[1] || comp_level == level[2] || comp_level == level[3]) {
			for(int i=0;i < s1.length;i++){
		        for(int j=0;j < key.length;j++) {
		            if(s1[i].equals(key[j])) {
		            	num_keyWords ++;
		            }
		        }
		    }
		    System.out.println("total num: " + num_keyWords);     
		}
        
		
        //Level 2:count the number of keywords switch and cases
        if(comp_level == level[1] || comp_level == level[2] || comp_level == level[3]) {
        	int num_switch = 0;
            for(int i=0;i < s1.length;i++) {
            	if(s1[i].equals("switch")) {
            		num_switch ++;
            	}
            }
            System.out.println("switch num:  " + num_switch);
            
            //count the number of "switch case" structures
            Vector vec_case = new Vector(4);
            int num_case = 0;
            int index = -1;
            for(int i=0;i < s1.length;i++) {
            	if(s1[i].equals("switch")) {
                     index++;
                     num_case=0;
                }
                if(s1[i].equals("case")) {
                     num_case++;
                     vec_case.add(index,num_case);
                }
            }
            
            System.out.print("case num:  ");
            if(num_switch == 0) {
            	System.out.println(0);
            }else {
				for(int t=0;t <= index;t++) {
					System.out.print(vec_case.get(t)+" ");
				}
                System.out.println();  
            }
        }
        
        
        
        //Level 3:count the number of if-else structure
        if(comp_level == level[2] || comp_level == level[3]) {
        	for(int i=0;i<s1.length;i++) {	
        		if(i!=0) {
        			if(s1[i].equals("if")&&!s1[i-1].equals("else")) {
        				int init = i + 1;
                		if(i<s1.length-2) {
                			while((!(s1[i+1].equals("else")&&!s1[i+2].equals("if")))&&i<s1.length-3) {
                    			i++;
                    		}
                		}else{
                			break;
                		}
                		
                		
                		int tmp = i;
                		boolean flag = false;
                		boolean flag2 = true;
                		for(int t = init;t<tmp;t++) {
                			if(s1[t].equals("if")&&!s1[t-1].equals("else")) {
                				
                				for(int k = t;k<tmp;k++) {
                					if(s1[k].equals("else")) {
                						flag2 = false;
                						break;
                					}
                				}
                				
                				if(flag2) num_if_else ++ ;
                				flag = true;
                				break;
                			}
                		}
                		
                		if(flag) {
                    		if(i<s1.length-2) {
                    			while(!(s1[i+1].equals("else")&&!s1[i+2].equals("if"))) {
                        			i++;
                        		}
                    		}else {
                    			break;
                    		}
                    		num_if_else ++;
                		}
                	}
            	}  	
        	}
            System.out.println("if-else num: "+num_if_else);
        }
        
        
        //Level 4:count the number of if-else if-else structure
        if(comp_level == level[3]) {
        	 for(int i=0;i<s1.length;i++) {
        		 if(i < s1.length - 1) {
        			 if(s1[i].equals("else")&&!s1[i+1].equals("if")) {
        				 num_else ++;
                     }
        		 }
        		 if(i == s1.length-1) {
        			 if(s1[i].equals("else")) {
        				 num_else ++;
        			 }
        		 }
        	 }
        	 num_if_else_if_else = num_else - num_if_else;
        	 System.out.println("if -else if- else num: "+num_if_else_if_else);
        }
	      
	}
}
