package util;

public class TestUser {
	private static TestUser instance;
    private int userId;
    private String userName;

    private TestUser(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public static TestUser getInstance() {
        if (instance == null) {
            instance = new TestUser(1, "Test_User");
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void cleanUserSession() {
        instance = null;
    }
}
