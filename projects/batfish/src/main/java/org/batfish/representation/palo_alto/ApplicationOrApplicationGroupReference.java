package org.batfish.representation.palo_alto;

import java.io.Serializable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Represents a reference to an {@link Application} or {@link ApplicationGroup}. */
public class ApplicationOrApplicationGroupReference
    implements Serializable, Comparable<ApplicationOrApplicationGroupReference> {

  private final String _name;

  public ApplicationOrApplicationGroupReference(String name) {
    _name = name;
  }

  @Override
  public int compareTo(@Nonnull ApplicationOrApplicationGroupReference o) {
    return _name.compareTo(o._name);
  }

  /** Return the name of the referenced Application or ApplicationGroup */
  public String getName() {
    return _name;
  }

  /**
   * Return the name of the vsys this reference is attached to, or return null if no match is found
   */
  @Nullable
  @SuppressWarnings("fallthrough")
  String getVsysName(PaloAltoConfiguration pc, Vsys vsys) {
    if (vsys.getApplications().containsKey(_name)
        || vsys.getApplicationGroups().containsKey(_name)) {
      return vsys.getName();
    }
    switch (vsys.getNamespaceType()) {
      case LEAF:
        if (pc.getShared() != null) {
          return getVsysName(pc, pc.getShared());
        }
        // fall-through
      case SHARED:
        if (pc.getPanorama() != null) {
          return getVsysName(pc, pc.getPanorama());
        }
        // fall-through
      default:
        return null;
    }
  }
}
