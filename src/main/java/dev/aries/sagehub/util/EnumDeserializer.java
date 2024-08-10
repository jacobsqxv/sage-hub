package dev.aries.sagehub.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.aries.sagehub.constant.ExceptionConstants;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnumDeserializer<E extends Enum<E>> extends JsonDeserializer<E> {
	private Class<E> enumClass;

	@Override
	public E deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		String value = jsonParser.getText();
		try {
			return Enum.valueOf(enumClass, value.toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException(String.format(ExceptionConstants.ENUM_VALUE_INVALID,
					value));
		}
	}
}
