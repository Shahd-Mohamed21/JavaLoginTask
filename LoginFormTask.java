import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

class User {

    // Fields
    private String username;
    private String password;
    private String role;

    // Constructor
    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

class WelcomePage extends Frame {

    WelcomePage(User user) {

        super("Welcome Page");

        setLayout(new GridLayout(2, 1, 10, 10));

        Label welcome = new Label(
                "Hello, " + user.getUsername(),
                Label.CENTER);

        welcome.setFont(new Font("SansSerif", Font.BOLD, 22));

        Label role = new Label(
                "Your Role: " + user.getRole(),
                Label.CENTER);

        role.setFont(new Font("SansSerif", Font.PLAIN, 18));

        add(welcome);
        add(role);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(350, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

public class LoginFormTask extends Frame {

    private ArrayList<User> users = new ArrayList<>();
    private int wrongAttempts = 0;

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));

        Label title = new Label("Welcome back", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));
        form.add(new Label("Username:", Label.RIGHT));
        TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Panel buttons = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        Button cancel = new Button("Cancel");
        Button login = new Button("Login");
        buttons.add(cancel);
        buttons.add(login);
        content.add(buttons, BorderLayout.SOUTH);

        add(content);

        login.addActionListener(e -> {

    String enteredUsername = username.getText().trim();
    String enteredPassword = password.getText();
    String enteredRole = role.getSelectedItem();

    // Check empty fields
    if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {

        JOptionPane.showMessageDialog(this, "Please fill all fields!");
        return;
    }

    // Find user
    User user = findUser(enteredUsername);

    if (user == null) {

        JOptionPane.showMessageDialog(this, "Username does not exist!");
        return;
    }

    // Check password
    if (!user.getPassword().equals(enteredPassword)) {

        wrongAttempts++;

        if (wrongAttempts >= 3) {

            login.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Too many wrong attempts! Login disabled."
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Wrong password! Remaining attempts: " + (3 - wrongAttempts)
            );
        }

        return;
    }

    // Check role
    if (!user.getRole().equals(enteredRole)) {

        JOptionPane.showMessageDialog(this, "Incorrect role!");
        return;
    }

    // Successful login
    JOptionPane.showMessageDialog(this, "Login Successful!");

    new WelcomePage(user);

    dispose();
});

        cancel.addActionListener(e -> {
    dispose();
});

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(420, 340);
        setLocationRelativeTo(null);
    }

    private void loadUsers() {

        users.add(new User("mousa", "1234", "Student"));
        users.add(new User("ahmed", "5678", "TA"));
        users.add(new User("mohamed", "abcd", "Professor"));

    }

    private User findUser(String name) {

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(name)) {
                return user;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        new LoginFormTask().setVisible(true);
    }
}
