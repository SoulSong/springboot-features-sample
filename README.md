# Instruction
This is a sample to show how to integrate the features in springboot as follow:
- [Conversion & Converter](https://docs.spring.io/spring/docs/5.1.8.RELEASE/spring-framework-reference/core.html#core-convert)
- JsonSerialize & JsonDeserialize
- i18n
- [Validation](https://docs.spring.io/spring/docs/5.1.8.RELEASE/spring-framework-reference/core.html#validation-beanvalidation)
- [@ConfigurationProperties Validation](https://docs.spring.io/spring-boot/docs/2.1.5.RELEASE/reference/htmlsingle/#boot-features-validation)
- ExceptionHandler

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

## Test validation & i18n
Test `@EmailValid` , `@BaseEntityValid` and `@NotNull` annotation with curl:
```bash
$ curl -X POST "http://localhost:8080/user?lang=zh_CN" -H "Content-type: application/json;charset=UTF-8" -d "{\"username\": \"foo\", \"password\": \"YmFy\", \"email\": \"foo\"}"
``` 
Switch language with the `lang` queryParam
```bash
$ curl -X POST "http://localhost:8080/user?lang=en_US" -H "Content-type: application/json;charset=UTF-8" -d "{\"username\": \"foo\", \"password\": \"YmFy\", \"email\": \"foo\"}"
``` 