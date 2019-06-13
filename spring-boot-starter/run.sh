./gradlew clean
./gradlew build
cd dist
chmod +x start.sh && ./start.sh
cd ..
Docker-compose up -d