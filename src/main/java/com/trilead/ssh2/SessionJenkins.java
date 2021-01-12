package com.trilead.ssh2;

import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.channel.ChannelManager;

public class SessionJenkins extends Session{

  SessionJenkins(ChannelManager cm, SecureRandom rnd) throws IOException {
    super(cm, rnd);
  }

  /**
   * Write stdout received from the other side to the specified {@link OutputStream}.
   *
   * <p>
   * By default, when data arrives from the other side, trilead buffers them and lets
   * you read it at your convenience from {@link #getStdout()}. This is normally convenient,
   * but if all you are doing is to send the data to another {@link OutputStream} by
   * copying a stream, then you'll be end up wasting a thread just for this.
   * In such a situation, you can call this method and let the I/O handling thread of trilead
   * directly pass the received data to the output stream. This also eliminates the internal
   * buffer used for spooling.
   *
   * <p>
   * When you do this, beware of a blocking write! If a write blocks, it'll affect
   * all the other channels and sessions that are sharing the same SSH connection,
   * as there's only one I/O thread. For example, this can happen if you are writing to
   * {@link Socket}.
   *
   * <p>
   * If any data has already been received and spooled before calling this method,
   * the data will be sent to the given stream immediately.
   *
   * <p>
   * To signal the end of the stream, when the other side notifies us of EOF or when
   * the channel closes, the output stream gets closed. If this is not desirable,
   * you must wrap the output stream and ignore the {@link OutputStream#close()} call.
   *
   * @param os the os
   * @throws IOException the io exception
   */
  /*
  public void pipeStdout(OutputStream os) throws IOException {
    cn.pipeStdoutStream(os);
    cn.getStdoutStream().
  }*/

  /**
   * The same as {@link #pipeStdout(OutputStream)} except for stderr, not for stdout.
   *
   * @param os the os
   * @throws IOException the io exception
   */
  /*
  public void pipeStderr(OutputStream os) throws IOException {
    cn.pipeStderrStream(os);
  }*/
}
