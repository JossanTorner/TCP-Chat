package client;

import server.Server;

//Vet ej vad klassen ska heta, lär ju va nåt annat?
public class User {

    private String user;
    private Status status;


    //vi behöver ju koppla ihop med servern här tänker jag?
    //hmm vad behövs från servern

    public User(String user, Status status) {
        this.user=user;

    }
//åh härligt
    public String getUser() {
        return user;
    }

    public Status getStatus() {
        return status;
    }
}
