package school21.spring.service.models;

public class User {
    private static Long lastId = new Long(1);
    public Long id;
    public String email;

    public User() {
        this.id = User.lastId++;
    }

    public User(String email) {
        this.id = User.lastId++;
        this.email = email;
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
