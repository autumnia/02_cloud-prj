
package com.autumnia.userservice.util;

import org.modelmapper.ModelMapper;

public class ObjectUtil {
	public static <T> T toModel( Object object,  Class<T> type) {
		try {
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			return mapper.map(object, type);
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
	}

    public static Payload toPayload(Object o){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(o);
            return DefaultPayload.create(bytes);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(Payload payload, Class<T> type){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = payload.getData().array();
            return objectMapper.readValue(bytes, type);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}