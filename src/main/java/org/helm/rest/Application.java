/*--
 *
 * @(#) Application.java
 *
 *
 */
package org.helm.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code Application} TODO comment me
 *
 * @author
 * @version $Id$
 */

@ApplicationPath("/WebService/service")
public class Application extends ResourceConfig {

  /** The Logger for this class */
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public Application() {
    packages("org.helm.rest");
  }

}
