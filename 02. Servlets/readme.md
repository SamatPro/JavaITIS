# Java EE (Servlets)

## Apache Maven 

Это фреймворк автоматической сборки проектов с широким функционалом. Maven использует декларативный подход, где все инструкции записываются на языке разметки POM – обычном XML с рядом предопределенных сущностей.

### pom.xml:
```
<project>
  <!-- версия модели для POM-ов Maven 2.x всегда 4.0.0 -->
  <modelVersion>4.0.0</modelVersion>
  
  <!-- координаты проекта, то есть набор значений, который
       позволяет однозначно идентифицировать этот проект -->
  
  <groupId>com.mycompany.app</groupId>
  <artifactId>my-app</artifactId>
  <version>1.0</version>
 <!-- архив веб-приложений (war) - содержит все файлы, связанные с веб-приложением. Он может включать сервлеты Java, JSP, страницы HTML, дескриптор развертывания и связанные ресурсы.-->
  <packaging>war</packaging>

  <!--Для тех, кого интересует, в чём различие между jar и war [](https://www.baeldung.com/java-jar-war-packaging) -->

  <!-- зависимости от библиотек -->
  
  <dependencies>
    <dependency>
    
      <!-- координаты необходимой библиотеки -->
      
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      
      <!-- эта библиотека используется только для запуска и компилирования тестов -->
      
      <scope>test</scope>
      
    </dependency>
  </dependencies>
</project>
 ```

### web.xml 

Файл web.xml хранит информацию о конфигурации приложения. Данный файл должен располагаться в папке WEB-INF. При запуске Tomcat считывает его содержимое и использует считанную конфигурацию. Если же файл содержит ошибки, то Tomcat отображает ошибку. web.xml имеет определенную структуру. Все вложенные узлы, которые определяют конфигурацию, помещаются в корневой узел <web-app>.
 
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
  version="4.0">

<!-- с помощью элемента <servlet> определяется сервлет. Элемент <servlet-name> задает имя сервлета, на которое будет проецироватья класс, указанный в элементе <servlet-class>. -->
    <servlet>
        <servlet-name>SimpleServlet</servlet-name>
        <servlet-class>ru.kpfu.itis.SimpleServlet</servlet-class>
     </servlet>


     <!-- в элементе <servlet-mapping> сервлет с именем SimpleServlet сопоставляется с путем "/home" --> 
    <servlet-mapping>
      <servlet-name>SimpleServlet</servlet-name>
      <url-pattern>/home</url-pattern>
    </servlet-mapping>
<!-- P.S. также существует более современный способ сопоставить путь к сервлету, навешиванием аннотации @WebServlet("/home") к классу сервлета-->
    
  </web-app>
  ```

## Сервлеты

Сервлет является интерфейсом, реализация которого расширяет функциональные возможности сервера. Сервлет взаимодействует с клиентами посредством принципа запрос-ответ. Хотя сервлеты могут обслуживать любые запросы, они обычно используются для расширения веб-серверов.


Основные методы сервлета:

* public void init(ServletConfig config) throws ServletException запускается сразу после загрузки сервлета в память;
* public ServletConfig getServletConfig() возвращает ссылку на объект, который предоставляет доступ к информации о конфигурации сервлета;
* public String getServletInfo() возвращает строку, содержащую информацию о сервлете, например: автор и версия сервлета;
* public void service(ServletRequest request, ServletResponse response) throws ServletException, java.io.IOException вызывается для обработки каждого запроса;
* public void destroy() выполняется перед выгрузкой сервлета из памяти.


### Структура веб-проекта

```
src/main/java Исходники приложения/библиотеки

src/main/resources Ресурсные файлы приложения/библиотеки

src/main/webapp Исходники веб-приложения

src/test/java Исходники тестов

src/test/resources Ресурсные файлы тестов

```

### Контейнер сервлетов


Контейнер сервлетов — программа, представляющая собой сервер, который занимается системной поддержкой сервлетов и обеспечивает их жизненный цикл. Может работать как полноценный самостоятельный веб-сервер, быть поставщиком страниц для другого веб-сервера, или интегрироваться в Java EE сервер приложений.
Контейнер сервлетов обеспечивает обмен данными между сервлетом и клиентами.

```
Наиболее известные реализации контейнеров сервлетов:

* Apache Tomcat
* Jetty
* JBoss
* WildFly
* GlassFish
```

### Основные методы класса HttpServlet

```
doGet(HttpServletRequest request, HttpServletResponse response)

doPost(HttpServletRequest request, HttpServletResponse response)

```

### Обзор HttpServletRequest

Данный интерфейс позволяет получить данные из запроса, поступившего от клиента.

```
String getParameter(String name)  `Получение из запроса значения параметра. Наименование параметра определено значением name. <input name="last_name" value=""/>`

Enumeration getParameterNames() `Получение из запроса имен всех параметров.`

String[ ] getParameterValues(String name) `Для параметра с несколькими значениями данный метод возвращает строковый массив.`

```

### Обзор HttpServletResponse

методы интерфейса HttpServletResponse, дают возможность сервлету сформировать ответ клиенту

```
void addCookie (Cookie cookie) `Метод используется для добавления Cookie в заголовок ответа клинту.`
ServletOutputStream getOutputStream() `Получение бинарного потока вывода для отправления бинарных данных клиенту.`
PrintWriter getWriter `Получение символьного потока вывода для отправления текстовых данных клиенту.`
void setContentType(String type)  `Определение MIME-типа ответа браузеру.`
```
