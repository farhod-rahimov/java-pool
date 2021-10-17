package ex03;

public class UserIdsGenerator {
    private static final UserIdsGenerator   instance = new UserIdsGenerator();
    private static int                      id;

    public static UserIdsGenerator getInstance() {
        return instance;
    }

    public int generateId() {
        return ++this.instance.id;
    };
}
