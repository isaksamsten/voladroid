package com.voladroid.ui.cli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CompletionUtils {

	public static List<String> matches(Collection<String> orig, String p) {
		List<String> m = new ArrayList<String>();
		for (String o : orig) {
			if (o.matches(p)) {
				m.add(o);
			}
		}

		return m;
	}
}
