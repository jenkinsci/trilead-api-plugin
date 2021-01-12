package com.trilead.ssh2.signature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael Clarke
 */
public final class KeyAlgorithmManager {

  private static final Collection<SSHSignature> supportedAlgorithms = buildSupportAlgorithmsList();

  private KeyAlgorithmManager() {
    super();
    // static access only
  }

  public static Collection<SSHSignature> getSupportedAlgorithms() {
    return supportedAlgorithms;
  }

  private static Collection<SSHSignature> buildSupportAlgorithmsList() {
    List<SSHSignature> algorithms = new ArrayList<>();
    algorithms.add(Ed25519Verify.get());
    algorithms.add(ECDSASHA2Verify.ECDSASHA2NISTP256Verify.get());
    algorithms.add(ECDSASHA2Verify.ECDSASHA2NISTP384Verify.get());
    algorithms.add(ECDSASHA2Verify.ECDSASHA2NISTP521Verify.get());
    algorithms.add(RSASHA256Verify.get());
    algorithms.add(RSASHA512Verify.get());

    // TODO: remove SHA-1 support soon
    algorithms.add(RSASHA1Verify.get());
    algorithms.add(DSASHA1Verify.get());
    return (Collection) Collections.unmodifiableCollection(algorithms);
  }
}
