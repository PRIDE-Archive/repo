package uk.ac.ebi.pride.archive.repo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rui Wang
 * @version $Id$
 */
public final class CollectionUtils {
  private CollectionUtils() {}

  public static <T> List<T> createListFromList(Collection<T> collection) {
    List<T> newList = new ArrayList<>();

    if (collection != null) {
      newList.addAll(collection);
    }

    return newList;
  }

  public static <T> void replaceValuesInCollection(Collection<T> from, Collection<T> to) {
    to.clear();
    if (from != null) {
      to.addAll(from);
    }
  }
}
