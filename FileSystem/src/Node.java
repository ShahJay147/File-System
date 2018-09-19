import java.util.*;
import java.io.*;

public class Node {
	boolean isDir, isOpen ,isPro;
	ArrayList<Node> childs = new ArrayList<Node>();
	String data;
//	int Mpointer;
	int size;
	int seekP;
	String name;
	int FD;

	public String toString(){
		System.out.println(name);
		return name;
	}

	public Node(String filePath){
		this.seekP = 0;
		this.size = 0;
		this.name = filePath;
		this.seekP = 0;
		try{
			if(filePath.split("\\.")[1].equals("file")){
				this.isDir = false;
			}else{
				this.isOpen = true;
				this.isDir = true;
			}
		}catch(Exception e){
			this.isOpen = true;
			this.isDir = true;
		}
	}
	public Node(String filePath, int fd){
		this.seekP = 0;
		this.FD = fd;
		this.size = 0;
		this.name = filePath;
		this.seekP = 0;
		try{
			if(filePath.split("\\.")[1].equals("file")){
				this.isDir = false;
			}else{
				this.isOpen = true;
				this.isDir = true;
			}
		}catch(Exception e){
			this.isOpen = true;
			this.isDir = true;
		}
	}
	
	
/*	public int getPointer(){
		return this.Mpointer;
	}

	public void setPointer(int location){
		this.Mpointer = location;
	}
	
	public void setSize(){
	}
	
	public int getSize(){
		return this.size;
	}
	
*/	public void addFF(String filepath,int fd){
		String[] remainingPath = filepath.split("/");
		//if(remainingPath[0].equals("home") || remainingPath[0].equals("Home"))
		if(remainingPath.length == 2){
			int existing = -1;

			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing == -1)
				this.childs.add(new Node(remainingPath[1],fd));
			else
				System.out.println("File already Exist");
					
		}
		else{
			int existing = -1;

			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing == -1){
				Node toAdd = new Node(remainingPath[1]);
				this.childs.add(toAdd);
				Node nextN = childs.get(childs.indexOf(toAdd));
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.addFF(pathToGive, fd);
			}
			else{
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.addFF(pathToGive, fd);
			}
		}
	}

	
	public void deleteFF(String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
					break;
				}
			}

			if(existingChe == -1){
				System.out.println("ERROR: File path not existing.");
			}
			else{
				childs.remove(existingChe);
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.deleteFF(pathToGive);
			}
			else{
				System.out.println("ERROR: File path not existing.");
			}
		}
	}
	
	public void printLayout() {
		/*for (int i = 0; i < increment-1; i++) {
            System.out.print("  ");
        }
		System.out.print("'--");
		String[] print=path.split("/");
		System.out.println(print[print.length-1]);

        for( Node n: childs){
        	
 
        	if(n.isDir == false){
              for (int i = 0; i < increment+2; i++) {
                System.out.print(" ");
       	        }
        		System.out.println(path + "/" + n.name);
        	}
        }
        for( Node n: childs){
        	if(n.isDir == true){
        		if(n.isOpen){
        			n.printLayout(increment+2, path+"/" + n.name);
        		}
        		else{
        			System.out.println(n.name + " directory is closed. Its content cant be read.");        		}
        	}
        }*/
		for(int i = 0; i<childs.size();i++){
			System.out.println(childs.get(i).name);
		}

    }
	
	public void openFile(String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: file path not existing.");
			}
			else{
					childs.get(existingChe).isOpen = true ;	
					System.out.println("Opened: " + filepath);
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.openFile(pathToGive);
			}
			else{
				System.out.println("ERROR: file path not existing.");
			}
		}
	}
	public void closeFile(String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: file path not existing.");
			}
			else{
				if(childs.get(existingChe).isOpen ){
					childs.get(existingChe).isOpen = false ;					
					System.out.println("Closed: " + filepath);
				}
				else{
					System.out.println("File is not open");//Closed: " + filepath);

				}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.closeFile(pathToGive);
			}
			else{
				System.out.println("ERROR: file path not existing.");
			}
		}
	}
	public void renameFF(String filepath, String newName){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: file path not existing.");
			}
			else{
				childs.get(existingChe).name = newName;
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";				
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.renameFF(pathToGive, newName);
			}
			else{
				System.out.println("ERROR: file path not existing.");
			}
		}

	}

	public void readDirectory( String filepath){

		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 1 && remainingPath[0].equals("home")){
					System.out.println("home Directory Contents:");
			for(Node n: childs){
				System.out.println(n.name);
			}
		}
	
		
		else if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: Directory not existing.");
			}
			else{
					
					for(int i = 0; i<childs.size();i++){
						if(childs.get(i).name.equals(remainingPath[1])){
							existingChe = i;
						}
					}
					Node yolo = childs.get(existingChe);
					System.out.println(yolo.name + " Directory Contents:");
					for(Node n: yolo.childs){
						System.out.println(n.name);
					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.readDirectory(pathToGive);
			}
			else{
				System.out.println("ERROR: Directory not existing.");
			}
		}
		
	}
	public void readFile( String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{
					if(childs.get(existingChe).isOpen){
						System.out.println(childs.get(existingChe).data);
					}else{
						System.out.println("File is Closed. Open it to read/write.");
					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.readFile(pathToGive);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		
	}
	public void writeFile( String filepath, String junk){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{
					if(childs.get(existingChe).isOpen){
						childs.get(existingChe).data = junk;
						childs.get(existingChe).size = junk.length();
					}else{
						System.out.println("File is Closed. Open it to read/write.");
					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.writeFile(pathToGive, junk);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		
	}
	public void readMeta( String filepath){
		String[] remainingPath = filepath.split("/");
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{
//					if(childs.get(existingChe).isOpen){
						System.out.println("File Attributes: ");
						System.out.println("File Name: " + childs.get(existingChe).name);
						//System.out.println("Data: " + childs.get(existingChe).data);
						System.out.println("IsOpen: " + childs.get(existingChe).isOpen);
						System.out.println("Seek pointer: " + childs.get(existingChe).seekP);
						System.out.println("Size: " + childs.get(existingChe).size);
//					}else{
//						System.out.println("File is Closed. Open it to read/write.");
//					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				nextN.readMeta(pathToGive);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		
	}
	public int getFD(String filepath){
		String[] remainingPath = filepath.split("/");
		int toReturn = -1;
		if(remainingPath.length == 2){
			int existingChe = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existingChe = i;
				}
			}
			if(existingChe == -1){
				System.out.println("ERROR: File not existing.");
			}
			else{
					if(childs.get(existingChe).isOpen){
						toReturn = childs.get(existingChe).FD;
					}else{
//						System.out.println("File is Closed. Open it to read/write.");
					}
			}
		}
		else{
			int existing = -1;
			for(int i = 0; i<childs.size();i++){
				if(childs.get(i).name.equals(remainingPath[1])){
					existing = i;
				}
			}
			if(existing != -1){
				Node nextN = childs.get(existing);
				String pathToGive = "";	
				for(int i=1; i< remainingPath.length - 1; i++){
					pathToGive += remainingPath[i] + "/";
				}
				pathToGive += remainingPath[remainingPath.length - 1];
				toReturn = nextN.getFD(pathToGive);
			}
			else{
				System.out.println("ERROR: File not existing.");
			}
		}
		return toReturn;
	}
}
