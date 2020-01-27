package org.meanbean.factories.net;

import org.meanbean.factories.basic.RandomFactoryBase;
import org.meanbean.util.RandomValueGenerator;
import org.meanbean.util.RandomValueSampler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UrlFactory extends RandomFactoryBase<URL> {

	/** Unique version ID of this Serializable class. */
	private static final long serialVersionUID = 1L;

	private RandomValueSampler sampler;

	private List<String> schemes = Arrays.asList("http://", "https://", "ftp://");
	private List<String> tlds = Arrays.asList(".example", ".invalid", ".test");
	private List<String> paths = Arrays.asList("", "/foo", "/foo/bar/", "/foo/bar/index?a=b");

	public UrlFactory(RandomValueGenerator randomValueGenerator) {
		super(randomValueGenerator);
		this.sampler = new RandomValueSampler(randomValueGenerator);
	}

	@Override
	public URL create() {
		String scheme = sampler.getFrom(schemes);
		String domain = getRandomDomain();
		String tld = sampler.getFrom(tlds);
		String path = sampler.getFrom(paths);
		String url = String.join("", scheme, domain, tld, path);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			throw new IllegalStateException(url, e);
		}
	}

	protected String getRandomDomain() {
		int subdomainCount = getRandomValueGenerator().nextInt(1) + 1;
		return IntStream.range(0, subdomainCount)
				.mapToObj(num -> UUID.randomUUID().toString())
				.collect(Collectors.joining("."));
	}

}