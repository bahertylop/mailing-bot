# Используем образ OpenJDK
FROM openjdk:18-jdk-alpine

# Указываем рабочую директорию
WORKDIR /app

# Копируем ваш jar файл в контейнер
COPY target/*.jar /app/mailing-bot.jar

# Запускаем ваше приложение
CMD ["java", "-jar", "/app/mailing-bot.jar"]
