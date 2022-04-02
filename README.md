# classProj_backtracking_colorPalette

This was an assignment for my Analysis of Algorithms course at ULM. It taught me a lot about implementing recursive methods, and I had fun figuring out how to get it working :) Below are brief descriptions of the assignment, and the files uploaded here.

Assignment Description:
  A paint shop owner is faced with the problem of displaying N distinct vertical color strips representing all available paint colors at the shop. The most popular colors are to be displayed on the left side of the wall, proceeding with the less popular colors towards the right side. However, there are certain pairs of colors that cannot be placed next to each other for
aesthetic reasons. Your basic task is to write software to produce the color arrangement that, for each position starting at the left, selects the most popular unused color that does not conflict with the previous color and that allows all N colors to be displayed.

[FILE] colors0.txt, colors1.txt, colors2.txt:
  Text file containts a list of all colors (in order of preference- 1st listed color being best), followed by a list of colors (in pairs) that should not be placed next to eachother.

PaintShop.java:
  PaintShop class contains a constructor that imports a text file (colors0.txt, colors1.txt, colors2.txt), creating appropriate data structures to store the information within the file. Methods in the class can return the total amount of possible color arrangements, or the optimal color arrangement.
  
PaletteTest.java:
  Main program to test the PaintShop.java class. This will create objects in the PaintShop class and test all 3 .txt files.
  
