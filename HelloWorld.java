public class HelloWorld implements Runnable {
	String str;
	long pause;
	int index;
	HelloWorld(String str, long pause) {
		this.str=str;
		this.pause=pause;
		index=0;
	}
	public void run() {
		try {
			while(true) {
				if(index > str.length()-1) break;
				System.out.print(str.substring(index, index+1));
				if(!str.substring(index, index+1).equals(" ")) Thread.sleep(pause);
				index++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Thread t = new Thread(new HelloWorld("Hello World", 150));
		t.start();
	}
}
