# useless_web_interface
This is a package to realize the contact between different devices, using **TCP**.

Also you can write a `Server.java`, to transmit the message between different devices.

However, this is just a useless package once you use it.

Welcome to update this interface.

> jdk version: 1.8

# How to use

You can impliment `WebInteface.java`, and override this method. And besides, announce an instance of `WebController.java` in the code, to realize static proxy.

Here is a naive examole.

```java

class Test impliment WebInteface{
  private WebController controller;
  
  public Test(){
    controller = new WebController(this);
    controller.send();
  }
  
  void send(){
    new Thread(()->controller.send("Hello, world"));
  }
  
  @override
  public void webAction(String message){
    System.out.println(message);
  }

}

```

wait for updating...
