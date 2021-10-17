package ex02;

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}

public class UsersArrayList implements UsersList {
    private User    array[];
    private int     currentSize = 0;
    private int     arraySize = 10;

    public UsersArrayList() {
        this.array = new User[this.arraySize];
    }

    @Override
    public void addUser(User newUser) {
        if (this.currentSize == this.arraySize) {
            User    tmpArray[];
            int     tmpArraySize;

            tmpArray = this.array;
            tmpArraySize = this.arraySize;
            this.arraySize += this.arraySize / 2;
            this.array = new User[this.arraySize];

            for (this.currentSize = 0; this.currentSize < tmpArraySize; this.currentSize++) {
                this.array[this.currentSize] = tmpArray[this.currentSize];
            }
        }
        this.array[this.currentSize++] = newUser;
    };

    @Override
    public User retrieveUserById(long id) {
        int i;

        for (i = 0; i < this.currentSize; i++) {
            if (this.array[i].getId() == id) {
                break;
            }
        }
        if (i == this.currentSize) {
            throw new UserNotFoundException(String.format("User by id %d not found", id));
        }
        return this.array[i];
    };

    @Override
    public User retrieveUserByIndex(int index) {
        return this.array[index];
    };

    @Override
    public int retrieveNumberOfUsers() {
        return this.currentSize;
    };
}
