# 💴 GoldEngine

[![CodeFactor](https://www.codefactor.io/repository/github/bindglam/goldengine/badge)](https://www.codefactor.io/repository/github/bindglam/goldengine)

GoldEngine은 마인크래프트 Paper 서버에 다양한 화폐 시스템을 손쉽게 추가하고 관리할 수 있도록 돕는 플러그인입니다.

## 🚀 주요 기능

*   **다중 화폐 지원**: 서버 내에서 여러 종류의 화폐를 정의하고 사용할 수 있습니다.
*   **심플한 구조**: 화폐 기능의 필수적인 기능만 구현하여 쉽고 간단하게 사용할 수 있습니다.
*   **유연한 API**: 개발자가 쉽게 화폐 시스템을 확장하고 연동할 수 있는 API를 제공합니다.

## 🧑🏻‍💻 API

build.gradle.kts
```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.bindglam.GoldEngine:api:<VERSION>")
}
```

## 📦 빌드 방법

이 프로젝트는 Gradle을 사용하여 빌드할 수 있습니다.

```bash
./gradlew build
```

## 📄 라이선스

이 프로젝트는 [LICENSE](LICENSE) 파일에 명시된 라이선스를 따릅니다.
