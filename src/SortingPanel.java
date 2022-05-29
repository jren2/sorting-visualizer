import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;
public class SortingPanel extends JPanel{
    
    private int[] numbers;
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    
    private boolean start;
    private boolean finish;
    
    private int comparisons;
    private int compareIndex1;
    private int compareIndex2;
    private int bubbleComparisons;
    private int sorted;
    private int selectionComparisons;
    private int insertionComparisons;
    private int shellComparisons;
    private int gnomeComparisons;
    private int combComparisons;
    
    private String f;
    private String sortingName;
    
    public SortingPanel()
    {
        // should be set equal to the current sorting algorithm that is running.
        sortingName = "";  

        comparisons = 0;
        compareIndex1 = 0;
        compareIndex2 = 0;
        bubbleComparisons = 0;
        
        start = false;
        finish = false;
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        numbers = new int[100];
        f = "numbers2.txt";
    }

    private void swap(int i, int j){
        int temp = numbers[i];  
        numbers[i] = numbers[j];  
        numbers[j] = temp;
        this.paintImmediately(getVisibleRect());
    }
        
    private void bubbleSort(){
        boolean doMore = true;
        sorted=1;
        while (doMore) {
            doMore = false;
            for (int i=0; i<numbers.length-sorted; i++) {
                comparisons++;
                compareIndex1 = i;
                compareIndex2 = i+1;
                this.paintImmediately(getVisibleRect());
                if (numbers[i] > numbers[i+1]) {
                    swap(i, i+1);
                    doMore = true;
                }
            }
            sorted++;
        }
    }
    
    private void selectionSort(){
        int smallest;
        for(int j=0;j<numbers.length;j++){
            smallest=j;
            for(int i=j+1;i<numbers.length;i++){
                comparisons++;
                compareIndex1 = smallest;
                compareIndex2 = i;
                this.paintImmediately(getVisibleRect());
                if(numbers[i]<numbers[smallest])
                    smallest=i;
            }
            if(smallest!=j)
                swap(j,smallest);
        }
    }
    
    private void insertionSort(){
        for(int i=1;i<numbers.length;i++){
            int x=numbers[i];
            int j=i;
            
            while(j>0 && numbers[j-1]>x){
                numbers[j]=numbers[j-1];
                j=j-1;
                comparisons++;
                compareIndex1 = i+1;
                compareIndex2 = j+1;
                this.paintImmediately(getVisibleRect());
            }
            numbers[j]=x;
        }
    }
        
    private void combSort()  {
        int compare=numbers.length;
        boolean swapped=true;
        
        while (compare!=1 || swapped==true) {
            compare=(compare)/2;
            if (compare < 1)
                compare=1;
            
            swapped = false;
            
            for (int i=0; i<numbers.length-compare; i++){
                if (numbers[i] > numbers[i+compare]){
                    swap(i,i+compare);
                    swapped = true;
                }
                comparisons++;
                compareIndex1 = numbers.length;
                compareIndex2 = i;
                this.paintImmediately(getVisibleRect());
            }
        }
    }
    
    private void gnomeSort(){
        int index = 0;
        
        while (index<numbers.length) {
            if (index==0) {
                index++;
            }
            
            if (numbers[index]>=numbers[index-1]) {
                index++;
            } else{
                swap(index, index-1);
                index--;
            }
            comparisons++;
            compareIndex1 = index;
            compareIndex2 = index+1;
            this.paintImmediately(getVisibleRect());
        }
    }

    private int[] resizeArray(int array[], int newSize){
        int newArray[] = new int[newSize];
        if(array.length <= newSize){
            for(int i = 0 ; i < array.length ; i++){
                newArray[i] = array[i];
            }
        }
        else{
            for(int i = 0 ; i < newSize ; i++){
                newArray[i] = array[i];
            }
        }
        return (newArray);
    } 
    
    void createArray() {
        comparisons = 0;
        Scanner file;
        try {
            
            file = new Scanner(new File(f));
            int counter = 0;
            while (file.hasNext())
            {
                if(numbers.length == counter)
                    numbers = resizeArray(numbers, counter*2);
                numbers[counter] = file.nextInt();
                counter++;
            }       
            numbers = resizeArray(numbers, counter);
            
        } catch (FileNotFoundException e) {
            System.out.println("File not Found");
        }
        
    }
    
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        if(!finish){

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            
            for(int i=0; i<numbers.length; i++){
                if(compareIndex1 == i || compareIndex2 == i)
                    g2.setColor(Color.RED);
                else
                    g2.setColor(Color.CYAN);
                g2.fillRect(i*WIDTH/numbers.length, HEIGHT-numbers[i], WIDTH/numbers.length, numbers[i]);
                
                g2.setColor(Color.BLUE);
                g2.drawRect(i*WIDTH/numbers.length, HEIGHT-numbers[i], WIDTH/numbers.length, numbers[i]);   
            }
            
            g2.setColor(Color.BLACK);
            g2.setFont(new Font ("Arial", Font.BOLD, 35));
            g2.drawString(sortingName, WIDTH-275, HEIGHT-10);
            g2.drawString(String.valueOf(comparisons), 10, HEIGHT-10);
        }
        else{
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            
            for(int i=0; i<numbers.length; i++){
                g2.setColor(Color.CYAN);
                g2.fillRect(i*WIDTH/numbers.length, HEIGHT-numbers[i], WIDTH/numbers.length, numbers[i]);
                
                g2.setColor(Color.BLUE);
                g2.drawRect(i*WIDTH/numbers.length, HEIGHT-numbers[i], WIDTH/numbers.length, numbers[i]);   
            }
            
            g2.setColor(Color.BLACK);
            g2.setFont(new Font ("Arial", Font.BOLD, 35));
            // add a string for each sorting algorithm here.
            g2.drawString("Bubble Sort", WIDTH/2-227, HEIGHT/2-100);
            g2.drawString("Comparisons = " + String.valueOf(bubbleComparisons), WIDTH/2-20, HEIGHT/2-100);
            g2.drawString("Selection Sort", WIDTH/2-227, HEIGHT/2-10);
            g2.drawString("Comparisons = " + String.valueOf(selectionComparisons), WIDTH/2+18, HEIGHT/2-10);
            g2.drawString("Insertion Sort", WIDTH/2-227, HEIGHT/2-55);
            g2.drawString("Comparisons = " + String.valueOf(insertionComparisons), WIDTH/2+18, HEIGHT/2-55);
            g2.drawString("Gnome Sort", WIDTH/2-227, HEIGHT/2+35);
            g2.drawString("Comparisons = " + String.valueOf(gnomeComparisons), WIDTH/2+18, HEIGHT/2+35);
            g2.drawString("Comb Sort", WIDTH/2-227, HEIGHT/2+80);
            g2.drawString("Comparisons = " + String.valueOf(combComparisons), WIDTH/2+18, HEIGHT/2+80);
        }
        
        if(!start){
             start =!start;
             
             //Insertion sort
             createArray();
             sortingName="Insertion Sort";
             insertionSort();
             insertionComparisons=comparisons;
             comparisons=0;
             this.paintImmediately(getVisibleRect());
             
             // bubble sort
             createArray();
             sortingName = "Bubble Sort";
             bubbleSort();
             bubbleComparisons = comparisons;
             comparisons=0;
             this.paintImmediately(getVisibleRect());
             
             //selection sort
             createArray();
             sortingName="Selection Sort";
             selectionSort();
             selectionComparisons=comparisons;
             comparisons=0;
             this.paintImmediately(getVisibleRect());
             
             
             //Gnome Sort
             createArray();
             sortingName="Gnome Sort";
             gnomeSort();
             gnomeComparisons=comparisons;
             comparisons=0;
             this.paintImmediately(getVisibleRect());
             
             //Comb Sort
             createArray();
             sortingName="Comb Sort";
             combSort();
             combComparisons=comparisons;
             comparisons=0;
             this.paintImmediately(getVisibleRect());

             finish = true;
             this.paintImmediately(getVisibleRect());

        }
    }
    
}
