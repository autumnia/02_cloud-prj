
package com.autumnia.userservice.util;

import org.modelmapper.ModelMapper;

public class ObjectUtil {
	public static <T> T toModel( Object object,  Class<T> type) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper.map(object, type);
	}
}