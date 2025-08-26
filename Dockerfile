FROM maven:3.9.6-eclipse-temurin-21

# Set working directory
WORKDIR /app

# Install dependencies: wget, curl, unzip, gnupg2
RUN apt-get update && apt-get install -y wget curl unzip gnupg2

# Install Node.js and npm
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs

# Install Allure CLI
RUN npm install -g allure-commandline --save-dev

# Install Google Chrome
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list && \
    apt-get update && apt-get install -y google-chrome-stable

# Install ChromeDriver (latest matching version)
RUN CHROME_DRIVER_VERSION=$(curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE) && \
    wget -q "https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip" -O /tmp/chromedriver.zip && \
    unzip /tmp/chromedriver.zip -d /usr/local/bin/ && rm /tmp/chromedriver.zip

# Copy Maven project files
COPY pom.xml .

# Download all Maven dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Create directories for reports
RUN mkdir -p allure-results target/surefire-reports && \
    chmod -R 777 allure-results target/surefire-reports

ENTRYPOINT ["mvn"]

# Run tests by default
CMD ["clean", "test", "allure:report", "-Dmaven.test.failure.ignore=true"]
