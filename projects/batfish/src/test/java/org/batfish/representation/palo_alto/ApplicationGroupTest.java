package org.batfish.representation.palo_alto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Map;
import org.junit.Test;

/** Tests of {@link ApplicationGroup}. */
public class ApplicationGroupTest {
  @Test
  public void testGetDescendantObjectsBase() {
    ApplicationGroup ag = new ApplicationGroup("group");
    Map<String, Application> applications =
        ImmutableMap.of(
            "a1", Application.builder("a1").build(), "a2", Application.builder("a2").build());

    // only one of the address objects is a member
    ag.getReferences().add(new ApplicationOrApplicationGroupReference("a1"));
    assertThat(
        ag.getDescendantObjects(applications, ImmutableMap.of(), new HashSet<>()),
        equalTo(ImmutableSet.of("a1")));
  }

  @Test
  public void testGetDescendantObjectsBuiltIn() {
    ApplicationGroup ag = new ApplicationGroup("group");
    ag.getReferences()
        .add(new ApplicationOrApplicationGroupReference(ApplicationBuiltIn.FTP.getName()));

    assertThat(
        ag.getDescendantObjects(ImmutableMap.of(), ImmutableMap.of(), new HashSet<>()),
        equalTo(ImmutableSet.of(ApplicationBuiltIn.FTP.getName())));
  }

  @Test
  public void testGetDescendantObjectsCircular() {
    Map<String, ApplicationGroup> applicationGroups =
        ImmutableMap.of(
            "parentGroup",
            new ApplicationGroup("parentGroup"),
            "childGroup",
            new ApplicationGroup("childGroup"),
            "grandchildGroup",
            new ApplicationGroup("grandchildGroup"));
    Map<String, Application> applications =
        ImmutableMap.of(
            "a1", Application.builder("a1").build(), "a2", Application.builder("a2").build());

    // parent -> child -> {parent, grandChild}
    // grandChild -> {child, ad1}
    applicationGroups
        .get("parentGroup")
        .getReferences()
        .add(new ApplicationOrApplicationGroupReference("childGroup"));
    applicationGroups
        .get("childGroup")
        .getReferences()
        .add(new ApplicationOrApplicationGroupReference("grandchildGroup"));
    applicationGroups
        .get("grandchildGroup")
        .getReferences()
        .addAll(
            ImmutableSet.of(
                new ApplicationOrApplicationGroupReference("childGroup"),
                new ApplicationOrApplicationGroupReference("a1")));

    assertThat(
        applicationGroups
            .get("parentGroup")
            .getDescendantObjects(applications, applicationGroups, new HashSet<>()),
        equalTo(ImmutableSet.of("a1")));
  }

  @Test
  public void testGetDescendantObjectsEmpty() {
    ApplicationGroup ag = new ApplicationGroup("group");
    assertThat(
        ag.getDescendantObjects(ImmutableMap.of(), ImmutableMap.of(), new HashSet<>()),
        equalTo(ImmutableSet.of()));
  }

  @Test
  public void testGetDescendantObjectsIndirect() {
    Map<String, ApplicationGroup> applicationGroups =
        ImmutableMap.of(
            "parentGroup",
            new ApplicationGroup("parentGroup"),
            "childGroup",
            new ApplicationGroup("childGroup"));
    Map<String, Application> applications =
        ImmutableMap.of(
            "a1", Application.builder("a1").build(), "a2", Application.builder("a2").build());

    applicationGroups
        .get("parentGroup")
        .getReferences()
        .add(new ApplicationOrApplicationGroupReference("childGroup"));
    applicationGroups
        .get("childGroup")
        .getReferences()
        .add(new ApplicationOrApplicationGroupReference("a1"));

    assertThat(
        applicationGroups
            .get("parentGroup")
            .getDescendantObjects(applications, applicationGroups, new HashSet<>()),
        equalTo(ImmutableSet.of("a1")));
  }
}
