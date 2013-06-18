package util.id;

import java.io.Serializable;

import org.doomdark.uuid.UUID;
import org.doomdark.uuid.UUIDGenerator;

public class Id implements Serializable {

  private String id;

  public Id(String id) {
    this.id = id;
  }

  /**
   * For serialization.
   */
  public Id() {
  }

  static public Id create() {
    UUID toUse = UUIDGenerator.getInstance()
        .generateTimeBasedUUID();
    return new Id(toUse.toString());
  }

  static public Id create(String value) {
    return new Id(value);
  }

  public String getId() {
    return id;
  }

  public String toString() {
    return getId();
  }

  public int hashCode() {
    return id.hashCode();
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof Id)) {
      return false;
    }

    Id other = (Id) obj;

    return getId().equals(other.getId());
  }
}