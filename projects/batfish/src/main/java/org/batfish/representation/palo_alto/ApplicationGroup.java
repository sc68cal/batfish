package org.batfish.representation.palo_alto;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Represents a Palo Alto application-group */
public final class ApplicationGroup implements Serializable {

  private final Set<ApplicationOrApplicationGroupReference> _references;

  private final String _name;

  public ApplicationGroup(String name) {
    _name = name;
    _references = new TreeSet<>();
  }

  @VisibleForTesting
  Set<String> getDescendantObjects(
      Map<String, Application> applications,
      Map<String, ApplicationGroup> applicationGroups,
      Set<String> alreadyTraversedGroups) {
    if (alreadyTraversedGroups.contains(_name)) {
      return ImmutableSet.of();
    }
    alreadyTraversedGroups.add(_name);
    Set<String> descendantObjects = new HashSet<>();
    for (ApplicationOrApplicationGroupReference reference : _references) {
      String referenceName = reference.getName();

      if (applications.containsKey(referenceName)) {
        descendantObjects.add(referenceName);
      } else if (applicationGroups.containsKey(referenceName)) {
        descendantObjects.addAll(
            applicationGroups
                .get(referenceName)
                .getDescendantObjects(applications, applicationGroups, alreadyTraversedGroups));
      } else if (ApplicationBuiltIn.getBuiltInApplication(referenceName).isPresent()) {
        descendantObjects.add(referenceName);
      }
    }
    return descendantObjects;
  }

  /**
   * Returns all {@link Application applications} that are directly or indirectly contained in this
   * group. Accounts for circular group references.
   */
  public Set<String> getDescendantObjects(
      Map<String, Application> applications, Map<String, ApplicationGroup> applicationGroups) {
    return getDescendantObjects(applications, applicationGroups, new HashSet<>());
  }

  public Set<ApplicationOrApplicationGroupReference> getReferences() {
    return _references;
  }

  public String getName() {
    return _name;
  }
}
