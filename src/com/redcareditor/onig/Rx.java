package com.redcareditor.onig;

import java.io.UnsupportedEncodingException;

import org.jcodings.specific.UTF8Encoding;
import org.joni.Matcher;
import org.joni.Option;
import org.joni.Regex;
import org.joni.Region;
import org.joni.Syntax;

/**
 * wrapper class around the Joni Regex library which is a optimized port of
 * Onigurama
 * 
 * @author kungfoo
 * 
 */
public class Rx {
	String pattern;
	Regex regex;
	public boolean matchesStartOfLine = false;

	public Rx(String pattern) {
		// TODO: really make this a regex then...
		this.pattern = pattern;
		regex = compileRegex(pattern);
		matchesStartOfLine = pattern.charAt(0) == '^';
	}

	public Match search(String target, int start, int end) {
		byte[] bytes;
		try {
			bytes = target.getBytes("UTF-8");
			Matcher matcher = regex.matcher(bytes, 0, bytes.length);
			int a = matcher.search(0, bytes.length, Option.NONE);
			
			if(a == -1){
				return null;
			}
			
			Region region = matcher.getEagerRegion();
			return new Match(regex, region, target);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Regex compileRegex(String pattern) {
		byte[] bytes;
		try {
			bytes = pattern.getBytes("UTF-8");
			return new Regex(bytes, 0, bytes.length, Option.DEFAULT,
					UTF8Encoding.INSTANCE, Syntax.RUBY);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		return pattern;
	}
}