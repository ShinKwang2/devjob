# 1단계: Gradle로 Spring Boot 실행 JAR 만들기
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# 의존성 관련 파일부터 복사하면, 소스만 바뀐 빌드에서 캐시를 활용할 수 있다
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# 실제 소스 복사 및 실행 JAR 생성
COPY src ./src
RUN ./gradlew bootJar -x test --no-daemon

# 2단계: 만들어진 JAR만 담아 실행하는 가벼운 이미지
FROM eclipse-temurin:21-jre
WORKDIR /app

# 헬스체크용 wget 설치 + 일반 사용자 생성
RUN apt-get update \
    && apt-get install -y --no-install-recommends wget \
    && rm -rf /var/lib/apt/lists/* \
    && addgroup --system appgroup \
    && adduser --system --ingroup appgroup appuser \
    && mkdir -p /app/uploads \
    && chown -R appuser:appgroup /app

# 빌드 단계에서 만들어진 JAR 하나만 가져온다.
COPY --from=builder /app/build/libs/*.jar app.jar

USER appuser

# 문서화 목적이며, 실제 포트 공개는 docker run/compose에서 한다
EXPOSE 8888

# Actuator health를 컨테이너 헬스체크로 활용
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget -qO- http://localhost:8888/actuator/health | grep -q '"status":"UP"' || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
