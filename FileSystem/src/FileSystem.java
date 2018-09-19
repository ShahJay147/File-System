/*
 * OPERATIONG SYSTEMS PROJECT
 * 
 * 201401221 - Deep Raiya
 * 201401408 - Keval Shah
 * 
*/

import java.io.*;
import java.util.*;
public class FileSystem {
    
       static BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
       static Node home = new Node("home.dir");
    static int fdCounter = 1;
    static char MEM[] = new char[20*1024];
    static ArrayList<MemNode> memBus = new ArrayList<MemNode>();

    static Node curPos=home;
    public static void main(String[] args) {
        _init();
        while(true){
            if(handleInput()==-5)
                break;
            }
        
    }
    public static void cdFF(String filepath){
        String[] remainingPath = filepath.split("/");
        if(remainingPath.length == 2){
            int existingChe = -1;
            for(int i = 0; i<curPos.childs.size();i++){
                if(curPos.childs.get(i).name.equals(remainingPath[1])){
                    existingChe = i;
                    break;
                }
            }

            if(existingChe == -1){
                System.out.println("ERROR: File path not existing.");
            }
            else{
                curPos=curPos.childs.get(existingChe);
                System.out.println(curPos.name+" **");
                return;
            }
        }
        else{
            int existing = -1;
            for(int i = 0; i<curPos.childs.size();i++){
                if(curPos.childs.get(i).name.equals(remainingPath[1])){
                    existing = i;
                }
            }
            if(existing != -1){
                curPos = curPos.childs.get(existing);
                String pathToGive = "";                
                for(int i=1; i< remainingPath.length - 1; i++){
                    pathToGive += remainingPath[i] + "/";
                }
                pathToGive += remainingPath[remainingPath.length - 1];
                //System.out.println("********");
                cdFF(pathToGive);
            }
            else{
                System.out.println("ERROR: File path not existing.");
            }
        }
    }
    
    public static int handleInput(){
        
        try{
            
                String[] inp = BR.readLine().split(" ");
                if(inp.length!=1)inp[1]=curPos.name+"/"+inp[1];
            switch(inp[0].toLowerCase()) {
                case "cd" :
                {
                    if(inp.length==1)
                    {
                        curPos=home;
                    }
                    else
                    {
                        cdFF(inp[1]);
                        //System.out.println("****");
                    }
                    break;
                }
                case "debug" :
                {
                    for (MemNode temp : memBus) 
                    {
                        
                        System.out.println(temp);
                    }
                    break ;
                }
                case "make" :
                    {
                        curPos.addFF(inp[1],fdCounter);
                        int existing = -1;

                        for(int i = 0; i<memBus.size();i++){
                            if(memBus.get(i).fd == fdCounter){
                                existing = i;
                            }
                        }

                        if(existing == -1){
                            memBus.add(new MemNode("",fdCounter));
                        }
                        fdCounter++;
                        break;
                    }
                case "rm" :
                    {
                        int toInspect = curPos.getFD(inp[1]);
                        int existing = -1;
                        for(int i = 0; i<memBus.size();i++){
                            if(memBus.get(i).fd == toInspect){
                                existing = i;
                            }
                        }
                        if(existing != -1){
                            memBus.remove(existing);
                        }
                        curPos.deleteFF(inp[1]);
                        break;
                    }    
                case "rn" :
                    {
                        curPos.renameFF(inp[1],inp[2]);
                        break;
                    }
                case "ls" :
                {
                    curPos.printLayout();
                    break;
                }
                case "open" :
                {
                    curPos.openFile(inp[1]);
                    break;
                }
                case "close" :
                {
                    curPos.closeFile(inp[1]);
                    break;
                }
                case "rd" :
                {
                    curPos.readDirectory(inp[1]);
                    break;
                }
                case "rf" :
                {
                    curPos.readFile(inp[1]);
                    break;
                }
                case "write" :
                {
                    String toWrite = "";
                    for(int i = 2; i<inp.length;i++){
                        toWrite += inp[i] + " ";
                    }
                    curPos.writeFile(inp[1], toWrite);
                    int toInspect = curPos.getFD(inp[1]);
                    int existing = -1;
                    for(int i = 0; i<memBus.size();i++){
                        if(memBus.get(i).fd == toInspect){
                            existing = i;
                        }
                    }
                    if(existing != -1){
                        memBus.get(existing).data = toWrite;
                        memBus.get(existing).setSize();
                    }
                    break;
                }
                case "meta" :
                {
                    curPos.readMeta(inp[1]);
                    break;
                }
                case "exit" :
                {
                    return -5;
                }
                case "" :
                {
                    break;
                }
                default :
                {
                    System.out.println("Invalid command");
                    break;
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return 0;
    }

    public static void _init(){
        for(int i=0;i<MEM.length;i++){
            MEM[i]= '0';
        }
        System.out.println("This file system uses tree-structured directory approach and contiguous memory allocation.");
        System.out.println("--------------------------");
        System.out.println("List of commands : ");
        System.out.println();
        System.out.println();
        System.out.println("{filepath} example : home/mnt/folder1/a/b/file1.file (All files and folders should be inside home.)");
        System.out.println();
        System.out.println("1) Create: make {filepath} ");
        System.out.println("2) Delete: rm {filepath} ");
        System.out.println("3) Rename: rn {filepath} {newName}");
        System.out.println("4) Print: ls");
        System.out.println("5) Open: open {filepath}");
        System.out.println("6) Close: close {filepath}");
        System.out.println("7) Read Dir: rd {DirectoryPath}");
        System.out.println("8) Read File: rf {filePath}");
        System.out.println("9) Write File: write {filePath} {data}");
        System.out.println("10) Read Metadata: meta {filePath}");
        System.out.println("11) Exit Filesystem: exit");


    }
}