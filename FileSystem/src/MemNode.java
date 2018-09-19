public class MemNode {
	String data= "";
	int size = data.length();
	int fd;
	
	public MemNode(String a, int c){
		this.data = a;
		this.size = a.length();
		this.fd = c;
	}
	
	public void setSize(){
		this.size = this.data.length();
	}

	public String toString(){
		return (data + " " + size + " " + fd);
	}
}
