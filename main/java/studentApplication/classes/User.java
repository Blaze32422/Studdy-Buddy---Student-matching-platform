package studentApplication.classes;



public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String major;
    private int schoolYear;

    public User() {}
   
    public User(String first_name, String last_name, String email, String username, String password, String major, int schoolYear) {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.major = major;
        this.schoolYear = schoolYear;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int school_year) {
        this.schoolYear = schoolYear;
    }
}