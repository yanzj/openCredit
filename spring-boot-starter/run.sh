docker-compose up -d
./gradlew clean
./gradlew build
cd dist
chmod +x start.sh && ./start.sh
