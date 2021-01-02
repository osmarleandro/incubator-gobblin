package org.apache.gobblin.runtime.api;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Iterator;

public interface IInstrumentedSpecStore {

	boolean exists(URI specUri) throws IOException;

	void addSpec(Spec spec) throws IOException;

	boolean deleteSpec(URI specUri) throws IOException;

	Spec getSpec(URI specUri) throws IOException, SpecNotFoundException;

	Collection<Spec> getSpecs(SpecSearchObject specSearchObject) throws IOException;

	Spec updateSpec(Spec spec) throws IOException, SpecNotFoundException;

	Collection<Spec> getSpecs() throws IOException;

	Iterator<URI> getSpecURIs() throws IOException;

	void addSpecImpl(Spec spec) throws IOException;

	Spec updateSpecImpl(Spec spec) throws IOException, SpecNotFoundException;

	boolean existsImpl(URI specUri) throws IOException;

	Spec getSpecImpl(URI specUri) throws IOException, SpecNotFoundException;

	boolean deleteSpecImpl(URI specUri) throws IOException;

	Collection<Spec> getSpecsImpl() throws IOException;

	Iterator<URI> getSpecURIsImpl() throws IOException;

	/** child classes can implement this if they want to get specs using {@link SpecSearchObject} */
	Collection<Spec> getSpecsImpl(SpecSearchObject specUri) throws IOException;

}