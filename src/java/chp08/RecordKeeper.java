package chp08;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;

import util.id.Id;

public final class RecordKeeper {

  private Map records = new HashMap();

  public void create(Record record) {
    RecordPermission perm = new RecordPermission(record.getId(),
        "create");
    AccessController.checkPermission(perm);
    records.put(record.getId(), record);
  }

  public Record read(Id recordId) {
    RecordPermission perm = new RecordPermission(recordId, "read");
    AccessController.checkPermission(perm);
    return (Record) records.get(recordId);
  }

  public void update(Record record) {
    RecordPermission perm = new RecordPermission(record.getId(),
        "update");
    AccessController.checkPermission(perm);
    records.put(record.getId(), record);
  }

  public void delete(Id recordId) {
    RecordPermission perm = new RecordPermission(recordId, "delete");
    AccessController.checkPermission(perm);
    records.remove(recordId);
  }

}