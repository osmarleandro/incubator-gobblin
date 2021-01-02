package org.apache.gobblin.runtime.api;

import java.net.URI;

public interface ISpecNotFoundException {

	URI getMissingJobSpecURI();

}