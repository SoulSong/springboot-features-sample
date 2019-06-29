# Instruction
This is a sample to show how to integrate the features in springboot as follow:
- [Conversion & Converter](https://docs.spring.io/spring/docs/5.1.8.RELEASE/spring-framework-reference/core.html#core-convert)
- JsonSerialize & JsonDeserialize
- i18n
- [Validation](https://docs.spring.io/spring/docs/5.1.8.RELEASE/spring-framework-reference/core.html#validation-beanvalidation)
- [@ConfigurationProperties Validation](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/#boot-features-validation)
- ExceptionHandler
- Actuator
  - Prometheus integration
- Logging


# Prepare
There are two endpoints for encoding and decoding string with base64:

- encode
```bash
$ curl http://localhost:8080/help/base64/encoder/abc
YWJj
```
- decode
```bash
$ curl http://localhost:8080/help/base64/decoder/YWJj
abc
```

# Test

## Test converter
`YWJj` is converted to `abc` by the [`@IdDecoder`](./src/main/java/com/shf/springboot/conversion/IdDecoder.java) annotation.
```bash
$ curl http://localhost:8080/user/YWJj
{"id":"abc","username":"foo","password":"YmFy","age":12,"weight":120.5,"email":"foo@gmail.com"}
```

## Test Serialize
```bash
$ curl -X POST "http://localhost:8080/user/" -H "Content-type: application/json;charset=UTF-8" -d "{\"id\": \"abc\", \"username\": \"foo\", \"password\": \"YmFy\", \"age\": 12, \"weight\":120.5, \"email\": \"foo@gmail.com\"}"
{"id":"abc","username":"foo","password":"YmFy","age":12,"weight":120.5,"email":"foo@gmail.com"}
```
View the console, the original value of password is `bar`, it is encoded and decoded 
by [`JsonSerializer`](./src/main/java/com/shf/springboot/serializer/IdSerializer.java) 
and [`JsonDeserializer`](./src/main/java/com/shf/springboot/serializer/IdDeserializer.java) 
```text
c.s.s.controller.UserController          : User(id=abc, username=foo, password=bar, age=12, weight=120.5, email=foo@gmail.com)
```

## Test validation & i18n & exceptionHandler

- Test `@EmailValid` , `@BaseEntityValid` and `@NotNull` annotation with `ValidationExceptionAdviceTrait`:
```bash
$ curl -X POST "http://localhost:8080/user?lang=zh_CN" -H "Content-type: application/json;charset=UTF-8" -d "{\"username\": \"foo\", \"password\": \"YmFy\", \"email\": \"foo\"}"
``` 
**Output**
```json
{
    "biz_error_code": 10001,
    "error_message": "Validation failed.",
    "error_count": 3,
    "validation_failures": {
        "user.id": {
            "field_name": "user.id",
            "error_message": "不能为null",
            "error_code": "NotNull",
            "rejected_value": "null"
        },
        "user.email": {
            "field_name": "user.email",
            "error_message": "邮箱无效",
            "error_code": "EmailValid",
            "rejected_value": "foo"
        },
        "user": {
            "field_name": "user",
            "error_message": "age和weight不能同时为空",
            "error_code": "BaseEntityValid"
        }
    },
    "path": "/user",
    "timestamp": "2019-06-29T16:12:55.488+0000"
}
```

- Switch language with the `lang` queryParam
```bash
$ curl -X POST "http://localhost:8080/user?lang=en_US" -H "Content-type: application/json;charset=UTF-8" -d "{\"username\": \"foo\", \"password\": \"YmFy\", \"email\": \"foo\"}"
``` 
**Output**
```json
{
    "biz_error_code": 10001,
    "error_message": "Validation failed.",
    "error_count": 3,
    "validation_failures": {
        "user.id": {
            "field_name": "user.id",
            "error_message": "must not be null",
            "error_code": "NotNull",
            "rejected_value": "null"
        },
        "user.email": {
            "field_name": "user.email",
            "error_message": "email is not valid.",
            "error_code": "EmailValid",
            "rejected_value": "foo"
        },
        "user": {
            "field_name": "user",
            "error_message": "age and weight cannot be empty at the same time.",
            "error_code": "BaseEntityValid"
        }
    },
    "path": "/user",
    "timestamp": "2019-06-29T16:14:40.834+0000"
}
```

- Test `GeneralExceptionAdviceTrait` handler with unMapping httpMethod:
```bash
$ curl -X Get "http://localhost:8080/user?lang=en_US" -H "Content-type: application/json;charset=UTF-8" -d "{\"username\": \"foo\", \"password\": \"YmFy\", \"email\": \"foo\"}"
``` 
**Output**
```json
{
    "biz_error_code": -1,
    "error_message": "An error occurred and we were unable to resolve it, please contact support on customer service.org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'GET' not supported\r\n\tat org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.handleNoMatch(RequestMappingInfoHandlerMapping.java:200)\r\n\tat org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.lookupHandlerMethod(AbstractHandlerMethodMapping.java:419)\r\n\tat org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:365)\r\n\tat org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:65)\r\n\tat org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler(AbstractHandlerMapping.java:401)\r\n\tat org.springframework.web.servlet.DispatcherServlet.getHandler(DispatcherServlet.java:1232)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1015)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:897)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:634)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:882)\r\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:741)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:92)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:200)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:836)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1747)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\r\n\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\r\n\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.lang.Thread.run(Thread.java:748)\r\n",
    "timestamp": "2019-06-29T16:18:23.652+0000",
    "tracking_id": "e1f5fdbc-4503-4d50-9010-ae233ea545f5"
}
```

## Test Logging
Execute the command as follows:
```bash
$ curl http://localhost:8080/actuator/logging/foo
hello foo
```
View the output in the console
```text
2019-06-30 02:52:25.719  INFO 21592 --- [nio-8080-exec-8] c.s.s.controller.ActuatorController      : hello foo
2019-06-30 02:52:25.720  INFO 21592 --- [nio-8080-exec-8] c.s.s.filter.CommonRequestLoggingFilter  : remote:[0:0:0:0:0:0:0:1],uri:[/actuator/logging/foo],api:[/actuator/logging/{user_id}],http-method:[GET],spent:[3]
```
Here the `uri` info is different from the `api` info.

## Test prometheus
Because of setting `management.endpoints.web.base-path=/manage` in application.properties, so call the endpoint as follows:
```bash
$ curl http://localhost:8080/manage/prometheus
```
Then see that, will get the tag named `userId` of each metrics.
```text
.......
# TYPE http_server_requests_seconds summary
http_server_requests_seconds_count{method="POST",uri="/user",userId="sample_user",} 1.0
http_server_requests_seconds_sum{method="POST",uri="/user",userId="sample_user",} 0.1326422
http_server_requests_seconds_count{method="GET",uri="/manage/prometheus",userId="sample_user",} 1.0
http_server_requests_seconds_sum{method="GET",uri="/manage/prometheus",userId="sample_user",} 1.2391209
http_server_requests_seconds_count{method="GET",uri="/manage/env",userId="sample_user",} 1.0
http_server_requests_seconds_sum{method="GET",uri="/manage/env",userId="sample_user",} 0.0398848
http_server_requests_seconds_count{method="GET",uri="/actuator/logging/{user_id}",userId="sample_user",} 3.0
http_server_requests_seconds_sum{method="GET",uri="/actuator/logging/{user_id}",userId="sample_user",} 0.0619368
# HELP http_server_requests_seconds_max
# TYPE http_server_requests_seconds_max gauge
http_server_requests_seconds_max{method="POST",uri="/user",userId="sample_user",} 0.0
http_server_requests_seconds_max{method="GET",uri="/manage/prometheus",userId="sample_user",} 0.0
http_server_requests_seconds_max{method="GET",uri="/manage/env",userId="sample_user",} 0.0
http_server_requests_seconds_max{method="GET",uri="/actuator/logging/{user_id}",userId="sample_user",} 0.0
# HELP jvm_gc_max_data_size_bytes Max size of old generation memory pool
.....
``` 
In prod, it is a very important way to monitor our services. 