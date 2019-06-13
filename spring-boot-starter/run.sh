docker-compose up -d
# cp ~/generator-ZWM/dir_sdk_ca/sdk/* src/main/resources
# cp ~/generator-ZQH/dir_sdk_ca/sdk/* src/main/resources
./gradlew clean
./gradlew build
cd dist
chmod +x start.sh && ./start.sh
