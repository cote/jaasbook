package chp11.uiobjects;

public class SelectOption {

  public String getLabel() {
    return label_;
  }

  public void setLabel(String label) {
    label_ = label;
  }

  public boolean isSelected() {
    return selected_;
  }

  public void setSelected(boolean selected) {
    selected_ = selected;
  }

  public String getValue() {
    return value_;
  }

  public void setValue(String value) {
    value_ = value;
  }

  private boolean selected_;

  private String value_;

  private String label_;
}
