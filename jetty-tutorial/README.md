# jetty-tutorial
使用jetty的案例：
1、JettyWebSample，使用嵌入的jetty创建了一个web应用。
2、JettyWebDeploySample，结合assembly将项目导出到一个目录，并采用java命令运行，提供一个web应用案例。
java -classpath . -Djava.ext.dirs=./lib org.ado.jetty.tutorial.JettyWebDeploySample
java.ext.dirs参数指定多个jar所在目录。