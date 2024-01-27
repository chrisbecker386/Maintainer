format:
	./gradlew ktlintFormat

check:
	./gradlew ktlintCheck

testAll:
    ./gradlew test

testRelease:
    ./gradlew testRelease

testDebug:
    ./gradlew testDebug

testReleaseUnit:
    ./gradlew :app:testReleaseUnitTest

testDebugUnit:
    ./gradlew :app:testDebugUnitTest --info



