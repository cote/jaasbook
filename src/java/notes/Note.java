package notes;

import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;

public class Note implements Comparable {

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public Date getCreated() {
    return created;
  }

  public int compareTo(Object o) {
    Note other = (Note) o;

    // newest first
    return -1 * new  CompareToBuilder().append(getCreated(),
        other.getCreated()).append(getTitle(), other.getTitle())
        .append(getContent(), other.getContent()).append(getId(),
            other.getId()).toComparison();
  }

  private String id;
  private String title;
  private String content;
  private Date created = new Date();
  private Date lastModified;
}