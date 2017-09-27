# MQTT-TEST

MQTT의 QoS 별 성능 테스트코드 입니다.

## Getting Started

Broker를 직접 구현 하거나, test.mosquitto.org 에서 제공해주는 테스트 Broker를 이용합니다.


### Prerequisites

* JAVA 1.8 이상 필요
* Maven 필요
* Eclipse 필요

### Installing

다운 로드 받으면 완료 

## Running 

Queue를 이용한 통신 순서에 대한 신뢰성을 가진 방법과, 일번적인 방법 2가지가 있습니다.

```
//일반적인 방법
test/java/lahuman.mqtt/WithOutQueuePub.java
test/java/lahuman.mqtt/WithOutQueueSub.java

//순서에 대한 신뢰성을 가지는 방법
test/java/lahuman.mqtt/WithQueuePub.java
test/java/lahuman.mqtt/WithQueueSub.java
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [JAVA](http://www.oracle.com/technetwork/java/index.html) - JAVA SE Develpment Kit
* [Eclipse](http://www.eclipse.org/) - IDE


## Authors

* **lahuman** - [Daniel Lim](https://lahuman.github.io)


## License

This project is licensed under the MIT License - see the [MIT License](https://opensource.org/licenses/MIT) file for details
