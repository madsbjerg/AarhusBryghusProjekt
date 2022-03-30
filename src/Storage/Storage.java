package Storage;

public class Storage {
    private static Storage storage;

    public Storage getStorage(){
        if(storage == null) storage = new Storage();
        return storage;
    }
}