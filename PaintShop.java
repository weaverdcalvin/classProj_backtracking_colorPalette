/**
Date:          04/02/2022
Course:        CSCI3005-60360
Description:   PaintShop is a class that contains a constructor method that
               imports a file containing a list of colors (in order of preference)
               along with a list of color pairs that cannot be next to eachother.
               2 different methods are available to return a string containing the
               best arrangements or an integer representing the total amount of
               possible color arrangements 
               

On my honor, I have neither given nor received unauthorized help while completing this assignment.
Name: Calvin Weaver     CWID: 17701091.
*/



import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class PaintShop
{

   /*
   Below are vars/data structures that are
   shared by many of the methods in this class
   */
   
   //Initialize adjacency matrix
   private boolean[][] adjacency;
   //Initialize arrayList to store all colors and associate them with an integer index
   private List<String> colors = new ArrayList<>();
   
   //Initialize array to store colors that cannot be paired together
   private String[] conflicts;
   //Initialize array to hold color pairing solutions
   private String[] solution;
   
   
   private int size = 0;
   private int totalAnswers = 0;
   
   //Boolean vars help "backtracker" method decide whether to return first/best answer or instead count total solutions
   private boolean firstAnswer = true;
   private boolean keepGoing = true;
   
   //String var to store the best solution
   private String returnSol;
   
   
   
   /**
   Constructor takes in a String representing a file name. The file
   is opened, and its contents are stored
   @param f string filename to be opened
   @throws FileNotFoundException if file doesn't exist
   */ 
   public PaintShop(String f) throws FileNotFoundException
   {
     //Assign passed filename to var
      String fname = f;
   
     //Attempt to read in file
      try
      {
         File inputFile = new File(fname);
         Scanner inFile = new Scanner(inputFile);
         inFile.close();
      }
      catch(FileNotFoundException x)
      {
         //File could not be read successfully
         System.out.println("Error! File Could Not Be Located.");
      }
   
         //Read in file
         File inputFile = new File(fname);
         Scanner inFile = new Scanner(inputFile);
         
         //Set size var to first number of file
         size = (Integer.parseInt(inFile.nextLine())+1);
         
         //Make first entry in list a dummy variable, to be ignored. Recursive method needs an initial value to work.
         colors.add("ignore");
         
         //Read in remaining lines, storing them in "color" list
         for(int i = 0; i < size - 1; i++)
         {
            colors.add(inFile.next());
         }
         
         //Set size2 var to second number in file
         int size2 = Integer.parseInt(inFile.next())*2;
         
         //Create an array to store the remainder of the entries in file
         conflicts = new String[size2];
         for(int i = 0; i < size2; i++)
         {
            conflicts[i] = inFile.next();
         }
         
         //Close file
         inFile.close();
         
         //Create an adjacency matrix (all slots initialized as "false" - meaning "no conflict")
         adjacency = new boolean[size][size];
         //For every conflict pair (loop looks at 2 conflicts per run), set adjacency matrix to true for both of the rows the colors interact.
         for(int i = 0; i < size2; i +=2)
         {
            //Uses the String value in conflicts to find its index in the colors arrayList. Then the appropriate index in adjacency matrix is set to "true"
            adjacency[colors.indexOf(conflicts[i])][colors.indexOf(conflicts[i+1])] = true;
            adjacency[colors.indexOf(conflicts[i+1])][colors.indexOf(conflicts[i])] = true;
         }

   }
   
   
   
   /**
      The getSolution method returns the preferred color arrangement that satisfies the given constraints, in a comma-separated list enclosed by brackets. If no solution exists, the method returns the String NONE.
      @return returnSol String containing solution
   */
   public String getSolution()
   {
      //Reset returnSol and totalAnswers
      returnSol="[";
      totalAnswers = 0;
      
      //Set flags (used in backtracker method, will return only the first solution)
      firstAnswer = true;
      keepGoing = true;
      
      //Reset the solution array and place a "dummy" entry into the first slot
      solution = new String[size];
      solution[0] = "dummy";
      
      //Call the backtracker method to find a solution
      backtracker(0);
      
      //Return "none" if no solution is added to returnSol after backtracker is called
      if(returnSol.equals("["))
      {
         return "None";
      }
      else
      {
         return returnSol;
      }
      
   }
   
   
   
   /**
      The howManySolutions method returns the total number of arrangements that include all colors while avoiding conflicts, regardless of order.
      @return totalAnswers int amount of solutions
   */
   public int howManySolutions()
   {
      //Reset totalAnswers
      totalAnswers = 0;
      
      //Set flags (used in backtracker method, will continue until all answers are found)
      firstAnswer = false;
      keepGoing = true;
      
      //Reset solution array and place a "dummy" entry into first slot
      solution = new String[size];
      solution[0] = "dummy";
      
      //Call backtracker method to find amount of solutions
      backtracker(0);
      
      
      return totalAnswers;
   }
   
   
   
   /**
      The backtracker method calls itself recursively until a solution is found, or the total amount of solutions is found
   */
   public void backtracker(int i)
   {
      //Call promising method for the current node/index
      //Keep going flag will stop after the first solution is found, or allow the program to continue counting all solutions, depencing on what method called it
      if(promising(i) && keepGoing)
      {  
         //If the solution array is full / a complete answer is found 
         if(i == size - 1)
         {
            //Increase the amount of answers found
            totalAnswers++;
            
            //If only the first answer/solution is needed
            if(firstAnswer)
            {
               //Counter for use in for loop below
               int x = 0;
               
               //Add each entry in solution array to the returnSol string with expected formatting
               for(String each : solution)
               {
                  if(x != 0 && x < size-1) 
                  {
                     returnSol += each + ", ";
                  }
                  else if(x == size-1)
                  {
                     returnSol += each + "]";
                  }
                  x++;
               }
               
               //Flag will prevent this recursive method from continuing
               keepGoing = false;
               
            }       
         }
         
         //Continue operation
         else
         {
            for(int x = 1; x < size; x++)
            {
               //Set next solution to the next color
               solution[i+1] = colors.get(x);
               //Check next solution
               backtracker(i+1);
            }
         }
      }
   }
   
   
   
   
   /**
      The promising method determines if the most recently-added color has a conflict with any other colors in the solution array
   */   
   public boolean promising(int p)
   {
      
      for(int j = 1; j < p; j++)
      {
         //If current solution has already been used or if it has a conflict with the previous color in the solution array
         if(solution[j].equals(solution[p]) || adjacency[colors.indexOf(solution[p])][colors.indexOf(solution[p-1])] == true)
         {
            return false;
         }
      }
      
      //Return true if no conflicts are found
      return true;     
   }
   
}
