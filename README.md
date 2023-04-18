# useless_web_interface
This is a package to realize the contact between different devices, using **TCP**.

这是一个基于**TCP**传输协定的可用于两个设备之间交互的网络实现。

Also you can write a `Server.java`, to transmit the message between different devices.

撰写一个`Server.java`，用于转发消息。

However, this is just a useless package once you use it.

当然，一旦你开始使用它，你会发现它没有任何用处。

Welcome to update this interface.

期待您对这个接口的改进（如果有兴趣的话，不会真的有人会有兴趣吧）

> jdk version: 1.8

# How to use

You can impliment `WebInteface.java`, and override this method. And besides, announce an instance of `WebController.java` in the code, to realize static proxy.

在你的类中，实现`WebInteface.java`并覆写其中的方法，同时声明`WebController.java`的实例变量，以实现接收消息的静态代理。

Here is a naive examole.

这是一个很稚嫩的例子。

```java

class Test impliment WebInteface{
  private WebController controller;
  private String name;
  
  public Test(String name){
    //use default address and port, see at the constructor of WebController
    //使用默认的地址和端口，参见WebController的构造器。
    controller = new WebController(this);
    this.name = name;
  }
  
  void send(){
    controller.send("Hello, world");
  }
  
  @override
  public void webAction(String message){
    System.out.println(name + "get: " + message);
  }
  
  public static void main(String[] args){
    Test t1 = new Test("t1");
    Test t2 = new Test("t2");
    t1.send();
  }

}

```

wait for updating...
