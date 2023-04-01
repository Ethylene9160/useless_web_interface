package web.string_server;


import web.tools.WebController;
import web.tools.WebInterface;

class Person implements WebInterface {

    int ownID;
    String name;
    WebController controller;

    public Person(String name) {
        this.name = name;
        controller = new WebController(this);
    }

    @Override
    public void webAction(String message) {
        showMessage(message.charAt(0), message.substring(1));
    }

    public void send(int targetID, String info) {
        controller.send(targetID + StringServer.SPLIT_REG + info);
    }

    private void showMessage(char index, String msg) {
        switch (index) {
            case StringServer.SEND_ID:
                ownID = Integer.parseInt(msg);
                break;
            case StringServer.RECEIVE_MESSAGE:
                System.out.println(name + " Received: " + msg);
                break;
        }
    }


}

class Test{
    public static void main(String[] args) throws InterruptedException {
        Person mike = new Person("Mike");

        Thread.sleep(50);

        Person gan_yu = new Person("Gan Yu");

        Thread.sleep(50);

        Person ye_yang = new Person("Wang Xiaomei");

        Thread.sleep(100);

        mike.send(102, "bSo beautiful! ");

        Thread.sleep(100);

        gan_yu.send(101, "bThk, Mike.");

        Thread.sleep(100);

        gan_yu.send(101, "bDadada");

        Thread.sleep(100);

        ye_yang.send(101, "bI'm real Ganyu!!");
    }
}
