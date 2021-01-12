package com.trilead.ssh2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.trilead.ssh2.Connection;

public class ConnectionJenkins extends Connection{

  public ConnectionJenkins(String hostname) {
    super(hostname);
  }

  public ConnectionJenkins(String hostname, int port) {
    super(hostname, port);
  }

  /**
   * Executes a process remotely and blocks until its completion.
   *
   * @param command the command
   * @param output  The stdout/stderr will be sent to this stream.
   * @return the int
   * @throws IOException          the io exception
   * @throws InterruptedException the interrupted exception
   */
  public int exec(String command, OutputStream output) throws IOException, InterruptedException {
    Session session = openSession();
    try {
      session.execCommand(command);
      PumpThread t1 = new PumpThread(session.getStdout(), output);
      t1.start();
      PumpThread t2 = new PumpThread(session.getStderr(), output);
      t2.start();
      session.getStdin().close();
      t1.join();
      t2.join();

      // wait for some time since the delivery of the exit status often gets delayed
      session.waitForCondition(ChannelCondition.EXIT_STATUS,3000);
      Integer r = session.getExitStatus();
      if(r!=null) return r.intValue();
      return -1;
    } finally {
      session.close();
    }
  }

  /**
   * Pumps {@link InputStream} to {@link OutputStream}.
   *
   * @author Kohsuke Kawaguchi
   */
  private static final class PumpThread extends Thread {
    private final InputStream in;
    private final OutputStream out;

    /**
     * Instantiates a new Pump thread.
     *
     * @param in  the in
     * @param out the out
     */
    public PumpThread(InputStream in, OutputStream out) {
      super("pump thread");
      this.in = in;
      this.out = out;
    }

    public void run() {
      byte[] buf = new byte[1024];
      try {
        while(true) {
          int len = in.read(buf);
          if(len<0) {
            in.close();
            return;
          }
          out.write(buf,0,len);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
