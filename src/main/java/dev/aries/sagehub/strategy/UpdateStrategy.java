package dev.aries.sagehub.strategy;

@FunctionalInterface
public interface UpdateStrategy<T, R> {
	T update(T entity, R request);
}
