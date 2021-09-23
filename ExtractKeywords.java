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
		
		int level[] = {1,2,3,4};
		String reader;
		FileInputStream inputStream = new FileInputStream(f_path);
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	    int num_else = 0;
	    int num_if_else_if_else = 0;
	    int num_if_else = 0;
	    int num_keyWords = 0;
	    int count = 0;
        String []key=  {
        		"auto","double","int","struct","break","else","long","switch","case","enum","register",
        		"typedef","char","extern","return","union", "const","float","short","unsigned","continue",
        		"for","signed","void","default","goto","sizeof","volatile","do","if","while","static",
        		"elseif"};
		
    }
}