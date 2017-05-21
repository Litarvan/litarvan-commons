# Litarvan's commons

## Setup

### Gradle

```groovy
repositories {
    maven {
        url 'http://litarvan.github.io'
    }
}

dependencies {
    compile 'fr.litarvan.commons:litarvan-commons:1.0.0'
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>litarvan</id>
        <url>http://litarvan.github.io/maven</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>fr.litarvan.commons</groupId>
        <artifactId>litarvan-commons</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## App base

```java
import fr.litarvan.commons.App;

public class MyApp implements App
{
    public String getName()
    {
        return "My App";
    }
    
    public String getVersion()
    {
        return "2.0.0";
    }
    
    public void start()
    {
        ...
    }
}
```

## Config engine

### Features

 * JSON or Java Properties config
 * Guice support (ConfigProvider is @Singleton
 * Object serializing (JSON)
 * Value path nesting

### Example

**config/myconfig.json :**

```json
{
  "hello": "Litarvan !"
}
```

**config/myotherconfig.json :**

```json
{
  "models": {
    "objects": [
      {
        "field1": "foo",
        "field2": "bar"
      },
      {
        "field1": "foo",
        "field2": "bar"
      }
    ]
  }
}
```

```java
import fr.litarvan.commons.App;

public class MyApp implements App
{
    private ConfigProvider provider = new ConfigProvider();
    
    public void start()
    {
        provider.from("config/myconfig.json");
        provider.from("config/myotherconfig.json");
        
        System.out.println("Hello " + provider.at("myconfig.hello")); // Hello Litarvan !
        MyObject[] objects = provider.at("myotherconfig.models.objects", MyObject[].class);
    }
}

public class MyObject
{
    public String field1;
    public String field2;
}
```

## Exception handling system

### Features

 * Customizable saved crash reports generation
 * Cancellable events triggering
 * Full Java 8 flavored
 * Return value support

### Example

```java
import fr.litarvan.commons.App;
import fr.litarvan.commons.crash.ExceptionHandler;

public class MyApp implements App
{
    private ExceptionHandler handler = new ExceptionHandler(this);
    
    public void start()
    {                                                                       
        handler.trigger((handler, throwable, cancel) -> {              
            // Will be called when an exception is caught              
            cancel.cancel(); // Cancel the crash report creation       
        });                                                            
                                                                       
        handler.on(MyException.class, (handler, throwable, cancel) -> {
            // Will be called when a MyException is caugth             
        });                                                            
                                                                       
        MyValue result = handler.handler(() -> {                       
            someRiskyThings();                                         
            return riskyOperation();                                   
        }); // result is null if an exception was triggered                                                            
                                                                       
        try                                                            
        {                                                              
            someRiskyThings();                                         
        }                                                              
        catch (MyException ex)                                         
        {                                                              
            handler.handle(ex); // Manual handling                     
        }                                                              
    }
}
```

## Cancellable

```java
import fr.litarvan.commons.App;
import fr.litarvan.commons.Canceller;
import java.util.List;

public class MyApp implements App
{
    private List<MyListener> listeners;
    
    public void start()
    {
        if (!Canceller.chain(canceller -> listeners.forEach(listener -> listener.onEvent(canceller))))
        {
            // Event was cancelled
            return;
        }
        
        // Continue launching
    }
}

public class MyCoolListener implements MyListener
{
    public void onEvent(Canceller canceller)
    {
        // Do things
        canceller.cancel(); // Cancel the event
    }
}

public interface MyListener
{
    void onEvent(Canceller canceller);
}
```