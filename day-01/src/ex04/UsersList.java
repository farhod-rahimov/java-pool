package ex04;

public interface UsersList {
    void addUser(User newUser);
    User retrieveUserById(long id);
    User retrieveUserByIndex(int index);
    int  retrieveNumberOfUsers();
}
