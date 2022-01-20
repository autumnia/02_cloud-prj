
package com.autumnia.userservice.util;

import com.autumnia.userservice.vo.UserResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ObjectUtil {

//    public static Payload toPayload(Object o){
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            byte[] bytes = objectMapper.writeValueAsBytes(o);
//            Payload payload = DefaultPayload.create(bytes);
//            return payload;
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }

//    public static <T> T toObject(Payload payload, Class<T> type){
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            byte[] bytes = payload.getData().array();
//            T t = objectMapper.readValue(bytes, type);
//            return t;
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }

	public static <T> T toModel( Object object,  Class<T> type) {
		try {
			ModelMapper mapper = new ModelMapper();
			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
			return mapper.map(object, type);
		} catch( Exception e )	{
			throw new RuntimeException(e);
		}

	}
}