package com.catalog.base;

/**
 * @author DzmitryF
 *
 */
public interface CatalogItem {

	boolean hasAttribute(String attributeName);
	
	String getAttributeValue(String attributeName);
}
