package chp08;

import util.id.Id;
import chp07.ActionsPermission;

public class RecordPermission
    extends ActionsPermission {

  public RecordPermission(String recordId, String actions) {
    super(recordId, actions);
  }

  public RecordPermission(Id recordId, String actions) {
    this(recordId.getId(), actions);
  }

}