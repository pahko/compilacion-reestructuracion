package comprobante;

class NewThread implements Runnable {
	
	String name;
    Thread t;
    
    NewThread(String threadname) {
        name = threadname;
        t = new Thread(this, name);
        System.out.println((new StringBuilder("New thread: ")).append(t).toString());
        t.start();
    }

    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println((new StringBuilder(String.valueOf(name))).append(": ").append(i).toString());
                Thread.sleep(1000L);
            }
        } catch(InterruptedException e) {
            System.out.println((new StringBuilder(String.valueOf(name))).append("Interrupted").toString());
        }
        System.out.println((new StringBuilder(String.valueOf(name))).append(" exiting.").toString());
    }
}
