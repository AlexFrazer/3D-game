import java.util.Random;

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
		String genomeString="A";
		int stringSize=10000;
		Random r = new Random();
		for(int i=0; i<stringSize; i++) {
			int current=r.nextInt(3);
			if(current==0) genomeString=genomeString + "A";
			if(current==1) genomeString=genomeString + "T";
			if(current==2) genomeString=genomeString + "G";
			if(current==3) genomeString=genomeString + "C";
		}
		//System.out.println(genomeString);
		Thread t = new Thread(new HelloWorld(genomeString, 10));
		t.start();
	}
}
