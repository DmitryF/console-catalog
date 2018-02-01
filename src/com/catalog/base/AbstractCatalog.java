package com.catalog.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author DzmitryF
 *
 */
public abstract class AbstractCatalog<T extends CatalogItem> {

	private List<T> items;

	public AbstractCatalog() {
		items = new ArrayList<>();
	}

	public Map<String, List<T>> getGroups(String attributeName) {

		Stream<T> itemsStream = items.stream();

		Map<String, List<T>> itemsByGroupName = itemsStream.filter(filterItem -> filterItem.hasAttribute(attributeName))
				.collect(Collectors.groupingBy(filterItem -> filterItem.getAttributeValue(attributeName)));

		return itemsByGroupName;
	}

	public void add(T item) {
		items.add(item);
	}

	public void remove(T item) {
		if (item != null && items.contains(item)) {
			items.remove(item);
		}
	}
	
	public List<T> getItems(){
		return items;
	}
}
