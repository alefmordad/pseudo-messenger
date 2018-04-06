package ir.alefmordad.makenger.client;

import ir.alefmordad.makenger.core.entities.User;
import ir.alefmordad.makenger.core.exceptions.FakeUserException;
import ir.alefmordad.makenger.core.model.Client;
import ir.alefmordad.makenger.core.tools.Receiver;
import ir.alefmordad.makenger.core.tools.Sender;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Tunnel extends Client {

    public Tunnel(String ip, int port) throws IOException {
        readUserInfo();
        Socket socket = new Socket(ip, port);
        sender = new Sender(this, socket.getOutputStream());
        receiver = new Receiver(this, socket.getInputStream());
        signIn();
    }

    private void readUserInfo() {
        System.out.print("Enter Id : ");
        Scanner scanner = new Scanner(System.in);
        User user = new User();
        user.setId(scanner.nextLine());
        System.out.print("Enter Password : ");
        user.setPassword(scanner.nextLine());
        this.setUser(user);
    }

    private void signIn() throws IOException {
        sender.sendInfoToServer();
        if (!receiver.receiveLoggedIn())
            throw new FakeUserException(user);
    }

}