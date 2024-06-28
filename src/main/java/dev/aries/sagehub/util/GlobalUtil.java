package dev.aries.sagehub.util;

import java.util.Map;

import dev.aries.sagehub.strategy.UpdateStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import static dev.aries.sagehub.constant.ExceptionConstants.NO_UPDATE_STRATEGY;

@Component
@RequiredArgsConstructor
@Slf4j
public class GlobalUtil {

	private final Map<String, UpdateStrategy> updateStrategies;

	public UpdateStrategy checkStrategy(String type) {
		if (updateStrategies.get(type) == null) {
			throw new IllegalArgumentException(String.format(NO_UPDATE_STRATEGY, type));
		}
		return updateStrategies.get(type);
	}
}
